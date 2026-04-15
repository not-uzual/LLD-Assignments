package SnakeLadder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoardGenerator {

    public Board generate(int dimension) {
        if (dimension <= 2) {
            throw new IllegalArgumentException("Board dimension must be at least 3.");
        }

        int lastCell = dimension * dimension;
        int snakeCount = dimension;
        int ladderCount = dimension;

        Map<Integer, Integer> snakes = new HashMap<>();
        Map<Integer, Integer> ladders = new HashMap<>();
        Set<Integer> occupiedCells = new HashSet<>();

        placeSnakes(lastCell, snakeCount, snakes, occupiedCells);
        placeLadders(lastCell, ladderCount, ladders, occupiedCells, snakes);

        return new Board(dimension, snakes, ladders);
    }

    private void placeSnakes(int lastCell, int count, Map<Integer, Integer> snakes, Set<Integer> occupiedCells) {
        int placed = 0;
        while (placed < count) {
            int head = (int) (Math.random() * (lastCell - 1)) + 2;

            if (occupiedCells.contains(head)) {
                continue;
            }

            int tail = (int) (Math.random() * (head - 1)) + 1;

            if (occupiedCells.contains(tail)) {
                continue;
            }

            snakes.put(head, tail);
            occupiedCells.add(head);
            occupiedCells.add(tail);
            placed++;
        }
    }

    private void placeLadders(int lastCell, int count, Map<Integer, Integer> ladders,
                              Set<Integer> occupiedCells, Map<Integer, Integer> snakes) {
        int placed = 0;
        while (placed < count) {
            int start = (int) (Math.random() * (lastCell - 1)) + 1;

            if (occupiedCells.contains(start) || snakes.containsKey(start)) {
                continue;
            }

            int end = (int) (Math.random() * (lastCell - start)) + start + 1;

            if (occupiedCells.contains(end) || snakes.containsValue(end)) {
                continue;
            }

            ladders.put(start, end);
            occupiedCells.add(start);
            occupiedCells.add(end);
            placed++;
        }
    }
}
