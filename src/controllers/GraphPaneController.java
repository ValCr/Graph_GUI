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

    @FXML
    private void addVertex(MouseEvent event) {
        if (!event.isPrimaryButtonDown()) {
            return;
        }
        Vertex newVertex = new Vertex(event.getX(), event.getY(), 15.0f, Color.RED);
        graph.getVertices().add(newVertex);
        graph.update();
        newVertex.injectGraphPaneController(this);
        graphPane.getChildren().addAll(newVertex, newVertex.getEdge());

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
        graph.update();
    }

    public void removeVertex(Vertex vertex) {
        for (Edge edge : vertex.getEdges()) {
            edge.getComplementaryAdjacentVertex(vertex).getEdges().remove(edge);
        }
        graph.getEdges().removeAll(vertex.getEdges());
        graph.getVertices().remove(vertex);
        graphPane.getChildren().removeAll(vertex.getEdges());
        graphPane.getChildren().remove(vertex);
        graph.update();
    }

    public void removeEdge(Edge edge) {
        edge.getStart().getEdges().remove(edge);
        edge.getEnd().getEdges().remove(edge);
        graph.getEdges().remove(edge);
        graphPane.getChildren().remove(edge);
        graph.update();
    }

    @FXML
    private void initialize() {
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
        graph = mainController.getGraph();
    }

    public void clearGraph() {
        graphPane.getChildren().clear();
    }

}
