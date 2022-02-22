package com.company.elevator;

import com.company.elevator.model.*;
import com.company.elevator.service.ElevatorScheduler;
import com.company.elevator.service.ElevatorService;
import com.company.elevator.service.impl.ElevatorSchedulerImpl;
import com.company.elevator.service.impl.ElevatorServiceImpl;

public class ElevatorSim {
    public static void main(String[] args) throws InterruptedException {
        ElevatorScheduler e1Scheduler = new ElevatorSchedulerImpl();
        Elevator e1 = new Elevator((short)1, Direction.NONE, ElevatorStatus.STOPPED, new Floor(0), DoorStatus.CLOSED);
        ElevatorService elevatorService = new ElevatorServiceImpl(e1, e1Scheduler);
        elevatorService.start();
        Thread.sleep(5000);
        e1Scheduler.acceptCall(new ExteriorCall(new Floor(4), Direction.UP, e1));
        Thread.sleep(2000);
        e1Scheduler.acceptInterior(new InteriorCall(new Floor(10)));
    }
}
