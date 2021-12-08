public class Edge {
    Vertex v1;
    Vertex v2;

    Edge(Vertex v1, Vertex v2){
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vertex getV1(){
        return v1;
    }

    public Vertex getV2(){
        return v2;
    }

    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Edge)){
            return false;
        }

        Edge that = (Edge) other;
        if(that.v1 == this.v1 && that.v2 == this.v2){
            return true;
        }
        return false;
    }

    public String toString(){
        return "(" + v1.toString() + ", " + v2.toString() + ")";
    }
}
