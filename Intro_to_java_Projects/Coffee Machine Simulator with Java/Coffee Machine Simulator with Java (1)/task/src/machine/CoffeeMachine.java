package machine;

import java.util.Scanner;

public class CoffeeMachine {
    protected static int water = 400;
    protected static int milk = 540;
    protected static int coffeeBeans = 120;
    protected static int cups = 9;
    protected static int money = 550;
    protected static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        RepresentationOfCoffeeMachine.startCoffeeMachine();
    }
}
