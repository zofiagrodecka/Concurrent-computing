import java.util.ArrayList;
import java.util.Arrays;

public class GaussianElimination {
    private float[][] matrix;

    public GaussianElimination(float[][] matrix){
        this.matrix = matrix;
    }

    public float calculateMultiplier(int i, int k){
        return matrix[k][i]/matrix[i][i];
    }

    public float multiply(int i, int j, int k, float multiplier){
        return matrix[i][j] * multiplier;
    }

    public void subtract(int i, int j, int k, float subtrahend){
        matrix[k][j] -= subtrahend;
    }

    public void run() throws InterruptedException {
        final int n = matrix.length;
        float[] multipliers = new float[n];
        float[][] multiplications = new float[n+1][n];
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
            System.out.println("Final A" + Arrays.toString(multipliers));
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
            System.out.println("Final B " + Arrays.deepToString(multiplications));
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
            System.out.println("Final C " + Arrays.deepToString(matrix));
            cThreads.clear();
        }

        System.out.println("Result:\n" + Arrays.deepToString(matrix));

        float gcd;
        for(int i=0; i<matrix.length; i++){
            gcd = gcd(matrix[i]);
            if(gcd != 1){
                for(int j=0; j<matrix[i].length; j++){
                    matrix[i][j] = matrix[i][j] / gcd;
                }
            }
        }

        System.out.println("Result after gcd:\n" + Arrays.deepToString(matrix));
    }

    private float gcd(float a, float b){
        if(a == 0){
            return b;
        }
        return gcd(b % a, a);
    }

    private float gcd(float array[]){
        float result = 0;
        for (float element: array){
            result = gcd(result, element);

            if(result == 1)
            {
                return 1;
            }
        }

        return result;
    }

    public float[][] getMatrix(){
        return matrix;
    }

    public String toString(){
        return Arrays.deepToString(matrix);
    }
}
