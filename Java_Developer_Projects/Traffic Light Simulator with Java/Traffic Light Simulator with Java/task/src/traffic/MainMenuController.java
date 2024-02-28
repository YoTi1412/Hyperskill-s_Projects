package traffic;

import java.util.Scanner;

public class MainMenuController implements Runnable{
    public static final String WELCOME_TEXT = "Welcome to the traffic management system!";

    private static final String MENU_TEXT = """
            Menu:
            1. Add road
            2. Delete road
            3. Open system
            0. Quit""";
    private final ConsolePrinter printer;

    private Scanner scanner;

    private int roads;
    private int interval;

    private TrafficLights trafficLights;

    private QueueThread systemTimer;

    public MainMenuController(Scanner scanner, ConsolePrinter consolePrinter) {
        this.scanner = scanner;
        this.printer = consolePrinter;
    }

    @Override
    public void run() {
        System.out.println(WELCOME_TEXT);

        setupTrafficLights();

        systemTimer = new QueueThread(trafficLights, printer);
        mainMenuLoop();
        systemTimer.purge();
    }

    private void mainMenuLoop() {
        int menuChoice;

        while (true){
            showMenu();

            menuChoice = scanValidatedInteger("[0-3]", () -> {
                System.out.println("Incorrect option");
                waitEnterThenCleanConsole();
                showMenu();
            });

            if (menuChoice == 0){
                System.out.println("Bye!");
                break;
            }

            switch (menuChoice){
                case 1:
                    addRoad();
                    waitEnterThenCleanConsole();
                    break;

                case 2:
                    deleteRoad();
                    waitEnterThenCleanConsole();
                    break;

                case 3:
                    openSystem();
                    break;
            }
        }
    }

    private void deleteRoad() {
        System.out.println(trafficLights.deleteRoad());
    }

    private void addRoad() {
        System.out.print("Input road name: ");
        var road = scanner.nextLine();
        System.out.println(trafficLights.addRoad(road));
    }

    private void openSystem() {
        systemTimer.setInSystemState(true);
        scanner.nextLine(); //wait on Return press
        systemTimer.setInSystemState(false);
    }

    private void setupTrafficLights() {
        System.out.print("Input the number of roads: ");
        roads = scanValidatedInteger("[1-9][0-9]{0,8}", () -> System.out.print("Error! Incorrect input. Try again: "));

        System.out.print("Input the interval: ");
        interval = scanValidatedInteger("[1-9][0-9]{0,8}", () -> System.out.print("Error! Incorrect input. Try again: "));

        trafficLights = new TrafficLights(roads, interval);
    }

    private void showMenu() {
        System.out.println(MENU_TEXT);
    }

    private int scanValidatedInteger(String regex, Runnable errorMsg) {
        String input = scanner.nextLine();

        while (!input.matches(regex)){
            errorMsg.run();
            input = scanner.nextLine();
        }

        return Integer.parseInt(input);
    }

    private void waitEnterThenCleanConsole() {
        scanner.nextLine();
        printer.clearConsole();
    }
}