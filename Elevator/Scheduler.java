package Elevator;

import java.util.List;

public class Scheduler {
    private Algorithm algo;

    public Scheduler(Algorithm algo) {
        this.algo = algo;
    }

    public List<Request> schedule(List<Request> requests) {
        return algo.sort(requests);
    }
}
