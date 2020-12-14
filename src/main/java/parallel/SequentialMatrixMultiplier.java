package parallel;

import java.util.Arrays;

/**
 * Matrix A multiply Matrix B in a sequential way.
 */
public class SequentialMatrixMultiplier {
    private int[][] mA, mB;
    private int rowNumbOfA, colNumOfA, colNumOfB;
    private int rowNumOfB;

    public SequentialMatrixMultiplier(int[][] mA, int[][] mB) {
        if (colNumOfA != rowNumOfB)
            throw new IllegalArgumentException("matrix A's col number != matrix B's row number");

        this.mA = mA;
        this.mB = mB;

        rowNumbOfA = mA.length;
        colNumOfA = mA[0].length;

        rowNumOfB = mB.length;
        colNumOfB = mB[0].length;
    }

    public int[][] product() {
        int[][] result = new int[rowNumbOfA][colNumOfB];

        for (int p = 0; p < rowNumbOfA; p++) {
            for (int q = 0; q < colNumOfB; q++) {
                result[p][q] = sum(p, q);
            }
        }

        return result;
    }

    private int sum(int p, int q) {
        int sum = 0;

        for (int i = 0, j = 0; i < colNumOfA && j < rowNumOfB; i++, j++) {
            sum += mA[p][i] * mB[j][q];
        }

        return sum;
    }

    public static void main(String[] args) {
        int[][] a = {{0, 1}, {0, 0}};
        int[][] b = {{0, 0}, {1, 0}};

        SequentialMatrixMultiplier matrixMultiplier = new SequentialMatrixMultiplier(a, b);
        int[][] r = matrixMultiplier.product();
        printMatrix(r);

        int[][] c = {{1, 2, 3}};
        int[][] d = {{4}, {5}, {6}};
        SequentialMatrixMultiplier matrixMultiplier1 = new SequentialMatrixMultiplier(c, d);
        int[][] r1 = matrixMultiplier1.product();
        printMatrix(r1);
    }

    public static void printMatrix(int[][] matrix) {

        Arrays.asList(matrix).forEach(row -> System.out.println(Arrays.toString(row)));

    }
}
