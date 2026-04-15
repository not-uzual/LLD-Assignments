package SnakeLadder;

public interface DifficultyPolicy {
    TurnAction afterRoll(Player player, int roll);
}
