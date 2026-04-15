package Pen;

public class Refill {
    private final String color;
    private final int capacity;
    private int remaining;

    public Refill(String color, int capacity) {
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color is required.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        this.color = color;
        this.capacity = capacity;
        this.remaining = capacity;
    }

    public String getColor() {
        return color;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRemaining() {
        return remaining;
    }

    public boolean canWrite(int units) {
        return units > 0 && remaining >= units;
    }

    public void consume(int units) {
        if (!canWrite(units)) {
            throw new IllegalStateException("Not enough ink in refill.");
        }
        remaining -= units;
    }

    @Override
    public String toString() {
        return "Refill{" +
                "color='" + color + '\'' +
                ", remaining=" + remaining +
                "/" + capacity +
                '}';
    }
}
