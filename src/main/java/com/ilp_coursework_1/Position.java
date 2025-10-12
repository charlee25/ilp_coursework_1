package com.ilp_coursework_1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class Position {
    @NotNull(message = "Latitude not found")
    private Double lat;
    @NotNull(message = "Longitude not found")
    private Double lng;

    public Position() {
    }
    public Position(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {return lat;}
    public void setLat(Double lat) {this.lat = lat;}
    public Double getLng() {return lng;}
    public void setLng(Double lng) {this.lng = lng;}

}
