package LLD_Assignment.Design_Assignment.SnakeLadder;

public class MovementResolver {

    public int resolveNextPosition(int current, int roll, Board board) {
        if (roll < 1 || roll > 6) {
            throw new IllegalArgumentException("Roll must be between 1 and 6.");
        }
        if (current < 0) {
            throw new IllegalArgumentException("Current position cannot be negative.");
        }

        int newPosition = current + roll;

        if (newPosition > board.getLastCell()) {
            return current;
        }

        Integer jumpTarget = board.getJumpTarget(newPosition);
        if (jumpTarget != null) {
            return jumpTarget;
        }

        return newPosition;
    }
}
