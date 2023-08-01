import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Prices {

    private final static int ITER = (int) Math.pow(10, 6), WIDTH = 4, HEIGHT = 5;
    private final static int T1 = 6, T2 = 7, T3 = 8, T4 = 9;
    private final static double DELTA = Math.pow(10, -6);

    public static void main(String[] args) {
        double[][][] arr = new double[ITER][HEIGHT][WIDTH];
        for (int i = 0; i < ITER; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                for (int k = 0; k < WIDTH; k++) {
                    if (j == 0 && k != 0 && k != WIDTH - 1) borderFilling(arr, i, j, k, T1);
                    else if (j == HEIGHT - 1 && k != 0 && k != WIDTH - 1) borderFilling(arr, i, j, k, T2);
                    else if (k == 0 && j != 0 && j != HEIGHT - 1) borderFilling(arr, i, j, k, T3);
                    else if (k == WIDTH - 1 && j != 0 && j != HEIGHT - 1) borderFilling(arr, i, j, k, T4);
                    if (fillingTrigger(i, j, k)) {
                        research(arr, i, j, k);
                    }
                }
            }
        }
    }

    private static void research(double[][][] arr, int i, int j, int k) {
        arr[i][j][k] = averageFour(arr, i, j, k);
        if (exitTrigger(arr, i, j, k)) {
            finalAccord(arr[i - 2]);
            finalAccord(arr[i - 1]);
            System.exit(0);
        }
    }

    private static void borderFilling(double[][][] arr, int i, int j, int k, int filler) {
        arr[i][j][k] = filler;
    }

    private static boolean fillingTrigger(int i, int j, int k) {
        return i > 1 && j > 0 && j < HEIGHT - 1 && k > 0 && k < WIDTH - 1;
    }

    private static boolean exitTrigger(double[][][] arr, int i, int j, int k) {
        return arr[i][j][k] > 0 && arr[i - 1][j][k] > 0 && Math.abs(arr[i][j][k] - arr[i - 1][j][k]) < DELTA;
    }

    private static double averageFour(double[][][] arr, int i, int j, int k) {
        return (arr[i - 1][j - 1][k] + arr[i - 1][j + 1][k] + arr[i - 1][j][k - 1] + arr[i - 1][j][k + 1]) / 4;
    }

    private static void finalAccord(double[][] arr) {
        for (double[] linear : arr) {
            for (double value : linear) System.out.print(String.format("%.6f", value) + "\t");
            System.out.println();
        }
        System.out.println();
    }
}
