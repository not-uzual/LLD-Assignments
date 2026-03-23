package LLD_Assignment.Design_Assignment.ParkingLot;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int floorNumber;
    private final List<ParkingSpot> spots;
    private final List<Gate> gates;

    public Level(int floorNumber) {
        this.floorNumber = floorNumber;
        this.spots = new ArrayList<>();
        this.gates = new ArrayList<>();
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSpot> getSpots() {
        return spots;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public void addSpot(ParkingSpot spot) {
        if (spot.getLevelNumber() != floorNumber) {
            throw new IllegalArgumentException("Spot level does not match this level.");
        }
        this.spots.add(spot);
    }

    public void addGate(Gate gate) {
        if (gate.getLevelNumber() != floorNumber) {
            throw new IllegalArgumentException("Gate level does not match this level.");
        }
        this.gates.add(gate);
    }
}