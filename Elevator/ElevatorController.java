package Elevator;

import java.util.ArrayList;
import java.util.List;

public class ElevatorController {
    private List<Elevator> elevators;
    private Scheduler scheduler;
    private static final int MIN_FLOOR = 0;
    private static final int MAX_FLOOR = 10;
    private static final int NUM_ELEVATORS = 3;

    public ElevatorController() {
        this.scheduler = new Scheduler(new FIFOAlgorithm());
        this.elevators = new ArrayList<>();

        for (int i = 0; i < NUM_ELEVATORS; i++) {
            elevators.add(new Elevator(i, scheduler, MIN_FLOOR, MAX_FLOOR));
        }
    }

    public void requestElevator(int floor, Direction direction) {
        validateFloor(floor);
        Request request = new Request(floor, RequestType.EXTERNAL);

        Elevator bestElevator = findBestElevator(floor, direction);
        bestElevator.addRequest(request);
    }

    private void validateFloor(int floor) {
        if (floor < MIN_FLOOR || floor > MAX_FLOOR) {
            throw new IllegalArgumentException("Floor " + floor + " is out of range [" + MIN_FLOOR + ", " + MAX_FLOOR + "]");
        }
    }

    private Elevator findBestElevator(int floor, Direction direction) {
        Elevator best = elevators.get(0);
        int bestScore = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            // Prefer idle elevators
            if (e.getDirection() == Direction.IDLE) {
                int distance = Math.abs(e.getFloor() - floor);
                if (distance < bestScore) {
                    bestScore = distance;
                    best = e;
                }
            }
        }

        // If no idle elevator, find one moving in the same direction
        if (best.getDirection() != Direction.IDLE) {
            for (Elevator e : elevators) {
                if (e.getDirection() == direction) {
                    int distance = Math.abs(e.getFloor() - floor);
                    if (distance < bestScore) {
                        bestScore = distance;
                        best = e;
                    }
                }
            }
        }

        // Fallback to closest elevator
        if (best.getDirection() != Direction.IDLE) {
            bestScore = Integer.MAX_VALUE;
            for (Elevator e : elevators) {
                int distance = Math.abs(e.getFloor() - floor);
                if (distance < bestScore) {
                    bestScore = distance;
                    best = e;
                }
            }
        }

        return best;
    }

    public void step() {
        for (Elevator e : elevators) {
            e.step();
        }
    }
}