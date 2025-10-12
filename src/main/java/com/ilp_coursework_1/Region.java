package com.ilp_coursework_1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class Region {
    @NotNull(message = "name not found")
    private String name;
    @Valid
    @NotNull(message = "vertices not found")
    private List<Position> vertices;

    public Region() {
    }
    public Region(String name, List<Position> vertices) {
        this.name = name;
        this.vertices = vertices;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public List<Position> getVertices() {return vertices;}
    public void setVertices(List<Position> vertices) {this.vertices = vertices;}

    public boolean checkValid() {
        if (name == null || name.isEmpty() || vertices == null || vertices.size() < 4) {
            return false;
        }

        for (Position vertex : vertices) {
            if (vertex == null || vertex.getLat() == null || vertex.getLng() == null) {
                return false;
            }
        }

        // First and last must be the same
        if (!vertices.getFirst().equals(vertices.getLast())) {
            return false;
        }

        // No duplicate vertices, except the first and last being equal
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                // Allow only the first and last vertex to be equal
                boolean isFirstLastPair = (i == 0 && j == vertices.size() - 1);
                if (!isFirstLastPair && vertices.get(i).equals(vertices.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
