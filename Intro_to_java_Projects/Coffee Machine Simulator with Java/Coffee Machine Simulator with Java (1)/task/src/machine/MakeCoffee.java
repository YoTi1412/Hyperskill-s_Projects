package machine;

public class MakeCoffee extends CoffeeMachine {
    public static void makeCoffee(int waterNeeded, int milkNeeded, int coffeeNeeded, int cost) {
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
}
