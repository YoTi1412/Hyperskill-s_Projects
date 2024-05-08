package minesweeper;

public class Cell {
    private boolean hasMine;
    private boolean hasMarker;
    private boolean isExplored;
    private int adjacentMines = 0;

    Cell() {
    }

    public void setHasMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public void setHasMarker(boolean hasMarker) {
        this.hasMarker = hasMarker;
    }

    public void setExplored(boolean explored) {
        isExplored = explored;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public boolean isHasMine() {
        return hasMine;
    }

    public boolean isHasMarker() {
        return hasMarker;
    }

    public boolean isExplored() {
        return isExplored;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }
}