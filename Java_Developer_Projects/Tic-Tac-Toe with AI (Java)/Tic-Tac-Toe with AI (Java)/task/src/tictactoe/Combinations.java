package tictactoe;

import java.util.Arrays;
import java.util.List;

public enum Combinations {
    FIRST_ROW(Arrays.asList(new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 2})),
    SECOND_ROW(Arrays.asList(new int[]{1, 0}, new int[]{1, 1}, new int[]{1, 2})),
    THIRD_ROW(Arrays.asList(new int[]{2, 0}, new int[]{2, 1}, new int[]{2, 2})),
    FIRST_COLUMN(Arrays.asList(new int[]{0, 0}, new int[]{1, 0}, new int[]{2, 0})),
    SECOND_COLUMN(Arrays.asList(new int[]{0, 1}, new int[]{1, 1}, new int[]{2, 1})),
    THIRD_COLUMN(Arrays.asList(new int[]{0, 2}, new int[]{1, 2}, new int[]{2, 2})),
    FIRST_DIAGONAL(Arrays.asList(new int[]{0, 0}, new int[]{1, 1}, new int[]{2, 2})),
    SECOND_DIAGONAL(Arrays.asList(new int[]{0, 2}, new int[]{1, 1}, new int[]{2, 0}));

    private final List<int[]> coordinates;

    Combinations(List<int[]> coordinates) {
        this.coordinates = coordinates;
    }

    public List<int[]> getCoordinates(){
        return this.coordinates;
    }
}