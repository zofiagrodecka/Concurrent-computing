import java.util.ArrayList;
import java.util.Arrays;

public class GaussianElimination {
    private double[][] matrix;

    public GaussianElimination(double[][] matrix){
        this.matrix = matrix;
    }

    public double calculateMultiplier(int i, int k){
        return matrix[k][i]/matrix[i][i];
    }

    public double multiply(int i, int j, int k, double multiplier){
        return matrix[i][j] * multiplier;
    }

    public void subtract(int i, int j, int k, double subtrahend){
        matrix[k][j] -= subtrahend;
    }

    public void run() throws InterruptedException {
        final int n = matrix.length;
        double[] multipliers = new double[n];
        double[][] multiplications = new double[n+1][n];
        ArrayList<Thread> aThreads = new ArrayList<>();
        ArrayList<Thread> bThreads = new ArrayList<>();
        ArrayList<Thread> cThreads = new ArrayList<>();

        for(int i=0; i<n-1; i++){
            for(int k=i+1; k<n; k++){
                aThreads.add(new Thread(new AThread(this, i, k, multipliers)));
            }
            for (Thread thread : aThreads) {
                thread.start();
            }
            for (Thread thread : aThreads) {
                thread.join();
            }
            aThreads.clear();

            for(int k=i+1; k<n; k++){
                for(int j=i; j<n+1; j++){
                    bThreads.add(new Thread(new BThread(this, i, j, k, multipliers, multiplications)));
                }
            }
            for (Thread thread : bThreads) {
                thread.start();
            }
            for (Thread thread : bThreads) {
                thread.join();
            }
            bThreads.clear();

            for(int k=i+1; k<n; k++){
                for(int j=i; j<n+1; j++){
                    cThreads.add(new Thread(new CThread(this, i, j, k, multiplications)));
                }
            }
            for (Thread thread : cThreads) {
                thread.start();
            }
            for (Thread thread : cThreads) {
                thread.join();
            }
            cThreads.clear();
        }

        System.out.println("Result:\n" + Arrays.deepToString(matrix));
    }

    public double[][] getMatrix(){
        return matrix;
    }

    public String toString(){
        return Arrays.deepToString(matrix);
    }
}
