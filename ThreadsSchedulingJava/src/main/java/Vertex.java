public class Vertex {
    private final char label;
    private final int id;
    private boolean visited = false;
    private int maxLevel = -1;

    Vertex(char label, int id){
        this.label = label;
        this.id = id;
    }

    public boolean isVisited(){
        return visited;
    }

    public int getId(){
        return id;
    }

    public char getLabel(){
        return label;
    }

    public int getMaxLevel(){
        return maxLevel;
    }

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public void setMaxLevel(int value){
        this.maxLevel = value;
    }

    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Vertex)){
            return false;
        }

        Vertex that = (Vertex) other;
        if(that.id == this.id && that.label == this.label){
            return true;
        }
        return false;
    }

    public String toString(){
        return label + ":" + id;
    }

}
