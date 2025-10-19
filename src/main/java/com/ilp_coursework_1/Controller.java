package com.ilp_coursework_1;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.HashMap;

@RestController
public class Controller {
    //Global Variables
    double threshold = 0.00015;
    int decimalPlaces = 6;
    double angleIncrement = 22.5;

    //2) UID
    @GetMapping("/api/v1/uid")
    public String getUid() {return "s2479712";}

    //3) distanceTo
    @PostMapping("/api/v1/distanceTo")
    public ResponseEntity<Double> getDistance(@Valid @RequestBody DistanceRequest request) {
        Position pos1 = request.getPosition1();
        Position pos2 = request.getPosition2();

        Double distance = CalculateDistance(pos1, pos2);
        return ResponseEntity.ok(distance);
    }

    //4) isCloseTo
    @PostMapping("/api/v1/isCloseTo")
    public ResponseEntity<Boolean> isCloseTo(@Valid @RequestBody DistanceRequest request){
        Position pos1 = request.getPosition1();
        Position pos2 = request.getPosition2();

        double distance = CalculateDistance(pos1, pos2);
        return ResponseEntity.ok(distance < threshold);
    }

    //5) nextPosition
    @PostMapping("/api/v1/nextPosition")
    public ResponseEntity<?> nextPosition(@Valid @RequestBody NextPositionRequest request){

        Position start = request.getStart();
        double angle = request.getAngle(); // Angle in DEGREES

        //Check angle is a multiple of 22.5
        if (angle % angleIncrement == 0 && angle < 360 && angle >= 0) {

        double deltaLat = threshold * Math.cos(Math.toRadians(angle));
        double deltaLng = threshold * Math.sin(Math.toRadians(angle));

        double newLat = start.getLat() + deltaLat;
        double newLng = start.getLng() + deltaLng;

        double roundedLat = Rounding(newLat, decimalPlaces);
        double roundedLng = Rounding(newLng, decimalPlaces);

        return ResponseEntity.ok(new Position(roundedLat, roundedLng));
        }
        else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Angle must be a multiple of " + angleIncrement + " degrees and between 0-360 degrees");
            return ResponseEntity.badRequest().body(error);
        }
    }

    //6) isInRegion
    @PostMapping("/api/v1/isInRegion")
    public ResponseEntity<?> isInRegion(@Valid @RequestBody RegionRequest request) {
        Position point = request.getPosition();
        Region region = request.getRegion();

        //Check list of vertices match requirements
        if (!region.checkValid()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Region not valid. Must have at least 4 vertices, first and last vertex must be the same, no duplicate vertices allowed (except first and last)");
            return ResponseEntity.badRequest().body(error);
        }

        int intersectCount = 0;
        for (int i = 0; i < region.getVertices().size(); i++) {
            Position v1 = region.getVertices().get(i);
            Position v2 = region.getVertices().get((i + 1) % region.getVertices().size());

            if (rayIntersectsSegment(point, v1, v2)) {
                intersectCount++;
            }
        }

        return ResponseEntity.ok(intersectCount % 2 == 1);
    }

    //--------------------------------------Helper functions----------------------------------------------
    public double CalculateDistance(Position pos1, Position pos2){
        Double lat1 = pos1.getLat();
        Double lng1 = pos1.getLng();
        Double lat2 = pos2.getLat();
        Double lng2 = pos2.getLng();

        double distance = Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lng2 - lng1, 2));
        return Rounding(distance, decimalPlaces);
    }

    public double Rounding (double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public boolean rayIntersectsSegment(Position point, Position v1, Position v2) {
        double px = point.getLng();
        double py = point.getLat();
        double x1 = v1.getLng();
        double y1 = v1.getLat();
        double x2 = v2.getLng();
        double y2 = v2.getLat();

        if (y1 > y2) {
            double tempX = x1;
            double tempY = y1;
            x1 = x2;
            y1 = y2;
            x2 = tempX;
            y2 = tempY;
        }

        if (py == y1 || py == y2) py += 0.00000001;

        if (py < y1 || py > y2) return false;
        if (px >= Math.max(x1, x2)) return false;

        if (px < Math.min(x1, x2)) return true;

        double slopeSeg1 = (x2 - x1) != 0 ? (y2 - y1) / (x2 - x1) : Double.MAX_VALUE;
        double slopeSeg2 = (px - x1) != 0 ? (py - y1) / (px - x1) : Double.MAX_VALUE;
        return slopeSeg2 >= slopeSeg1;
    }
}
