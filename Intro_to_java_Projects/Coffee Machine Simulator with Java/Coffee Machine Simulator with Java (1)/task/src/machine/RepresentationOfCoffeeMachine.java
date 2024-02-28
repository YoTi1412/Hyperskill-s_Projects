package machine;

import static machine.Status.printStatus;

public class RepresentationOfCoffeeMachine extends Actions {
    public static void startCoffeeMachine() {
        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = scanner.next();
            switch (action) {
                case "buy":
                    buy();
                    break;
                case "fill":
                    fill();
                    break;
                case "take":
                    take();
                    break;
                case "remaining":
                    printStatus();
                    break;
                case "exit":
                    return;
                default:
                    break;
            }
        }
    }
}
