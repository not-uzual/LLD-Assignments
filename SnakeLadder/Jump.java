package LLD_Assignment.Design_Assignment.SnakeLadder;

public class Jump {
    private final int from;
    private final int to;

    public Jump(int from, int to) {
        if (from <= 0) {
            throw new IllegalArgumentException("Jump source must be positive.");
        }
        if (to <= 0) {
            throw new IllegalArgumentException("Jump destination must be positive.");
        }
        if (from == to) {
            throw new IllegalArgumentException("Jump source and destination cannot be same.");
        }
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean isSnake() {
        return from > to;
    }

    public boolean isLadder() {
        return from < to;
    }

    @Override
    public String toString() {
        String type = isSnake() ? "Snake" : "Ladder";
        return type + " {" + from + " -> " + to + '}';
    }
}
