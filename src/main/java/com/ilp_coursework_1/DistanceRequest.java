package com.ilp_coursework_1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class DistanceRequest {
    @Valid
    @NotNull(message = "position1 not found")
    private Position position1;

    @Valid
    @NotNull(message = "position2 not found")
    private Position position2;

    // Getters and Setters
    public Position getPosition1() {
        return position1;
    }

    public void setPosition1(Position position1) {
        this.position1 = position1;
    }

    public Position getPosition2() {
        return position2;
    }

    public void setPosition2(Position position2) {
        this.position2 = position2;
    }
}

