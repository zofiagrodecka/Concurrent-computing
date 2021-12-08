import java.util.ArrayList;
import java.util.Collections;

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

    public ArrayList<ArrayList<Vertex>> FNF(){
        ArrayList<Vertex> topologicalSortedVertices = topologicalSort();
        System.out.println(topologicalSortedVertices);
        Collections.reverse(topologicalSortedVertices);
        calculateMaxLevels();
        int maxNumLevels = maxNumberOfLevels();
        ArrayList<ArrayList<Vertex>> result = new ArrayList<>(maxNumLevels+1);
        for(int i=0; i < maxNumLevels+1; i++) {
            result.add(new ArrayList<Vertex>());
        }

        for(Vertex v : topologicalSortedVertices){
            result.get(v.getMaxLevel()).add(v);
        }

        return result;
    }

    private void calculateMaxLevels(){
        ArrayList<Vertex> topologicalSortedVertices = topologicalSort();
        System.out.println(topologicalSortedVertices);
        Collections.reverse(topologicalSortedVertices);

        for(Vertex v : vertices){
            v.setVisited(false);
        }

        for(Vertex v : topologicalSortedVertices){
            int level;
            if(!v.isVisited()){
                v.setVisited(true);
                if(v.getMaxLevel() == -1){
                    level = 0;
                }
                else{
                    level = v.getMaxLevel();
                }
                calculateMaxLevel(v, level);
            }
        }
    }

    private void calculateMaxLevel(Vertex v, int level){
        if(v.getMaxLevel() < level){
            v.setMaxLevel(level);
        }

        ArrayList<Vertex> adjacent = adjacentVertices(v);
        for(Vertex neighbour : adjacent){
            calculateMaxLevel(neighbour, level+1);
        }
    }

    private int maxNumberOfLevels(){
        int max = -1;
        for(Vertex v : vertices){
            if(v.getMaxLevel() > max){
                max = v.getMaxLevel();
            }
        }
        return max;
    }

    private void transitiveReduction() {
        ArrayList<Vertex> visited = new ArrayList<Vertex>();
        ArrayList<Edge> toBeRemoved = new ArrayList<Edge>();
        ArrayList<Vertex> adjacent;
        Edge edge;

        for(Vertex u : vertices){
            adjacent = adjacentVertices(u);
            for(Vertex neighbour : adjacent){
                DFSVisit(neighbour, visited, null);
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


    private void DFSVisit(Vertex u, ArrayList<Vertex> visited, ArrayList<Vertex> stack){
        u.setVisited(true);
        ArrayList<Vertex> adjacentVertices = adjacentVertices(u);
        for(Vertex v : adjacentVertices){
            if(!v.isVisited()){
                if(visited != null){
                    visited.add(v);
                }
                DFSVisit(v, visited, stack);
            }
        }

        if(stack != null){
            stack.add(u);
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

    private ArrayList<Vertex> topologicalSort(){
        ArrayList<Vertex> stack = new ArrayList<Vertex>();
        for(Vertex v : vertices){
            v.setVisited(false);
        }

        for(Vertex v : vertices){
            if(!v.isVisited()){
                DFSVisit(v, null, stack);
            }
        }
        return stack;
    }

    public String toString(){
        return edges.toString();
    }
}
