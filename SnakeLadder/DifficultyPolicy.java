package LLD_Assignment.Design_Assignment.SnakeLadder;

public interface DifficultyPolicy {
    TurnAction afterRoll(Player player, int roll);
}
