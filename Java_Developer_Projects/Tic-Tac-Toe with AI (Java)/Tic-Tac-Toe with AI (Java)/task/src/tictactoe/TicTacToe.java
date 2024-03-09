package tictactoe;


import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe implements Runnable {
    private final char EMPTY_CELL = ' ';
    private static final Runnable BAD_PARAMETERS = () -> System.out.println("Bad parameters!");
    private final Scanner scanner;
    private GameResult gameStatus;

    char[][] gameBoard;
    private GameAction command;

    private Player player1;
    private Player player2;

    private Player currentPlayer;
    private Player opponent;

    public TicTacToe(Scanner scanner) {
        this.scanner = scanner;
        this.gameStatus = GameResult.GAME_CONTINUES;
        this.command = GameAction.INIT_PLAYERS;
        initPlayers();
    }

    @Override
    public void run() {
        if (command != GameAction.EXIT) {
            gameBoard = initGameBoard();

            printGameBoard();

            while (gameStatus == GameResult.GAME_CONTINUES) {
                int[] coordinates = getValidCoordinates();

                makeTheMove(coordinates);

                notifyMoveFromAI(currentPlayer.getLevel());

                printGameBoard();

                printGameResult();

                toggleTurn();
            }
        }
    }

    private void initPlayers() {
        String input = null;

        while (command != GameAction.START) {
            System.out.print("Input command: ");
            input = scanner.nextLine();

            command = validateInput(input);

            if (command == GameAction.EXIT) {
                return;
            }
        }

        this.player1 = new Player('X', PlayerLevel.valueOf(input.split("\\s")[1].toUpperCase()));
        this.player2 = new Player('O', PlayerLevel.valueOf(input.split("\\s")[2].toUpperCase()));

        toggleTurn();

    }

    private GameAction validateInput(String input) {
        String[] params = input.split("\\s");

        GameAction action = validateCommand(params[0]);

        if (action == GameAction.EXIT) {
            return GameAction.EXIT;
        }

        if (params.length < 3) {
            BAD_PARAMETERS.run();
            return GameAction.INIT_PLAYERS;
        }

        if (params.length == 3) {
            action = validatePlayers(params);
            if (action != GameAction.START) {
                BAD_PARAMETERS.run();
                return GameAction.INIT_PLAYERS;
            }
        }

        return GameAction.START;
    }

    private GameAction validatePlayers(String[] params) {
        if (validPlayer(params[1]) && validPlayer(params[2])) {
            return GameAction.START;
        }

        return GameAction.INIT_PLAYERS;
    }

    private boolean validPlayer(String player) {

        PlayerLevel level = PlayerLevel.getByDescription(player);
        return level == PlayerLevel.USER || level == PlayerLevel.EASY || level == PlayerLevel.MEDIUM || level == PlayerLevel.HARD;
    }

    private static GameAction validateCommand(String command) {
        switch (command) {
            case "start":
                return GameAction.VALIDATE_PLAYERS;
            case "exit":
                return GameAction.EXIT;
            default:
                BAD_PARAMETERS.run();
        }

        return GameAction.INIT_PLAYERS;
    }

    private void printGameResult() {
        gameStatus = checkGame();

        if (!gameStatus.equals(GameResult.GAME_CONTINUES)) {
            System.out.println(gameStatus.getMessage());
        }
    }

    private void makeTheMove(int[] coordinates) {
        int row = coordinates[0] - 1;
        int col = coordinates[1] - 1;

        char symbol = currentPlayer.getSymbol();
        gameBoard[row][col] = symbol;
    }

    private void notifyMoveFromAI(PlayerLevel level) {
        if (level != PlayerLevel.USER) {
            System.out.println("Making move level \"" + currentPlayer.getLevel().name().toLowerCase() + "\"");
        }
    }

    private int[] getValidCoordinates() {
        int[] coordinates;

        while (true) {
            String values = getCoordinatesForCurrentPlayer();

            if (values.matches("\\D*")) {
                notifyOnlyToHuman(() -> System.out.println("You should enter numbers!"));
            } else if (!values.matches("[1-3]\\s[1-3]")) {
                notifyOnlyToHuman(() -> System.out.println("Coordinates should be from 1 to 3!"));
            } else {
                coordinates = Arrays.stream(values.split("\\s")).mapToInt(Integer::parseInt).toArray();

                if (isOccupied(coordinates)) {
                    notifyOnlyToHuman(() -> System.out.println("This cell is occupied! Choose another one!"));
                } else {
                    return coordinates;
                }
            }
        }
    }

    private String getCoordinatesForCurrentPlayer() {
        return switch (currentPlayer.getLevel()) {
            case USER -> {
                System.out.print("Enter the coordinates: ");
                yield scanner.nextLine().trim();
            }
            case EASY -> getRandomCoordinates();

            case MEDIUM -> {
                String move = getWinOrBlockMove();

                if (!move.isEmpty()) {
                    yield move;
                }

                yield getRandomCoordinates();
            }

            case HARD -> {
                String move = getWinOrBlockMove();

                if (!move.isEmpty()) {
                    yield move;
                }

                yield getBestMove(currentPlayer);
            }
        };
    }

    private String getWinOrBlockMove() {
        if (canWinInOneMove(currentPlayer)) {
            return getWinningCoordinate(currentPlayer);
        } else if (canWinInOneMove(opponent)) {
            return getWinningCoordinate(opponent);
        }

        return "";
    }

    private String getWinningCoordinate(Player player) {
        String winningMove = getRandomCoordinates();

        char playerSymbol = player.getSymbol();

        for (Combinations comb : Combinations.values()) {
            int symbolCount = 0;
            int spaceCount = 0;

            List<int[]> combCoordinates = comb.getCoordinates();
            for (int[] rowCoord : combCoordinates) {
                int row = rowCoord[0];
                int col = rowCoord[1];
                if (gameBoard[row][col] == playerSymbol) {
                    symbolCount++;
                }

                if (gameBoard[row][col] == toggleSymbol(playerSymbol)) {
                    symbolCount--;
                }

                if (gameBoard[row][col] == EMPTY_CELL) {
                    spaceCount++;
                    winningMove = getCoordinateFromIndexes(row, col);
                }
            }

            // Two equal symbols in a row and an empty space - Win the match
            if (symbolCount == 2 && spaceCount == 1) {
                return winningMove;
            }
        }

        return winningMove;
    }

    private String getRandomCoordinates() {
        var random = new Random();

        return getCoordinateFromIndexes(random.nextInt(3), random.nextInt(3));
    }

    private boolean isOccupied(int[] coordinates) {
        return this.gameBoard[coordinates[0] - 1][coordinates[1] - 1] != EMPTY_CELL;
    }

    public char[][] initGameBoard() {
        char[][] game = new char[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                game[row][col] = getSymbolOrSpace(EMPTY_CELL);
            }
        }

        return game;
    }

    private static char getSymbolOrSpace(char symbol) {
        return symbol != '_' ? symbol : ' ';
    }

    private void printGameBoard() {
        String gameGrid = """
                ---------
                | %s %s %s |
                | %s %s %s |
                | %s %s %s |
                ---------
                """;
        String printUpdatedGrid = String.format(
                gameGrid,
                gameBoard[0][0], gameBoard[0][1], gameBoard[0][2],
                gameBoard[1][0], gameBoard[1][1], gameBoard[1][2],
                gameBoard[2][0], gameBoard[2][1], gameBoard[2][2]);

        System.out.println(printUpdatedGrid);
    }

    private boolean isFinished() {
        if (isEmpty()) {
            return false;
        }

        return getSpaceLeft() < 3 || oneWins();
    }

    private boolean isEmpty() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (gameBoard[row][col] != EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getSpaceLeft() {
        int spacesLeft = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (gameBoard[row][col] == EMPTY_CELL) {
                    spacesLeft++;
                }
            }
        }
        return spacesLeft;
    }

    private boolean oneWins() {
        return isTheWinner(currentPlayer.getSymbol()) || isTheWinner(opponent.getSymbol());
    }

    private boolean isTheWinner(char symbol) {
        for (char[] combination : getWinningCombinations()) {
            if (isAWin(symbol, combination)) {
                return true;
            }
        }
        return false;
    }

    private boolean canWinInOneMove(Player player) {
        for (char[] combination : getWinningCombinations()) {
            if (canWinWithNextMove(combination, player)) {
                return true;
            }
        }

        return false;
    }

    private List<char[]> getWinningCombinations() {
        // winning combinations:
        // first row, second row third row
        // first col, second col, third col
        // first diagonal, second diagonal
        char[] firstRow = gameBoard[0];
        char[] secondRow = gameBoard[1];
        char[] thirdRow = gameBoard[2];

        char[] firstColumn = getColumn(0);
        char[] secondColumn = getColumn(1);
        char[] thirdColumn = getColumn(2);

        char[] firstDiagonal = getDiagonal("1st");
        char[] secondDiagonal = getDiagonal("2nd");

        return Arrays.asList(
                firstRow, secondRow, thirdRow,
                firstColumn, secondColumn, thirdColumn,
                firstDiagonal, secondDiagonal);
    }

    private char[] getColumn(int columnIndex) {
        char[] theColumn = new char[3];

        for (int r = 0; r < 3; r++) {
            theColumn[r] = gameBoard[r][columnIndex];
        }

        return theColumn;
    }

    private char[] getDiagonal(String i) {
        char[] theDiagonal = new char[3];

        if (i.equals("1st")) {
            theDiagonal[0] = gameBoard[0][0];
            theDiagonal[1] = gameBoard[1][1];
            theDiagonal[2] = gameBoard[2][2];
        }

        if (i.equals("2nd")) {
            theDiagonal[0] = gameBoard[0][2];
            theDiagonal[1] = gameBoard[1][1];
            theDiagonal[2] = gameBoard[2][0];
        }

        return theDiagonal;
    }

    private boolean isAWin(char symbol, char[] combination) {
        int symbolCount = 0;

        for (char c : combination) {
            if (c == symbol) {
                symbolCount++;
            }
        }

        return symbolCount == 3;
    }

    private boolean canWinWithNextMove(char[] combination, Player player) {
        char symbol = player.getSymbol();

        int symbolCount = 0;
        int spaceCount = 0;

        for (char c : combination) {
            if (c == symbol) {
                symbolCount++;
            }

            if (c == EMPTY_CELL) {
                spaceCount++;
            }

            if (c == toggleSymbol(symbol)) {
                symbolCount--;
            }
        }

        // Two equal symbols and only one space
        return symbolCount == 2 && spaceCount == 1;
    }

    public GameResult checkGame() {
        if (isImpossible()) {
            return GameResult.IMPOSSIBLE;
        }

        if (!isFinished()) {
            return GameResult.GAME_CONTINUES;
        }

        if (isTheWinner('X')) {
            return GameResult.X_WINS;
        }

        if (isTheWinner('O')) {
            return GameResult.O_WINS;
        }

        if (isDraw()) {
            return GameResult.DRAW;
        }

        return GameResult.GAME_CONTINUES;
    }

    public boolean isDraw() {
        return isFinished() &&
                !oneWins() &&
                getSpaceLeft() == 0;
    }

    private boolean isImpossible() {
        if (tooMuchOfTheSameSymbol()) {
            return true;
        }

        return tooMuchWinners();
    }

    private boolean tooMuchOfTheSameSymbol() {
        int xCount = 0;
        int oCount = 0;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (gameBoard[row][col] == 'X') {
                    xCount++;
                }
                if (gameBoard[row][col] == 'O') {
                    oCount++;
                }
            }
        }

        return Math.abs(xCount - oCount) > 1;
    }

    private boolean tooMuchWinners() {
        return isTheWinner('X') && isTheWinner('O');
    }

    private void notifyOnlyToHuman(Runnable msg) {
        if (currentPlayer.getLevel() == PlayerLevel.USER) msg.run();
    }

    private char toggleSymbol(char actual) {
        if (actual == 'X') return 'O';
        if (actual == 'O') return 'X';

        return actual;
    }

    private void toggleTurn() {
        if (currentPlayer == null || currentPlayer == player2) {
            currentPlayer = player1;
            opponent = player2;
        } else if (currentPlayer == player1) {
            currentPlayer = player2;
            opponent = player1;
        }

        currentPlayer.maximize();
        opponent.minimize();
    }

    private String getBestMove(Player player) {
        String bestMove = getRandomCoordinates();

        int bestScore = Integer.MIN_VALUE;

        for (int r = 0; r < gameBoard.length; r++) {
            for (int c = 0; c < gameBoard[0].length; c++) {
                // Search for the first empty spot
                if (gameBoard[r][c] == EMPTY_CELL) {
                    gameBoard[r][c] = player.getSymbol(); // provo a fare la mossa
                    int score = minimax(getMinimizer()); // verifico il punteggio per questa mossa in base alle possibilità dell'avversario
                    gameBoard[r][c] = EMPTY_CELL; // ripulisco la mossa fatta in precedenza
                    if (score > bestScore){
                        bestScore = score;
                        bestMove = getCoordinateFromIndexes(r,c);
                    }
                }
            }
        }
        return bestMove;
    }

    private static String getCoordinateFromIndexes(int row, int col) {
        ++row;
        ++col;
        return row + " " + col;
    }

    private int minimax(Player player) {
        var gameResult = checkGame();

        if (gameResult != GameResult.GAME_CONTINUES) {
            return getScoreForFinishedGame(gameResult);
        }

        int score;
        int bestScore;
        int winScore;
        Player theOther;

        if (player.isMaximising()){
            bestScore = Integer.MIN_VALUE;
            theOther = getMinimizer();
            winScore = MiniMax.MAXIMIZING.getScore();
        } else {
            bestScore = Integer.MAX_VALUE;
            theOther = getMaximizer();
            winScore = MiniMax.MINIMIZING.getScore();
        }

        for (int r = 0; r < gameBoard.length; r++) {
            for (int c = 0; c < gameBoard[0].length; c++) {
                if (gameBoard[r][c] == EMPTY_CELL) {
                    gameBoard[r][c] = player.getSymbol(); // provo a fare la mossa
                    score = minimax(theOther); // verifico il punteggio per questa mossa in base alle possibilità dell'avversario
                    gameBoard[r][c] = EMPTY_CELL; // ripulisco la mossa fatta in precedenza
                    bestScore = getBestScore(score, bestScore, player);
                    if (bestScore == winScore) { // alla prima combinazione vincente, restituisco le coordinate
                        return bestScore;
                    }
                }
            }
        }

        return bestScore;
    }

    private static int getBestScore(int score, int bestScore, Player player) {
        if (player.isMaximising()){
            return Math.max(score, bestScore);
        } else {
            return Math.min(score, bestScore);
        }
    }

    private int getScoreForFinishedGame(GameResult gameResult) {
        int score = Integer.MIN_VALUE;

        switch (gameResult) {
            case DRAW -> score = 0;

            case X_WINS -> {
                if (currentPlayer.getSymbol() == 'X' && currentPlayer.isMaximising()) {
                    score = currentPlayer.getMinimax().getScore();
                } else {
                    score = opponent.getMinimax().getScore();
                }
            }

            case O_WINS -> {
                if (currentPlayer.getSymbol() == 'O' && currentPlayer.isMaximising()) {
                    score = currentPlayer.getMinimax().getScore();
                } else {
                    score = opponent.getMinimax().getScore();
                }
            }
        }

        return score;
    }

    private Player getMinimizer() {
        if (currentPlayer.isMaximising()){
            return opponent;
        } else {
            return currentPlayer;
        }
    }

    private Player getMaximizer(){
        if (currentPlayer.isMaximising()){
            return currentPlayer;
        } else {
            return opponent;
        }
    }
}