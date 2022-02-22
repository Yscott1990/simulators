package com.company.elevator.service.impl;

import com.company.elevator.model.*;
import com.company.elevator.service.ElevatorScheduler;
import com.company.elevator.service.ElevatorService;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;


/**
 * @author user
 */
@Slf4j
public class ElevatorServiceImpl implements ElevatorService {

    private Elevator elevator;
    private ElevatorScheduler elevatorScheduler;
    private Floor nextFloor;
    private ExecutorService worker = Executors.newSingleThreadExecutor();

    public ElevatorServiceImpl(Elevator elevator, ElevatorScheduler elevatorScheduler) {
        this.elevator = elevator;
        this.elevatorScheduler = elevatorScheduler;
        nextFloor = null;
    }

    @Override
    public void move() {
        updateNextFloor();
        // if next floor is null, then no need to move anymore. set as idle.
        if (checkNextFloorAvailability()) {
            try {
                // simulate elevator's movement for one floor.
                TimeUnit.SECONDS.sleep(3);
                moveElevator();
            } catch (InterruptedException e) {
                log.error("error move elevator, " + e);
                Thread.currentThread().interrupt();
            }
        } else {
            log.info("elevator status: {}", this.elevator.getElevatorStatus());
        }
    }

    private void updateNextFloor() {
        log.info("Getting next floor...");
        LinkedList<Floor> floors = elevatorScheduler.getTargetFloors();
        synchronized (floors) {
            try {
                Floor floor = floors.peek();
                if (floor.compareTo(elevator.getCurrentFloor()) == 0) {
                    floors.removeFirst();
                } else {
                    nextFloor = floors.peek();
                }
            } catch (NoSuchElementException | NullPointerException e) {
                nextFloor = null;
            }
        }
        log.info("next floor is: {}", nextFloor);
    }

    private void moveElevator() {
        Objects.requireNonNull(nextFloor, "next Floor not available... no need to move elevator");
        elevator.setElevatorStatus(ElevatorStatus.RUNNING);
        this.elevator.setDirection(directionMove().apply(nextFloor, this.elevator));
        log.info("current elevator status: [{}], direction => {}, currentFloor: {}", elevator.getElevatorStatus(),
                elevator.getDirection(),
                elevator.getCurrentFloor());
        onArrive();
        elevator.setCurrentFloor(elevator.getDirection() == Direction.DOWN ?
                new Floor(elevator.getCurrentFloor().getFloorNumber() - 1) :
                new Floor(elevator.getCurrentFloor().getFloorNumber() + 1));
    }

    private BiFunction<Floor, Elevator, Direction> directionMove() {
        return ((floor, elevator1) -> {
            if (elevator1.getCurrentFloor().compareTo(floor) >= 0) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        });
    }

    private void onArrive() {
        if (elevator.getCurrentFloor().compareTo(nextFloor) == 0) {
            log.info("opened the door for floor: {}", elevator.getCurrentFloor());
            openDoor();
            elevator.setDirection(Direction.NONE);
            log.info("elevator : {} direction set to NONE", elevator.getId());
        }
        closeDoor();
        log.info("closed the door for floor: {}", elevator.getCurrentFloor());
    }

    private void openDoor() {
        elevator.setDoorStatus(DoorStatus.OPEN);
    }

    private void closeDoor() {
        elevator.setDoorStatus(DoorStatus.CLOSED);
    }

    private boolean checkNextFloorAvailability() {
        log.info("checking next floor availability...");
        if (nextFloor == null) {
            log.error("next Floor is not available..");
            log.error("resetting elevator properties");
            this.elevator.setDirection(Direction.NONE);
            this.elevator.setDoorStatus(DoorStatus.CLOSED);
            this.elevator.setElevatorStatus(ElevatorStatus.IDLE);
            return false;
        }
        log.info("next floor is available.");
        return true;
    }

    @Override
    public void start() {
        log.info("elevator service started for elevator : {}", elevator.getId());
        worker.submit(() -> {
            for (;;) {
                move();
            }
        });
    }

    @Override
    public void stop() {
        log.info("elevator service stopped for elevator: {}", elevator.getId());
        try {
            worker.shutdown();
            boolean isStopped = worker.awaitTermination(10, TimeUnit.SECONDS);
            if (!isStopped) {
                worker.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("thread interrupted " + e);
            Thread.currentThread().interrupt();
        } finally {
            this.elevator.setElevatorStatus(ElevatorStatus.STOPPED);
            this.elevator.setDoorStatus(DoorStatus.CLOSED);
            this.elevator.setDirection(Direction.NONE);
        }
    }
}
