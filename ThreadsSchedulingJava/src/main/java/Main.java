import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
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

        Task a = new Task('a', 'x', "");
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
        System.out.println(set.FNF("afdchgbecf"));
    }
}
