import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Prices {

    private final static int ITER = (int) Math.pow(10, 6), WIDTH = 4, HEIGHT = 5;
    private final static int T1 = 6, T2 = 7, T3 = 8, T4 = 9;
    private final static double DELTA = Math.pow(10, -6);

    public static void main(String[] args) {
        double[][][] plate = new double[ITER][HEIGHT][WIDTH];
        for (int i = 0; i < ITER; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                for (int k = 0; k < WIDTH; k++) {
                    if (j == 0 && k != 0 && k != WIDTH - 1) plate[i][j][k] = T1;
                    else if (j == HEIGHT - 1 && k != 0 && k != WIDTH - 1) plate[i][j][k] = T2;
                    else if (k == 0 && j != 0 && j != HEIGHT - 1) plate[i][j][k] = T3;
                    else if (k == WIDTH - 1 && j != 0 && j != HEIGHT - 1) plate[i][j][k] = T4;
                    if (i > 1 && j > 0 && j < HEIGHT - 1 && k > 0 && k < WIDTH - 1) {
                        plate[i][j][k] = (plate[i - 1][j - 1][k] + plate[i - 1][j + 1][k] + plate[i - 1][j][k - 1] + plate[i - 1][j][k + 1]) / 4;
                        if (plate[i][j][k] > 0 && plate[i - 1][j][k] > 0 && Math.abs(plate[i][j][k] - plate[i - 1][j][k]) < DELTA) {
                            for (double[] linear : plate[i - 1]) {
                                for (double value : linear) System.out.print(String.format("%.6f", value) + "\t");
                                System.out.println();
                            }
                            System.exit(0);
                        }
                    }
                }
            }
        }
    }
}
