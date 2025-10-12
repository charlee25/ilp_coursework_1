package com.ilp_coursework_1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class NextPositionRequest {
    @Valid
    @NotNull(message = "start not found")
    private Position start;

    @Valid
    @NotNull(message = "angle not found")
    private Double angle;

    // Getters and Setters
    public Position getStart() {
        return start;
    }
    public void setStart(Position start) {
        this.start = start;
    }
    public Double getAngle() {
        return angle;
    }
    public void setAngle(Double angle) {
        this.angle = angle;
    }
}
