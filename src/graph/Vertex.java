package graph;

import java.util.LinkedList;
import java.util.List;

public class Vertex {
    private List<Vertex> edges;

    public Vertex() {
        this.edges = new LinkedList<>();
    }

    public List<Vertex> getEdges() {
        return edges;
    }
}
