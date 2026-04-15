package Elevator;

import java.util.List;

public class FIFOAlgorithm implements Algorithm {
    @Override
    public List<Request> sort(List<Request> requests) {
        return requests; // no change
    }
}
