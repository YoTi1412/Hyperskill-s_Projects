import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt(); // number of rows
        int m = scanner.nextInt(); // number of seats per row

        int[][] cinema = new int[n][m];

        for (int rows = 0; rows < n; rows++) {
            for (int seats = 0; seats < m; seats++) {
                cinema[rows][seats] = scanner.nextInt();
            }
        }

        int k = scanner.nextInt(); // Number of consecutive seats to find
        int availableRow = 0; // Initialize with 0, no available row found initially

        for (int i = 0; i < n; i++) {
            int consecutiveCount = 0; // Count of consecutive available seats

            for (int j = 0; j < m; j++) {
                if (cinema[i][j] == 0) {
                    consecutiveCount++;
                    if (consecutiveCount == k) {
                        availableRow = i + 1; // Update availableRow with current row number
                        break; // Break the loop since k consecutive available seats are found
                    }
                } else {
                    consecutiveCount = 0; // Reset count if a sold seat is encountered
                }
            }

            if (availableRow != 0) {
                break; // Break the outer loop if availableRow is updated
            }
        }

        System.out.println(availableRow);
    }
}