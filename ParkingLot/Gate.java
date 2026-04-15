package ParkingLot;

public class Gate {
    private final int id;
    private final int levelNumber;
    private final int position;

    public Gate(int id, int levelNumber, int position) {
        if (id <= 0) {
            throw new IllegalArgumentException("Gate id must be positive.");
        }
        if (levelNumber < 0) {
            throw new IllegalArgumentException("Level number cannot be negative.");
        }
        if (position < 0) {
            throw new IllegalArgumentException("Gate position cannot be negative.");
        }
        this.id = id;
        this.levelNumber = levelNumber;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getPosition() {
        return position;
    }
}
