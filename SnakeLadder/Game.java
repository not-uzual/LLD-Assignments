package SnakeLadder;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private final TurnManager turnManager;
    private final MovementResolver movementResolver;
    private final DifficultyPolicy difficultyPolicy;
    private final List<Player> winners;
    private boolean finished;

    public Game(int boardDimension, List<Player> players, String difficultyLevel) {
        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players required.");
        }
        if (difficultyLevel == null || (!difficultyLevel.equalsIgnoreCase("easy") && !difficultyLevel.equalsIgnoreCase("hard"))) {
            throw new IllegalArgumentException("Difficulty level must be 'easy' or 'hard'.");
        }

        BoardGenerator generator = new BoardGenerator();
        this.board = generator.generate(boardDimension);
        this.players = new ArrayList<>(players);
        this.dice = new Dice(6);
        this.turnManager = new TurnManager(players);
        this.movementResolver = new MovementResolver();
        this.difficultyPolicy = difficultyLevel.equalsIgnoreCase("easy") ? new EasyPolicy() : new HardPolicy();
        this.winners = new ArrayList<>();
        this.finished = false;
    }

    public void start() {
        System.out.println("=== Snake and Ladder Game Started ===");
        System.out.println(board);
        System.out.println("Players: " + players.size());
        for (Player p : players) {
            System.out.println("  - " + p.getName());
        }
        System.out.println();
    }

    public void playTurn() {
        if (finished) {
            throw new IllegalStateException("Game is already finished.");
        }

        Player currentPlayer = turnManager.nextPlayer();
        System.out.println("\n[Turn] " + currentPlayer.getName() + " (Position: " + currentPlayer.getPosition() + ")");

        int roll = dice.roll();
        System.out.println("  Rolled: " + roll);

        int nextPosition = movementResolver.resolveNextPosition(currentPlayer.getPosition(), roll, board);
        currentPlayer.moveTo(nextPosition);

        System.out.println("  Moved to: " + nextPosition);

        if (board.hasSnake(nextPosition)) {
            Integer tail = board.getJumpTarget(nextPosition);
            System.out.println("  Snake! Down to: " + tail);
        } else if (board.hasLadder(nextPosition)) {
            Integer top = board.getJumpTarget(nextPosition);
            System.out.println("  Ladder! Up to: " + top);
        }

        if (nextPosition == board.getLastCell()) {
            currentPlayer.markWon();
            winners.add(currentPlayer);
            turnManager.updateAfterTurn(currentPlayer);
            System.out.println("  *** " + currentPlayer.getName() + " WINS! ***");
        } else {
            turnManager.updateAfterTurn(currentPlayer);
        }

        TurnAction action = difficultyPolicy.afterRoll(currentPlayer, roll);
        switch (action) {
            case EXTRA_TURN:
                System.out.println("  Extra turn granted!");
                playTurn();
                break;
            case SKIP_TURN:
                System.out.println("  Turn skipped!");
                break;
            case CONTINUE:
            default:
                break;
        }

        if (turnManager.getActivePlayerCount() < 2) {
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public List<Player> getWinners() {
        return new ArrayList<>(winners);
    }

    public void endGame() {
        System.out.println("\n=== Game Finished ===");
        System.out.println("Winners: " + winners.size());
        for (int i = 0; i < winners.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + winners.get(i).getName());
        }
    }
}
