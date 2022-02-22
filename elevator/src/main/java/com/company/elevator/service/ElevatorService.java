package com.company.elevator.service;

import com.company.elevator.model.Floor;

/**
 * @author user
 */
public interface ElevatorService {
    /**
     * move the elevator based on next stop
     */
    void move();
    /**
     * start elevator service for elevator
     */
    void start();

    /**
     * stop elevator service for elevator
     */
    void stop();
}
