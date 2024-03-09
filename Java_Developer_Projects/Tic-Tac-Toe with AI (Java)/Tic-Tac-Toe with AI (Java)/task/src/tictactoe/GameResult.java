package tictactoe;

public enum GameResult {
    GAME_CONTINUES("Game not finished"),
    DRAW("Draw"),
    X_WINS("X wins"),
    O_WINS("O wins"),
    IMPOSSIBLE("Impossible");

    private final String message;
    GameResult(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
