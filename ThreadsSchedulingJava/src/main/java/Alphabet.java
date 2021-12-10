import java.util.ArrayList;

public class Alphabet {
    public ArrayList<Task> tasks = new ArrayList<Task>();

    Alphabet(ArrayList<Task> alphabet){
        this.tasks = alphabet;
    }

    int size(){
        return tasks.size();
    }

    public Task taskFromChar(char c){
        for(Task task : tasks){
            if(task.getLabel() == c){
                return task;
            }
        }
        return null;
    }
}
