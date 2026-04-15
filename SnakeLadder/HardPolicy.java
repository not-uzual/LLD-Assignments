package SnakeLadder;

public class HardPolicy implements DifficultyPolicy {
    private int consecutiveSixes;
    private Player lastPlayerWithSix;

    public HardPolicy() {
        this.consecutiveSixes = 0;
        this.lastPlayerWithSix = null;
    }

    @Override
    public TurnAction afterRoll(Player player, int roll) {
        if (player == null) {
            throw new IllegalArgumentException("Player is required.");
        }

        if (roll == 6) {
            if (lastPlayerWithSix != null && lastPlayerWithSix.getId().equals(player.getId())) {
                consecutiveSixes++;
            } else {
                consecutiveSixes = 1;
                lastPlayerWithSix = player;
            }

            if (consecutiveSixes >= 3) {
                consecutiveSixes = 0;
                lastPlayerWithSix = null;
                return TurnAction.SKIP_TURN;
            }

            return TurnAction.EXTRA_TURN;
        }

        consecutiveSixes = 0;
        lastPlayerWithSix = null;
        return TurnAction.CONTINUE;
    }
}
