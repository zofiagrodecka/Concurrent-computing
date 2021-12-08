import java.util.ArrayList;

public class Graph {
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private String trace;
    private Set set;

    Graph(String word, Set set){
        this.trace = word;
        this.set = set;
        create();
    }

    public void create(){
        char c;
        Vertex current;
        for(int i=0; i<trace.length(); i++){
            c = trace.charAt(i);
            current = new Vertex(String.valueOf(c), i);
            vertices.add(current);
            for(Vertex v : vertices){
                if(!v.equals(current)){
                    for(Task task : set.getDependentTasks(current.getLabel())){
                        if(task.getLabel().equals(v.getLabel())){
                            edges.add(new Edge(v, current));
                        }
                    }
                }
            }
        }
        System.out.println(this);
        transitiveReduction();
    }

    private void transitiveReduction() {
        ArrayList<Vertex> visited = new ArrayList<Vertex>();
        ArrayList<Edge> toBeRemoved = new ArrayList<Edge>();
        ArrayList<Vertex> adjacent;
        Edge edge;

        for(Vertex u : vertices){
            adjacent = adjacentVertices(u);
            for(Vertex neighbour : adjacent){
                DFSVisit(neighbour, visited);
            }

            for(Vertex v : visited){
                edge = new Edge(u,v);
                if(containEdge(edge)){
                    toBeRemoved.add(edge);
                }
                v.setVisited(false);
            }
            visited.clear();

        }

        edges.removeAll(toBeRemoved);
    }

    private boolean containEdge(Edge edge){
        for(Edge e: edges){
            if(e.equals(edge)){
                return true;
            }
        }
        return false;
    }

    private ArrayList<Vertex> adjacentVertices(Vertex v){
        ArrayList<Vertex> result = new ArrayList<Vertex>();
        for(Edge e : edges){
            if(e.getV1() == v){
                result.add(e.getV2());
            }
        }
        return result;
    }


    private void DFSVisit(Vertex u, ArrayList<Vertex> visited){
        u.setVisited(true);
        ArrayList<Vertex> adjacentVertices = adjacentVertices(u);
        for(Vertex v : adjacentVertices){
            if(!v.isVisited()){
                visited.add(v);
                DFSVisit(v, visited);
            }
        }
    }

    /*private void DFS(Vertex start) {
        start.setVisited(true);
        //ArrayList<Edge> adjacentEdges = edges.stream().filter(edge -> edge.getV1().equals(startVertex)).collect(Collectors.toList());
        for(Vertex v : vertices){
            if(!v.isVisited()){
                DFSVisit(v);
            }
        }
    }*/

    /*private ArrayList<Vertex> topologicalSort(){

    }*/

    public String toString(){
        return edges.toString();
    }
}
