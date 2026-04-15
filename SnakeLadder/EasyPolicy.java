package SnakeLadder;

public class EasyPolicy implements DifficultyPolicy {

    @Override
    public TurnAction afterRoll(Player player, int roll) {
        if (player == null) {
            throw new IllegalArgumentException("Player is required.");
        }

        if (roll == 6) {
            return TurnAction.EXTRA_TURN;
        }

        return TurnAction.CONTINUE;
    }
}
