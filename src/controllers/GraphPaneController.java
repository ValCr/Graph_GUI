package controllers;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GraphPaneController {

    @FXML
    private Pane graphPane;
    private MainController mainController;
    private Graph graph;
    private int vertexId;

    @FXML
    private void addVertex(MouseEvent event) {
        if (!event.isPrimaryButtonDown()) {
            return;
        }
        Vertex newVertex = new Vertex(event.getX(), event.getY(), 15.0f, Color.RED);
        newVertex.injectGraphPaneController(this);
        newVertex.setId(String.valueOf(vertexId++));
        newVertex.setText();
        graph.getVertices().add(newVertex);
        graphPane.getChildren().addAll(newVertex, newVertex.getEdge(), newVertex.getText());

        graphPane.setOnMouseDragReleased(mouseDragEvent -> {
            Vertex startVertex = (Vertex) mouseDragEvent.getGestureSource();
            startVertex.getEdge().resetEdge();
            startVertex.setFill(Color.RED);
        });
    }

    public void addEdge(Edge newEdge) {
        graph.getEdges().add(newEdge);
        graphPane.getChildren().add(newEdge);
        newEdge.toBack();
    }

    public void removeVertex(Vertex vertex) {
        for (Edge edge : vertex.getEdges()) {
            edge.getComplementaryAdjacentVertex(vertex).getEdges().remove(edge);
        }
        graph.getVertices()
                .stream()
                .filter(v -> Integer.valueOf(v.getId()) > Integer.valueOf(vertex.getId()))
                .forEach(v -> v.setId(String.valueOf(Integer.valueOf(v.getId()) - 1)));
        graph.getEdges().removeAll(vertex.getEdges());
        graph.getVertices().remove(vertex);
        graphPane.getChildren().removeAll(vertex.getEdges());
        graphPane.getChildren().remove(vertex.getText());
        graphPane.getChildren().remove(vertex);
        vertexId--;
    }

    public void removeEdge(Edge edge) {
        edge.getStart().getEdges().remove(edge);
        edge.getEnd().getEdges().remove(edge);
        graph.getEdges().remove(edge);
        graphPane.getChildren().remove(edge);
    }

    @FXML
    private void initialize() {
        vertexId = 1;
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
        graph = mainController.getGraph();
    }

    public void clearGraph() {
        graphPane.getChildren().clear();
        vertexId = 1;
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public int getVertexId() {
        return vertexId;
    }
}
