package lastpencil;

import java.util.Scanner;

public class PencilGame {

    private final Scanner scanner = new Scanner(System.in);
    private int pencils;
    private boolean isPlayerTurn;

    public void run() {
        initPencils();
        decidePlayerTurn();

        Player player = new Player();
        Bot bot = new Bot();

        while (true) {
            render();
            int takenPencils;

            if (isPlayerTurn) {
                System.out.println("John's turn!");
                takenPencils = player.takePencils(pencils);
            } else {
                System.out.println("Jack's turn:");
                takenPencils = bot.takePencils(pencils);
                System.out.println(takenPencils);
            }

            pencils -= takenPencils;
            if (pencils == 0) {
                if (isPlayerTurn) {
                    System.out.println("Jack won!");
                } else {
                    System.out.println("John won!");
                }
                break;
            }
            isPlayerTurn = !isPlayerTurn;
        }
    }

    public void initPencils() {
        System.out.println("How many pencils would you like to use:");

        while (true) {
            try {
                String input = scanner.nextLine();
                pencils = Integer.parseInt(input);

                if (pencils == 0) {
                    System.out.println("The number of pencils should be positive");
                } else if (pencils < 0) {
                    System.out.println("The number of pencils should be numeric");
                } else {
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("The number of pencils should be numeric");
            }
        }
    }

    public void decidePlayerTurn() {
        System.out.println("Who will be the first (John, Jack):");

        while (true) {
            String firstPlayer = scanner.nextLine();

            if (!firstPlayer.equals("John") && !firstPlayer.equals("Jack")) {
                System.out.println("Choose between John and Jack");
                continue;
            }

            isPlayerTurn = firstPlayer.equals("John");
            break;
        }
    }


    public void render() {
        System.out.println("|".repeat(pencils));
    }
}