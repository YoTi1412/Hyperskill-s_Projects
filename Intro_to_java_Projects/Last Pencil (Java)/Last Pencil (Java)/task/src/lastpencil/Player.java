package lastpencil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {

    public int takePencils(int remain) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                int pencils = scanner.nextInt();

                if (pencils < 1 || pencils > 3) {
                    System.out.println("Possible values: '1', '2' or '3'");
                    continue;
                } else if (pencils > remain) {
                    System.out.println("Too many pencils were taken");
                    continue;
                }

                return pencils;
            } catch (InputMismatchException e) {
                System.out.println("Possible values: '1', '2' or '3'");
                scanner.nextLine();
            }
        }
    }
}