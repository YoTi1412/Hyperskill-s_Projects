package calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int total = (202 + 118 + 2250 + 1680 + 1075 + 80);
        System.out.println("Earned amount: ");
        System.out.println("Bubblegum: $202");
        System.out.println("Toffee: $118");
        System.out.println("Ice cream: $2250");
        System.out.println("Milk chocolate: $1680");
        System.out.println("Doughnut: $1075");
        System.out.println("Pancake: $80\n");

        System.out.println("Income: $" + total);


        System.out.println("Staff expenses: ");
        int staff = scanner.nextInt();


        System.out.println("Other expenses: ");
        int other = scanner.nextInt();

        System.out.println("Net income: $" + (total - staff - other));
    }
}