package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFour extends JFrame {
    private boolean isXTurn = true;
    private boolean gameFinished = false;
    private final Board board;
    private final JButton[][] buttons;
    private final Color baselineColor = Color.LIGHT_GRAY;
    private final Color winningColor = Color.YELLOW;

    public ConnectFour() {
        board = new Board();
        buttons = new JButton[6][7];
        setTitle("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(6, 7));
        add(boardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        add(controlPanel, BorderLayout.SOUTH);

        JButton resetButton = new JButton("Reset");
        resetButton.setName("ButtonReset");
        resetButton.addActionListener(e -> resetGame());
        controlPanel.add(resetButton);

        // Create the cells
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                JButton cell = new JButton(" ");
                cell.setBackground(baselineColor);
                cell.setFocusPainted(false);  // Remove the highlighting on clicked cells
                cell.setFont(new Font("Arial", Font.PLAIN, 24));
                cell.setName("Button" + (char)('A' + col) + (6 - row));
                cell.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!gameFinished) {
                            JButton clickedButton = (JButton) e.getSource();
                            int column = clickedButton.getName().charAt(6) - 'A';
                            handleCellClick(column);
                        }
                    }
                });
                buttons[row][col] = cell;
                boardPanel.add(cell);
            }
        }

        setLocationRelativeTo(null);  // Center the window
        setVisible(true);
    }

    private void handleCellClick(int column) {
        if (!board.placePiece(column, isXTurn ? "X" : "O")) {
            return;  // Column is full, do nothing
        }

        updateBoardUI();

        if (board.checkWin(isXTurn ? "X" : "O")) {
            gameFinished = true;
            highlightWinningCells(isXTurn ? "X" : "O");
        } else {
            isXTurn = !isXTurn;  // Switch turn
        }
    }

    private void updateBoardUI() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                buttons[row][col].setText(board.getPiece(row, col));
            }
        }
    }

    private void highlightWinningCells(String piece) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (checkAndHighlightDirection(row, col, 1, 0, piece) ||  // Horizontal
                        checkAndHighlightDirection(row, col, 0, 1, piece) ||  // Vertical
                        checkAndHighlightDirection(row, col, 1, 1, piece) ||  // Diagonal \
                        checkAndHighlightDirection(row, col, 1, -1, piece)) { // Diagonal /
                    return;
                }
            }
        }
    }

    private boolean checkAndHighlightDirection(int row, int col, int rowDir, int colDir, String piece) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int r = row + i * rowDir;
            int c = col + i * colDir;
            if (r < 0 || r >= 6 || c < 0 || c >= 7 || !board.getPiece(r, c).equals(piece)) {
                break;
            }
            count++;
        }
        if (count == 4) {
            for (int i = 0; i < 4; i++) {
                int r = row + i * rowDir;
                int c = col + i * colDir;
                buttons[r][c].setBackground(winningColor);
            }
            return true;
        }
        return false;
    }

    private void resetGame() {
        board.resetBoard();
        gameFinished = false;
        isXTurn = true;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                buttons[row][col].setText(" ");
                buttons[row][col].setBackground(baselineColor);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ConnectFour();
            }
        });
    }
}
