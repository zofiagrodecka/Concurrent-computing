import java.util.*;

public class Set {
    private final Alphabet alphabet;
    public ArrayList<Task> dependentTasks = new ArrayList<Task>(); // Dwa pierwsze elementy są ze sobą zależne, potem kolejne dwa są ze sobą zależne, potem kolejna para, itd...
    public ArrayList<Task> independentTasks = new ArrayList<Task>(); // Analogicznie jak dependentTasks


    Set(Alphabet alphabet){
        this.alphabet = alphabet;
        calculateDependency();
    }

    private void addDependentPair(Task task1, Task task2){
        dependentTasks.add(task1);
        dependentTasks.add(task2);
    }

    private void addIndependentPair(Task task1, Task task2){
        independentTasks.add(task1);
        independentTasks.add(task2);
    }

    public void calculateDependency(){ // dodaje zależne/niezależne pary do odpowiednich zbiorów
        Task t1, t2;
        for(int i=0; i<alphabet.tasks.size(); i++){
            for(int j=0; j<alphabet.tasks.size(); j++){
                t1 = alphabet.tasks.get(i);
                t2 = alphabet.tasks.get(j);
                if(t1.isDependent(t2)){
                    addDependentPair(t1, t2);
                }
                else{
                    addIndependentPair(t1, t2);
                }

            }
        }
    }

    public ArrayList<Task> getDependentTasks(char label) { // zwraca zależne taski od tego z podaną etykietą
        ArrayList<Task> result = new ArrayList<Task>();
        for(int i=0; i<dependentTasks.size(); i+= 2){
            if(dependentTasks.get(i).getLabel() == label){
                result.add(dependentTasks.get(i+1));
            }
        }
        return result;
    }

    public ArrayList<ArrayList<Task>> FNF(String word) {
        // alokacja stosów
        ArrayList<Stack<Task>> stacks = new ArrayList<>(alphabet.size());
        for (int i = 0; i < alphabet.size(); i++) {
            stacks.add(new Stack<Task>());
        }

        char c;
        Task currentTask;
        int index;
        Task empytTask = new Task(' ', ' ', " ");
        ArrayList<Task> dependent;

        // Dodawanie elementów do stosów
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

        // Usuwanie elementów ze stosów
        ArrayList<ArrayList<Task>> fnf = new ArrayList<>();
        ArrayList<Integer> stacksToBePoped = new ArrayList<Integer>();
        while(! allStacksEmpty(stacks)){
            fnf.add(new ArrayList<Task>());
            for(int i=0; i<alphabet.size(); i++){
                if(! stacks.get(i).empty()){
                    currentTask = stacks.get(i).peek(); // podejrzenie wierzchniego elementu i-tego stosu
                    if(! empytTask.equals(currentTask)){
                        fnf.get(fnf.size()-1).add(currentTask);
                        stacks.get(i).pop();
                        // przygotowanie zbioru stosów zależnych od bieżącego zadania, z których będą usuwane emptyTaski z góry
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
            for(Integer number : stacksToBePoped){ // usuwanie pierwszych elementów ze stosów
                stacks.get(number).pop();
            }
            stacksToBePoped.clear();
        }

        return fnf;
    }

    private boolean allStacksEmpty(ArrayList<Stack<Task>> stacks){ // czy wszystkie stosy są puste
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
