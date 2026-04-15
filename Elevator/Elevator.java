package Elevator;

import java.util.ArrayList;
import java.util.List;


public class Elevator {
    private int id;
    private int currentFloor;
    private Direction currentDirection;
    private List<Request> requests;
    private Scheduler scheduler;
    private int minFloor;
    private int maxFloor;

    public Elevator(int id, Scheduler scheduler, int minFloor, int maxFloor) {
        this.id = id;
        this.currentFloor = minFloor;
        this.currentDirection = Direction.IDLE;
        this.requests = new ArrayList<>();
        this.scheduler = scheduler;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    public void addRequest(Request request) {
        // Avoid duplicate requests for the same floor
        boolean exists = requests.stream().anyMatch(r -> r.getFloor() == request.getFloor());
        if (!exists) {
            requests.add(request);
            requests = scheduler.schedule(requests);
        }
    }

    public void addInternalRequest(int floor) {
        if (floor >= minFloor && floor <= maxFloor) {
            addRequest(new Request(floor, RequestType.INTERNAL));
        }
    }

    public void step() {
        if (requests.isEmpty()) {
            currentDirection = Direction.IDLE;
            return;
        }

        Request target = requests.get(0);
        int targetFloor = target.getFloor();

        if (targetFloor > currentFloor) {
            currentFloor++;
            currentDirection = Direction.UP;
        } else if (targetFloor < currentFloor) {
            currentFloor--;
            currentDirection = Direction.DOWN;
        } else {
            // Reached the target floor
            requests.remove(0);
            // Update direction based on remaining requests
            if (!requests.isEmpty()) {
                Request nextTarget = requests.get(0);
                if (nextTarget.getFloor() > currentFloor) {
                    currentDirection = Direction.UP;
                } else if (nextTarget.getFloor() < currentFloor) {
                    currentDirection = Direction.DOWN;
                }
            } else {
                currentDirection = Direction.IDLE;
            }
        }
    }

    public int getFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return currentDirection;
    }

    public int getId() {
        return id;
    }

    public int getRequestCount() {
        return requests.size();
    }

}
