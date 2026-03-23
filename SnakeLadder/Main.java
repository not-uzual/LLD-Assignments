package LLD_Assignment.Design_Assignment.SnakeLadder;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int boardDimension = 10;
        String difficultyLevel = "easy";

        List<Player> players = new ArrayList<>();
        players.add(new Player("P1", "Alice"));
        players.add(new Player("P2", "Bob"));
        players.add(new Player("P3", "Charlie"));

        Game game = new Game(boardDimension, players, difficultyLevel);
        game.start();

        while (!game.isFinished()) {
            game.playTurn();
        }

        game.endGame();
    }
}
