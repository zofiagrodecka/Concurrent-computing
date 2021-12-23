import java.util.Arrays;

public class BThread implements Runnable {
    private final GaussianElimination gaussianElimination;
    private final int i;
    private final int j;
    private final int k;
    private final double[] multipliers;
    private final double[][] multiplications;

    public BThread(GaussianElimination elimination, int i, int j, int k, double[] multipliers, double[][] multiplications){
        this.gaussianElimination = elimination;
        this.i = i;
        this.j = j;
        this.k = k;
        this.multipliers = multipliers;
        this.multiplications = multiplications;
    }

    @Override
    public void run() {
        multiplications[j][k] = gaussianElimination.multiply(i, j, k, multipliers[k]);
    }
}
