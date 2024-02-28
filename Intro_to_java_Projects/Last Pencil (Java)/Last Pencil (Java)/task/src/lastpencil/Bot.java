package lastpencil;

import java.util.Random;

public class Bot {

    private final Random random = new Random();

    public int takePencils(int pencils) {
        if (pencils == 1) {
            return 1;
        }

        int positionChecker = pencils % 4;

        return switch (positionChecker) {
            case 0 -> 3;
            case 1 -> genRandomNumber();
            case 2 -> 1;
            case 3 -> 2;
            default -> -1;
        };
    }

    private int genRandomNumber() {
        return random.nextInt(3) + 1;
    }

}