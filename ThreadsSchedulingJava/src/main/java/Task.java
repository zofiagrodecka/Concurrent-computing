public class Task {
    private final char label;
    private final char modifiedResource;
    private final String operatedResources;

    Task(char label, char modifiedResource, String operatedResources){
        this.label = label;
        this.modifiedResource = modifiedResource;
        this.operatedResources = operatedResources;
    }

    public boolean isDependent(Task other){
        if(this.modifiedResource == other.modifiedResource){
            return true;
        }

        char c;
        for(int i = 0, n = this.operatedResources.length() ; i < n ; i++) {
            c = this.operatedResources.charAt(i);
            if(c == other.modifiedResource){
                return true;
            }
        }

        for(int i = 0, n = other.operatedResources.length() ; i < n ; i++) {
            c = other.operatedResources.charAt(i);
            if(c == this.modifiedResource){
                return true;
            }
        }

        return false;
    }

    public char getLabel(){
        return label;
    }

    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Task)){
            return false;
        }

        Task that = (Task) other;
        if(that.label == this.label){
            return true;
        }
        return false;
    }

    public String toString(){
        return String.valueOf(label);
    }
}
