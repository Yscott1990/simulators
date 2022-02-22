package com.company.elevator.service.impl;

import com.company.elevator.model.ExteriorCall;
import com.company.elevator.model.Floor;
import com.company.elevator.model.InteriorCall;
import com.company.elevator.service.ElevatorScheduler;
import com.company.elevator.service.ElevatorService;
import com.company.elevator.service.SimulatorComparatorFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author user
 */

@Slf4j
public class ElevatorSchedulerImpl implements ElevatorScheduler {
    private LinkedList<Floor> targetFloors;

    public ElevatorSchedulerImpl() {
        this.targetFloors = new LinkedList<>();
    }

    @Override
    public void acceptCall(ExteriorCall exteriorCall) {
        synchronized (targetFloors) {
            Floor target = exteriorCall.getAt();
            if (!targetFloors.contains(target)) {
                targetFloors.add(target);
                targetFloors.sort(SimulatorComparatorFactory.FLOOR_COMPARATOR::floorCompare);
            }
        }
    }

    @Override
    public LinkedList<Floor> getTargetFloors() {
        return targetFloors;
    }

    @Override
    public void acceptInterior(InteriorCall interiorCall) {
        synchronized (targetFloors) {
            Floor target = interiorCall.getTargetFloor();
            if (!targetFloors.contains(target)) {
                targetFloors.add(target);
                targetFloors.sort(SimulatorComparatorFactory.FLOOR_COMPARATOR::floorCompare);
            }
        }
    }

    @Override
    public void schedule(ElevatorService elevatorService) {
        // start the scheduler and keep listening to incoming calls
    }

    @Override
    public boolean remove(Floor floor) {
        synchronized (targetFloors) {
            if (targetFloors.contains(floor)) {
                return targetFloors.remove(floor);
            } else {
                return false;
            }
        }
    }
}
