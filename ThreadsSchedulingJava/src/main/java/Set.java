import java.util.*;

public class Set {
    Alphabet alphabet;
    ArrayList<Task> dependentTasks = new ArrayList<Task>(); // Dwa pierwsze elementy są ze sobą zależne, potem kolejne dwa są ze sobą zależne, potem kolejna para, itd...
    ArrayList<Task> independentTasks = new ArrayList<Task>(); // Analogicznie jak dependentTasks
    ArrayList<ArrayList<Task>> FNF;


    Set(Alphabet alphabet){
        this.alphabet = alphabet;
    }

    private void addDependentPair(Task task1, Task task2){
        dependentTasks.add(task1);
        dependentTasks.add(task2);
    }

    private void addIndependentPair(Task task1, Task task2){
        independentTasks.add(task1);
        independentTasks.add(task2);
    }

    public void calculateDependency(){
        Task t1, t2;
        for(int i=0; i<alphabet.tasks.size(); i++){
            for(int j=0; j<alphabet.tasks.size(); j++){
                t1 = alphabet.tasks.get(i);
                t2 = alphabet.tasks.get(j);
                if(!t1.equals(t2)){
                    if(t1.isDependent(t2)){
                        addDependentPair(t1, t2);
                    }
                    else{
                        addIndependentPair(t1, t2);
                    }
                }
            }
        }
    }

    public ArrayList<Task> getDependentTasks(char label) {
        ArrayList<Task> result = new ArrayList<Task>();
        for(int i=0; i<dependentTasks.size(); i+= 2){
            if(dependentTasks.get(i).getLabel() == label){
                if(result.size() == 0){
                    result.add(dependentTasks.get(i));
                }
                result.add(dependentTasks.get(i+1));
            }
        }
        return result;
    }

    public ArrayList<ArrayList<Task>> FNF(String word) {
        ArrayList<Stack<Task>> stacks = new ArrayList<>(alphabet.size());
        for (int i = 0; i < alphabet.size(); i++) {
            stacks.add(new Stack<Task>());
        }

        char c;
        Task currentTask;
        int index;
        Task empytTask = new Task(' ', ' ', " ");
        ArrayList<Task> dependent;

        for (int i = word.length()-1; i >= 0; i--) {
            c = word.charAt(i);
            currentTask = alphabet.taskFromChar(c);
            index = alphabet.tasks.indexOf(currentTask);
            stacks.get(index).push(currentTask);
            dependent = getDependentTasks(c);
            for(Task task : dependent){
                if(! task.equals(currentTask)){
                    index = alphabet.tasks.indexOf(task);
                    stacks.get(index).push(empytTask);
                }
            }
        }

        ArrayList<ArrayList<Task>> fnf = new ArrayList<>();
        ArrayList<Integer> stacksToBePoped = new ArrayList<Integer>();
        while(! allStacksEmpty(stacks)){
            fnf.add(new ArrayList<Task>());
            for(int i=0; i<alphabet.size(); i++){
                if(! stacks.get(i).empty()){
                    currentTask = stacks.get(i).peek();
                    if(! empytTask.equals(currentTask)){
                        fnf.get(fnf.size()-1).add(currentTask);
                        stacks.get(i).pop();
                        dependent = getDependentTasks(currentTask.getLabel());
                        for(Task task : dependent){
                            index = alphabet.tasks.indexOf(task);
                            if(!task.equals(currentTask)){
                                stacksToBePoped.add(index);
                            }
                        }
                    }
                }
            }
            for(Integer number : stacksToBePoped){
                stacks.get(number).pop();
            }
            stacksToBePoped.clear();
        }

        return fnf;
    }

    private boolean allStacksEmpty(ArrayList<Stack<Task>> stacks){
        for (Stack<Task> stack : stacks) {
            if (!stack.empty()) {
                return false;
            }
        }
        return true;
    }

    public String toString(){
        String result = "Dependency set: ";
        for(int i=0; i<dependentTasks.size(); i+= 2){
            result += "(" + dependentTasks.get(i).toString() + ", " + dependentTasks.get(i+1).toString() + "), ";
        }
        result = result.substring(0, result.length()-2);
        result += "\nIndependency set: ";
        for(int i=0; i<independentTasks.size(); i+= 2){
            result += "(" + independentTasks.get(i).toString() + ", " + independentTasks.get(i+1).toString() + "), ";
        }
        result = result.substring(0, result.length()-2);
        return result;
    }
}
