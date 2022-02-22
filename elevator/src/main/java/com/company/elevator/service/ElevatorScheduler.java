package com.company.elevator.service;

import com.company.elevator.model.ExteriorCall;
import com.company.elevator.model.Floor;
import com.company.elevator.model.InteriorCall;

import java.util.LinkedList;
import java.util.List;

/**
 * @author user
 */
public interface ElevatorScheduler {
    /**
     * @param exteriorCall
     */
    void acceptCall(ExteriorCall exteriorCall);

    /**
     * @param interiorCall
     */
    void acceptInterior(InteriorCall interiorCall);

    /**
     *
     * @param elevatorService
     */
    void schedule(ElevatorService elevatorService);

    /**
     *
     * @param floor
     * @return
     */
    boolean remove(Floor floor);

    /**
     *
     * @return target floors
     */
    LinkedList<Floor> getTargetFloors();
}
