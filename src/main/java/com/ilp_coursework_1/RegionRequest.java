package com.ilp_coursework_1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RegionRequest {
    @Valid
    @NotNull(message = "position not found")
    private Position position;

    @Valid
    @NotNull(message = "region not found")
    private Region region;

    // Getters and Setters
    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public Region getRegion() {return region;}
    public void setRegion(Region region) {this.region = region;}

}
