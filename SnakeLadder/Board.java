package SnakeLadder;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private final int dimension;
    private final int lastCell;
    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;

    public Board(int dimension, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("Board dimension must be positive.");
        }
        if (snakes == null) {
            throw new IllegalArgumentException("Snakes map is required.");
        }
        if (ladders == null) {
            throw new IllegalArgumentException("Ladders map is required.");
        }

        this.dimension = dimension;
        this.lastCell = dimension * dimension;
        this.snakes = new HashMap<>(snakes);
        this.ladders = new HashMap<>(ladders);

        validateBoardIntegrity();
    }

    private void validateBoardIntegrity() {
        for (Integer head : snakes.keySet()) {
            if (head <= 0 || head > lastCell) {
                throw new IllegalArgumentException("Snake head out of bounds: " + head);
            }
            Integer tail = snakes.get(head);
            if (tail <= 0 || tail >= head) {
                throw new IllegalArgumentException("Invalid snake: " + head + " -> " + tail);
            }
        }

        for (Integer start : ladders.keySet()) {
            if (start <= 0 || start > lastCell) {
                throw new IllegalArgumentException("Ladder start out of bounds: " + start);
            }
            Integer end = ladders.get(start);
            if (end <= start || end > lastCell) {
                throw new IllegalArgumentException("Invalid ladder: " + start + " -> " + end);
            }
        }
    }

    public int getDimension() {
        return dimension;
    }

    public int getLastCell() {
        return lastCell;
    }

    public Integer getJumpTarget(int cell) {
        if (snakes.containsKey(cell)) {
            return snakes.get(cell);
        }
        if (ladders.containsKey(cell)) {
            return ladders.get(cell);
        }
        return null;
    }

    public boolean hasSnake(int cell) {
        return snakes.containsKey(cell);
    }

    public boolean hasLadder(int cell) {
        return ladders.containsKey(cell);
    }

    public Map<Integer, Integer> getSnakes() {
        return new HashMap<>(snakes);
    }

    public Map<Integer, Integer> getLadders() {
        return new HashMap<>(ladders);
    }

    @Override
    public String toString() {
        return "Board{" +
                "dimension=" + dimension +
                ", lastCell=" + lastCell +
                ", snakes=" + snakes.size() +
                ", ladders=" + ladders.size() +
                '}';
    }
}
