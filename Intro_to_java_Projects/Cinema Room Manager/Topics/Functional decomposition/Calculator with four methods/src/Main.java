class SimpleCalculator {

    public static long subtractTwoNumbers(long n1, long n2) {
        return n1 - n2;
    }

    public static long sumTwoNumbers(long n1, long n2) {
        return n1 + n2;
    }

    public static long divideTwoNumbers(long n1, long n2) {
        if (n2 == 0) {
            System.out.println("Division by 0!");
            return 0; // Return 0 or handle differently based on your needs
        }
        return n1 / n2;
    }

    public static long multiplyTwoNumbers(long n1, long n2) {
        return n1 * n2;
    }

    public static void power(long n, long p) {
        long number = n;
        long power = p;
        long result = 1;
        while (power > 0) {
            if (power % 2 != 0) {
                result *= number;
            }
            power /= 2;
            number *= number;
        }
        System.out.println(result);
    }
}

public class Main {

    public static void main(String[] args) {

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        long num1 = scanner.nextLong();
        String operator = scanner.next();
        long num2 = scanner.nextLong();

        long result = 0;

        if (operator.equals("/") && num2 == 0) {
            System.out.println("Division by 0!");
        } else {
            switch (operator) {
                case "^":
                    SimpleCalculator.power(num1, num2);
                    break;
                case "+":
                    result = SimpleCalculator.sumTwoNumbers(num1, num2);
                    System.out.println(result);
                    break;
                case "-":
                    result = SimpleCalculator.subtractTwoNumbers(num1, num2);
                    System.out.println(result);
                    break;
                case "/":
                    result = SimpleCalculator.divideTwoNumbers(num1, num2);
                    if (result != 0) {
                        System.out.println(result);
                    }
                    break;
                case "*":
                    result = SimpleCalculator.multiplyTwoNumbers(num1, num2);
                    System.out.println(result);
                    break;
                default:
                    break;
            }
        }
    }
}
