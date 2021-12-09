import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

        if(args.length == 0){
            System.out.println("Please, run this program with a file name as an argument");
            System.exit(1);
        }

        String word = "";
        String alphabetLetters = "";
        ArrayList<Task> allTasks = new ArrayList<Task>();
        int colonIndex;
        String lettersAfterColon;

        String str;
        try(BufferedReader fileReader = new BufferedReader(new FileReader("src/main/testfiles/test2.txt"))) {
            while((str = fileReader.readLine()) != null){
                System.out.println(str);
                if(str.charAt(0) == 'A'){
                    alphabetLetters = str.substring(1).replaceAll("[^A-Za-z]+", "");
                }
                else if(str.charAt(0) == 'w'){
                    str = str.replaceAll("\\s+","");
                    word = str.substring(2);
                }
                else if(alphabetLetters.indexOf(str.charAt(0)) != -1){
                    str = str.replaceAll("\\s+","");
                    colonIndex = str.indexOf(':');
                    lettersAfterColon = str.replaceAll("[^A-Za-z]+", "");
                    allTasks.add(new Task(str.charAt(0), str.charAt(colonIndex-1), lettersAfterColon));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Alphabet alphabet = new Alphabet(allTasks);
        System.out.println("\nAlphabet: " + allTasks);
        System.out.println("Word: " + word);
        Set set = new Set(alphabet);
        set.calculateDependency();
        System.out.println("Dependency and independency relations:\n" + set);
        System.out.println("FNF of a trace w: " + set.FNF(word));
        Graph dikertGrapf = new Graph(word, set);
        System.out.println("Dikert's graph: " + dikertGrapf);
        System.out.println("FNF based on Dikert's graph: " + dikertGrapf.FNF());

        try {
            dikertGrapf.saveAsFile("res_test2.dot");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
