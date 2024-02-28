import java.util.Scanner;

class ArrayOperations {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);

        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        
        int[][] array = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = scanner.nextInt();
            }
        }

        printCorners(array);

    }
    static void printCorners(int[][] twoDimArray) {
        int rows = twoDimArray.length;
        int cols = twoDimArray[0].length;

        System.out.print(twoDimArray[0][0] + " ");
        System.out.print(twoDimArray[0][cols - 1]);
        System.out.println();

        if (rows == 1) {
            System.out.print(twoDimArray[0][0] + " ");
            System.out.print(twoDimArray[0][cols - 1]);
            System.out.println();
            return;
        }

        System.out.print(twoDimArray[rows - 1][0] + " ");
        System.out.print(twoDimArray[rows - 1][cols - 1]);
        System.out.println();
    }

}
