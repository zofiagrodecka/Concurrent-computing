import java.util.Arrays;

public class CThread implements Runnable{
    private final GaussianElimination gaussianElimination;
    private final int i;
    private final int j;
    private final int k;
    private final float[][] multiplications;

    public CThread(GaussianElimination elimination, int i, int j, int k, float[][] multiplications){
        this.gaussianElimination = elimination;
        this.i = i;
        this.j = j;
        this.k = k;
        this.multiplications = multiplications;
    }

    @Override
    public void run() {
        gaussianElimination.subtract(i, j, k, multiplications[j][k]);
        System.out.println(Arrays.deepToString(multiplications));
    }
}
