package machine;
public class Actions extends MakeCoffee {
    public static void buy() {
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

    public static void fill() {
        System.out.println("Write how many ml of water you want to add:");
        water += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        milk += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        coffeeBeans += scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        cups += scanner.nextInt();
    }

    public static void take() {
        System.out.println("I gave you $" + money);
        money = 0;
    }
}
