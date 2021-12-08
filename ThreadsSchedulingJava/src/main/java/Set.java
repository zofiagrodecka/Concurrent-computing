import java.util.ArrayList;

public class Set {
    Alphabet alphabet;
    ArrayList<Task> dependentTasks = new ArrayList<Task>(); // Dwa pierwsze elementy są ze sobą zależne, potem kolejne dwa są ze sobą zależne, potem kolejna para, itd...
    ArrayList<Task> independentTasks = new ArrayList<Task>(); // Analogicznie jak dependentTasks


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

    public ArrayList<Task> getDependentTasks(String label) {
        ArrayList<Task> result = new ArrayList<Task>();
        for(int i=0; i<dependentTasks.size(); i+= 2){
            if(dependentTasks.get(i).getLabel().equals(label)){
                if(result.size() == 0){
                    result.add(dependentTasks.get(i));
                }
                result.add(dependentTasks.get(i+1));
            }
        }
        return result;
    }

    public String toString(){
        return "Dependency set: " + dependentTasks + "\nIndependency set: " + independentTasks;
    }
}
