package com.company.elevator.service;

import com.company.elevator.model.Floor;

import java.util.Comparator;

/**
 * @author user
 */

public enum SimulatorComparatorFactory {

    /**
     *
     */
    FLOOR_COMPARATOR ("floorComparator") {
        @Override
        public int floorCompare(Floor t1, Floor t2) {
            return t1.compareTo(t2);
        }
    };

    public abstract int floorCompare(Floor f1, Floor f2);

    SimulatorComparatorFactory(String floorComparator) {
    }
}
