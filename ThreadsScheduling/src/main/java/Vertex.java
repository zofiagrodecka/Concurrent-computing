public class Vertex {
    String label;
    int id;
    boolean visited = false;

    Vertex(String label, int id){
        this.label = label;
        this.id = id;
    }

    public boolean isVisited(){
        return visited;
    }

    public String getLabel(){
        return label;
    }

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Vertex)){
            return false;
        }

        Vertex that = (Vertex) other;
        if(that.id == this.id && that.label.equals(this.label)){
            return true;
        }
        return false;
    }

    public String toString(){
        return label + ":" + id;
    }

}
