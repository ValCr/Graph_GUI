package graph;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.List;

public class Graph {
    private SimpleListProperty<Vertex> vertices;
    private SimpleListProperty<Edge> edges;
    private SimpleIntegerProperty size;
    private SimpleIntegerProperty order;
    private SimpleIntegerProperty maxDegree; // also outdegree when graph is oriented
    private SimpleIntegerProperty minDegree; // also outdegree when graph is oriented
    private SimpleIntegerProperty maxIndegree;
    private SimpleIntegerProperty minIndegree;

    public Graph() {
        vertices = new SimpleListProperty<>(FXCollections.observableArrayList());
        edges = new SimpleListProperty<>(FXCollections.observableArrayList());
        order = new SimpleIntegerProperty(0);
        size = new SimpleIntegerProperty(0);
        maxDegree = new SimpleIntegerProperty(0);
        minDegree = new SimpleIntegerProperty(0);
        maxIndegree = new SimpleIntegerProperty(0);
        minIndegree = new SimpleIntegerProperty(0);

        order.bind(vertices.sizeProperty());
        size.bind(edges.sizeProperty());
        maxDegree.bind(Bindings.createIntegerBinding(
                () -> vertices.stream()
                        .mapToInt(v -> v.getEdges()
                                .size())
                        .max()
                        .orElse(0),
                vertices.sizeProperty(),
                edges.sizeProperty()
        ));
        minDegree.bind(Bindings.createIntegerBinding(
                () -> vertices.stream()
                        .mapToInt(v -> v.getEdges()
                                .size())
                        .min()
                        .orElse(0),
                vertices.sizeProperty(),
                edges.sizeProperty()
        ));
        maxIndegree.bind(Bindings.createIntegerBinding(
                () -> vertices.stream()
                        .mapToInt(v -> Math.toIntExact(edges.stream()
                                .filter(e -> e.getEnd() == v)
                                .count()))
                        .max()
                        .orElse(0),
                vertices.sizeProperty(),
                edges.sizeProperty()
        ));
        minIndegree.bind(Bindings.createIntegerBinding(
                () -> vertices
                        .stream()
                        .mapToInt(v -> Math.toIntExact(edges.stream()
                                .filter(e -> e.getEnd() == v)
                                .count()))
                        .min()
                        .orElse(0),
                vertices.sizeProperty(),
                edges.sizeProperty()
        ));
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

    public int getMaxIndegree() {
        return maxIndegree.get();
    }

    public SimpleIntegerProperty maxIndegreeProperty() {
        return maxIndegree;
    }

    public int getMinIndegree() {
        return minIndegree.get();
    }

    public SimpleIntegerProperty minIndegreeProperty() {
        return minIndegree;
    }
}