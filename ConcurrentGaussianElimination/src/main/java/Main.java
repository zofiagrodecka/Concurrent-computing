import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        if(args.length == 0){
            System.out.println("Please, run this program with a input file name and output file name as the arguments");
            System.exit(-1);
        }

        MatrixFileReader fileReader = new MatrixFileReader(args[0]);
        try {
            float[][] matrix = fileReader.readFile();
            System.out.println(Arrays.deepToString(matrix));
            GaussianElimination elimination = new GaussianElimination(matrix);
            elimination.run();
            fileReader.writeFile(args[1], elimination.getMatrix());

        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
