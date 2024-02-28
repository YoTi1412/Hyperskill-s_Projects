package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private static int water = 400;
    private static int milk = 540;
    private static int coffeeBeans = 120;
    private static int cups = 9;
    private static int money = 550;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        startCoffeeMachine();
    }

    private static void printStatus() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffeeBeans + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + money + " of money");
    }

    private static void makeCoffee(int waterNeeded, int milkNeeded, int coffeeNeeded, int cost) {
        if (water >= waterNeeded && milk >= milkNeeded && coffeeBeans >= coffeeNeeded && cups >= 1) {
            System.out.println("I have enough resources, making you a coffee!");
            water -= waterNeeded;
            milk -= milkNeeded;
            coffeeBeans -= coffeeNeeded;
            cups--;
            money += cost;
        } else {
            if (water < waterNeeded) {
                System.out.println("Sorry, not enough water!");
            } else if (milk < milkNeeded) {
                System.out.println("Sorry, not enough milk!");
            } else if (coffeeBeans < coffeeNeeded) {
                System.out.println("Sorry, not enough coffee beans!");
            } else {
                System.out.println("Sorry, not enough cups!");
            }
        }
    }

    private static void buy() {
        while (true) {
            System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
            String choice = scanner.next();
            if (choice.equals("back")) {
                break;
            }
            int option = Integer.parseInt(choice);
            switch (option) {
                case 1:
                    makeCoffee(250, 0, 16, 4);
                    break;
                case 2:
                    makeCoffee(350, 75, 20, 7);
                    break;
                case 3:
                    makeCoffee(200, 100, 12, 6);
                    break;
                default:
                    break;
            }
            break;
        }
    }

    private static void fill() {
        System.out.println("Write how many ml of water you want to add:");
        water += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        milk += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        coffeeBeans += scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        cups += scanner.nextInt();
    }

    private static void take() {
        System.out.println("I gave you $" + money);
        money = 0;
    }

    private static void startCoffeeMachine() {
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
