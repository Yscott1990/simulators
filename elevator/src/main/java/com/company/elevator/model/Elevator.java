package com.company.elevator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author user
 */

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Elevator {
    private short id;
    private Direction direction = Direction.NONE;
    private ElevatorStatus elevatorStatus = ElevatorStatus.STOPPED;
    private Floor currentFloor;
    private DoorStatus doorStatus = DoorStatus.CLOSED;
}
