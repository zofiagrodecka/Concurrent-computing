import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Task a = new Task("a", 'x', "xy");
        Task b = new Task("b", 'y', "yz");
        Task c = new Task("c", 'x', "xz");
        Task d = new Task("d", 'z', "yz");
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
    }
}
