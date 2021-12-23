import java.io.*;
import java.util.Scanner;

public class MatrixFileReader {
    private final String filename;

    public MatrixFileReader(String filename){
        this.filename = filename;
    }

    public double[][] readFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        final int n = scanner.nextInt();
        double[][] matrix = new double[n][n+1];
        int i = 0;
        int j = 0;
        while(i < n && j < n){
            matrix[i][j] = scanner.nextFloat();
            j++;
            if(j == n){
                j = 0;
                i++;
            }
        }
        j = n;
        for(int w=0; w<n; w++){
            matrix[w][j] = scanner.nextFloat();
        }
        scanner.close();
        return matrix;
    }

    public void writeFile(String filename, double[][] matrix) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(String.valueOf(matrix.length) + '\n');

        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix.length; j++){
                writer.write(String.valueOf(matrix[i][j]) + ' ');
            }
            writer.write("\n");
        }

        for(int i=0; i<matrix.length; i++){
            writer.write(String.valueOf(matrix[i][matrix.length]) + ' ');
        }

        writer.close();
    }
}
