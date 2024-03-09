package tictactoe;

public enum MiniMax {
    MAXIMIZING(1),
    MINIMIZING(-1);

    private final int score;

    MiniMax(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
