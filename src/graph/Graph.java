package graph;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.LinkedList;
import java.util.List;

public class Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;
    private SimpleIntegerProperty order;
    private SimpleIntegerProperty size;
    private SimpleIntegerProperty maxDegree;
    private SimpleIntegerProperty minDegree;

    public Graph() {
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
        order = new SimpleIntegerProperty(0);
        size = new SimpleIntegerProperty(0);
        maxDegree = new SimpleIntegerProperty(0);
        minDegree = new SimpleIntegerProperty(0);
    }

    public void update() {
        setOrder();
        setSize();
        setMinDegree();
        setMaxDegree();
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getOrder() {
        return order.get();
    }

    public SimpleIntegerProperty orderProperty() {
        return order;
    }

    public int getSize() {
        return size.get();
    }

    public SimpleIntegerProperty sizeProperty() {
        return size;
    }

    public int getMaxDegree() {
        return maxDegree.get();
    }

    public SimpleIntegerProperty maxDegreeProperty() {
        return maxDegree;
    }

    public int getMinDegree() {
        return minDegree.get();
    }

    public SimpleIntegerProperty minDegreeProperty() {
        return minDegree;
    }


    ///////////////////////////////////////////// Setters /////////////////////////////////////////////
    public void setMinDegree() {
        minDegree.set(vertices.stream().mapToInt(v -> v.getEdges().size()).min().orElse(0));
    }

    public void setMaxDegree() {
        maxDegree.set(vertices.stream().mapToInt(v -> v.getEdges().size()).max().orElse(0));
    }

    public void setOrder() {
        order.set(vertices.size());
    }

    public void setSize() {
        size.set(edges.size());
    }
}