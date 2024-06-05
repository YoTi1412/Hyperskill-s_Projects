package four;

public class Board {
    private final String[][] board;
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;

    public Board() {
        board = new String[ROWS][COLUMNS];
        // Initialize the board with spaces
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = " ";
            }
        }
    }

    public int getFirstFreeRow(int column) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][column].equals(" ")) {
                return i;
            }
        }
        return -1;  // Column is full
    }

    public boolean placePiece(int column, String piece) {
        int row = getFirstFreeRow(column);
        if (row != -1) {
            board[row][column] = piece;
            return true;
        }
        return false;
    }

    public String getPiece(int row, int column) {
        return board[row][column];
    }

    public boolean checkWin(String piece) {
        // Check horizontal, vertical, and both diagonal directions
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (checkDirection(row, col, 1, 0, piece) ||  // Horizontal
                        checkDirection(row, col, 0, 1, piece) ||  // Vertical
                        checkDirection(row, col, 1, 1, piece) ||  // Diagonal \
                        checkDirection(row, col, 1, -1, piece)) { // Diagonal /
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int rowDir, int colDir, String piece) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int r = row + i * rowDir;
            int c = col + i * colDir;
            if (r < 0 || r >= ROWS || c < 0 || c >= COLUMNS || !board[r][c].equals(piece)) {
                break;
            }
            count++;
        }
        return count == 4;
    }

    public void resetBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = " ";
            }
        }
    }
}
