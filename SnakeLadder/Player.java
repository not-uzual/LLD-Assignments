package SnakeLadder;

public class Player {
    private final String id;
    private final String name;
    private int position;
    private boolean won;

    public Player(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Player id is required.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name is required.");
        }
        this.id = id;
        this.name = name;
        this.position = 0;
        this.won = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void moveTo(int cell) {
        if (cell < 0) {
            throw new IllegalArgumentException("Position cannot be negative.");
        }
        this.position = cell;
    }

    public boolean hasWon() {
        return won;
    }

    public void markWon() {
        this.won = true;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", won=" + won +
                '}';
    }
}
