import java.util.Arrays;

public class AThread implements Runnable{
    private final GaussianElimination gaussianElimination;
    private final int i;
    private final int k;
    private final double[] multipliers;

    public AThread(GaussianElimination elimination, int a, int b, double[] multipliers){
        this.gaussianElimination = elimination;
        this.i = a;
        this.k = b;
        this.multipliers = multipliers;
    }

    @Override
    public void run() {
        multipliers[k] = gaussianElimination.calculateMultiplier(i, k);
    }
}
