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
        }

        Alphabet alphabet = new Alphabet(allTasks);
        System.out.println("\nAlfabet: " + allTasks);
        System.out.println("Słowo: " + word);
        Set set = new Set(alphabet);
        set.calculateDependency();
        System.out.println("Relacje zależności i niezależności:\n" + set);
        System.out.println("Postać FNF śladu w: " + set.FNF(word));
        Graph dikertGrapf = new Graph(word, set);
        System.out.println("Graf Dikerta: " + dikertGrapf);
        System.out.println("Postać FNF na podstawie grafu Dikerta: " + dikertGrapf.FNF());
        /*Task a = new Task('a', 'x', "xy");
        Task b = new Task('b', 'y', "yz");
        Task c = new Task('c', 'x', "xz");
        Task d = new Task('d', 'z', "yz");
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.add(a);
        allTasks.add(b);
        allTasks.add(c);
        allTasks.add(d);
        Alphabet alphabet = new Alphabet(allTasks);
        Set set = new Set(alphabet);
        set.calculateDependency();
        System.out.println(set);
        Graph dikertGrapf = new Graph("baddabcd", set);
        System.out.println(dikertGrapf);
        System.out.println(dikertGrapf.FNF());
        System.out.println(set.FNF("baddabcd"));*/

        /*Task a = new Task('a', 'x', "");
        Task b = new Task('b', 'x', "xy");
        Task c = new Task('c', 'x', "xz");
        Task d = new Task('d', 'y', "y");
        Task e = new Task('e', 'y', "vx");
        Task f = new Task('f', 'z', "");
        Task g = new Task('g', 'z', "zv");
        Task h = new Task('h', 'v', "vz");
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.add(a);
        allTasks.add(b);
        allTasks.add(c);
        allTasks.add(d);
        allTasks.add(e);
        allTasks.add(f);
        allTasks.add(g);
        allTasks.add(h);
        Alphabet alphabet = new Alphabet(allTasks);
        Set set = new Set(alphabet);
        set.calculateDependency();
        System.out.println(set);
        Graph dikertGrapf = new Graph("afdchgbecf", set);
        System.out.println(dikertGrapf);
        System.out.println(dikertGrapf.FNF());
        System.out.println(set.FNF("afdchgbecf"));*/
    }
}
