package graph;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.List;

public class Graph {
    private final SimpleIntegerProperty minDegree; // also outdegree when graph is oriented
    private final SimpleListProperty<Vertex> vertices;
    private final SimpleListProperty<Edge> edges;
    private final SimpleIntegerProperty size;
    private final SimpleIntegerProperty order;
    private final SimpleIntegerProperty maxDegree; // also outdegree when graph is oriented
    private final SimpleIntegerProperty maxIndegree;
    private final SimpleIntegerProperty minIndegree;
    private final SimpleBooleanProperty oriented;
    private boolean containsCircuit;

    public Graph() {
        vertices = new SimpleListProperty<>(FXCollections.observableArrayList());
        edges = new SimpleListProperty<>(FXCollections.observableArrayList());
        order = new SimpleIntegerProperty(0);
        size = new SimpleIntegerProperty(0);
        maxDegree = new SimpleIntegerProperty(0);
        minDegree = new SimpleIntegerProperty(0);
        maxIndegree = new SimpleIntegerProperty(0);
        minIndegree = new SimpleIntegerProperty(0);
        oriented = new SimpleBooleanProperty(false);
        containsCircuit = false;

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

    public boolean containsCircuit() {
        return containsCircuit;
    }

    public void setContainsCircuit(boolean containsCircuit) {
        this.containsCircuit = containsCircuit;
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public SimpleIntegerProperty orderProperty() {
        return order;
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

    public boolean isOriented() {
        return oriented.get();
    }

    public SimpleBooleanProperty orientedProperty() {
        return oriented;
    }

    public Vertex getVertexFromID(String id) {
        return vertices.stream().filter(v -> v.getId().equals(id)).findFirst().orElseThrow();
    }
}