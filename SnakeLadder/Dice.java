package SnakeLadder;

public class Dice {
    private final int faces;

    public Dice(int faces) {
        if (faces <= 0) {
            throw new IllegalArgumentException("Dice faces must be positive.");
        }
        this.faces = faces;
    }

    public int roll() {
        return (int) (Math.random() * faces) + 1;
    }

    public int getFaces() {
        return faces;
    }
}
