package LLD_Assignment.Design_Assignment.SnakeLadder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TurnManager {
    private final Queue<Player> activeOrder;

    public TurnManager(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("At least one player is required.");
        }
        this.activeOrder = new LinkedList<>(players);
    }

    public Player nextPlayer() {
        if (activeOrder.isEmpty()) {
            throw new IllegalStateException("No active players remaining.");
        }
        Player player = activeOrder.poll();
        activeOrder.offer(player);
        return player;
    }

    public void updateAfterTurn(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player is required.");
        }

        if (player.hasWon()) {
            activeOrder.remove(player);
        }
    }

    public int getActivePlayerCount() {
        return activeOrder.size();
    }

    public List<Player> getActivePlayers() {
        return new ArrayList<>(activeOrder);
    }
}
