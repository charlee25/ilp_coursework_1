package com.ilp_coursework_1;

import java.util.List;

public class Region {
    private String name;
    private List<Position> vertices;

    public Region(String name, List<Position> vertices) {
        this.name = name;
        this.vertices = vertices;
    }

    public String getName() {return name;}
    public List<Position> getVertices() {return vertices;}


}
