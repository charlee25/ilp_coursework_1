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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;

        double epsilon = 1e-9;
        return Math.abs(this.lat - other.lat) < epsilon &&
                Math.abs(this.lng - other.lng) < epsilon;
    }

    @Override
    public int hashCode() {
        long tempLat = Double.doubleToLongBits(lat);
        long tempLng = Double.doubleToLongBits(lng);
        return (int) (tempLat ^ (tempLat >>> 32)) ^
                (int) (tempLng ^ (tempLng >>> 32));
    }


}
