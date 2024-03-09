package tictactoe;


public class Player {
    private final char symbol;

    private final PlayerLevel level;

    private MiniMax minimax;

    private boolean isMaximising;

    public Player(char symbol, PlayerLevel level) {
        this.symbol = symbol;
        this.level = level;
        this.minimax = null;
    }

    public char getSymbol() {
        return symbol;
    }

    public PlayerLevel getLevel() {
        return level;
    }

    public MiniMax getMinimax() {
        return minimax;
    }

    public void maximize() {
        this.minimax = MiniMax.MAXIMIZING;
        this.isMaximising = true;
    }

    public void minimize() {
        this.minimax = MiniMax.MINIMIZING;
        this.isMaximising = false;
    }

    public boolean isMaximising() {
        return isMaximising;
    }
}