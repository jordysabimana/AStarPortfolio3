package sample;
import java.util.ArrayList;
import java.util.PriorityQueue;
import static java.lang.StrictMath.sqrt;

public class AStarGraph {
    private ArrayList<Vertex> vertices;
    public AStarGraph() {
        vertices=new ArrayList<>();
    }
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }
    public void addvertex(Vertex v) {
        vertices.add(v);
    }
    public void newconnection(Vertex v1, Vertex v2, Double dist) {
        v1.addOutEdge(v2,dist);
        v2.addOutEdge(v1,dist);
    }
    public boolean A_Star(Vertex start, Vertex destination, boolean Euclidean) {
        if (start==null || destination==null) {
            return false;
        }
        PriorityQueue<Vertex> Openlist = new PriorityQueue<>();
        ArrayList<Vertex> Closedlist = new ArrayList<>();
        Openlist.offer(start);
        Vertex Current;
        ArrayList<Vertex> CurrentNeighbors;
        Vertex Neighbor;

        //We choose which kind of calculation is made. Manhattan or Euclidean.
        for (Vertex vertex : vertices) {
            vertex.setPrev(null);
            vertex.setg(Double.POSITIVE_INFINITY);
            if (Euclidean) {
                vertex.seth(Euclidean(vertex, destination));
            }
            else {
                vertex.seth(Manhattan(vertex, destination));
            }
        }
        start.setg(0.0);
        start.calculatef();
        System.out.println("Computing...");

        // The A* removes current value from open vertices list
        // This loops through all vertices or if the destination has been reached
        while (!Openlist.isEmpty()) {
            Current = Openlist.remove();
            if(Current == destination) {
                return true;
            }
            Closedlist.add(Current);

            // Then we loop through the closer vertices to compare them with value of current
            CurrentNeighbors = Current.getNeighbours();
            for ( int i=0; i<CurrentNeighbors.size(); i++ ) {
                Double checkG = Current.getg() + Current.getNeighbourDistance().get(i);
                Neighbor = CurrentNeighbors.get(i);

                // If there is a neighbor with lower distance
                if ( checkG < Neighbor.getg() ) {
                    Neighbor.setPrev(Current);
                    Neighbor.setg(checkG);
                    Neighbor.calculatef();
                    // The closer neighbor are added to the list
                    if (!Closedlist.contains(Neighbor) && !Openlist.contains(Neighbor) ) {
                        Openlist.offer(Neighbor);
                    }
                }
            }
        }
        // This is returned if no paths are detected
        return false;
    }

    public Double Manhattan(Vertex from,Vertex goal){
        double distX = from.getx() - goal.getx();
        double distY = from.gety() - goal.gety();
        return distX + distY;
    }

    public Double Euclidean( Vertex from,Vertex to){
        double distX = from.getx() - to.getx();
        double distY = from.gety() - to.gety();
        double sqrtDist = distX*distX + distY*distY;
        return sqrt(sqrtDist);
    }
}

class Vertex implements Comparable<Vertex>{
    private String id;
    private ArrayList<Vertex> Neighbours=new ArrayList<>();
    private ArrayList<Double> NeighbourDistance =new ArrayList<>();
    private Double f;
    private Double g;
    private Double h;
    private Integer x;
    private Integer y;
    private Vertex prev =null;

    public Vertex(String id, int x_cor,int y_cor){
        this.id=id;
        this.x=x_cor;
        this.y = y_cor;
        f=Double.POSITIVE_INFINITY;
        g=Double.POSITIVE_INFINITY;
        h=0.0;
    }

    public void addOutEdge(Vertex toV, Double dist){
        Neighbours.add(toV);
        NeighbourDistance.add(dist);
    }
    public ArrayList<Vertex> getNeighbours(){
        return Neighbours;
    }
    public ArrayList<Double> getNeighbourDistance(){
        return NeighbourDistance;
    }
    public String getid(){ return id;}
    public Integer getx(){ return x; }
    public Integer gety(){return y; }
    public Double getf() { return f; }
    public void calculatef(){ f=g+h; }
    public Double getg() { return g; }
    public void setg(Double newg){ g=newg; }
    public void seth(Double estimate){ h=estimate; }
    public Vertex getPrev() { return prev; }
    public void setPrev(Vertex v){prev=v;}
    @Override
    public int compareTo(Vertex o) {
        return getf().compareTo(o.getf());
    }
}