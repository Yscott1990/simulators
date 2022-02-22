package com.company.elevator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author user
 */
@Setter
@Getter
@AllArgsConstructor
public class InteriorCall {
    private Floor targetFloor;
}
