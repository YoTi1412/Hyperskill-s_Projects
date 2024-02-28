import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();

        // Create an array of size 'size'
        int[] a = new int[size];

        for (int i = 0; i < size; i++) {
            a[i] = scanner.nextInt();
        }

        // Perform cyclic right shift
        int lastElement = a[size - 1];
        for (int i = size - 1; i > 0; i--) {
            a[i] = a[i - 1];
        }
        a[0] = lastElement;

        for (int i = 0; i < size; i++) {
            System.out.print(a[i] + " ");
        }
    }
}