package com.company.elevator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author user
 */
@Setter
@Getter
@AllArgsConstructor
public final class ExteriorCall {
    @NonNull
    private Floor at;
    @NonNull
    private Direction direction;
    @NonNull
    private Elevator elevator;
}
