package com.company.elevator.model;

import lombok.*;

import java.util.Objects;

/**
 * @author user
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Floor implements Comparable<Floor>{
    private int floorNumber;
    @Override
    public int compareTo(Floor o) {
        return this.floorNumber - o.getFloorNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Floor floor = (Floor) o;
        return floorNumber == floor.floorNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floorNumber);
    }
}
