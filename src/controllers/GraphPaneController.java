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

    @FXML
    void addVertex(MouseEvent event) {
        if (!event.isPrimaryButtonDown()) {
            return;
        }
        Graph graph = mainController.getGraph();
        Vertex newVertex = new Vertex(event.getX(), event.getY(), 15.0f, Color.RED);
        graph.getVertices().add(newVertex);
        graph.update();
        newVertex.injectGraphPaneController(this);
        graphPane.getChildren().addAll(newVertex, newVertex.getEdge());

        graphPane.setOnMouseDragReleased(mouseDragEvent -> {
            Vertex startVertex = (Vertex) mouseDragEvent.getGestureSource();
            startVertex.getEdge().resetEdge();
        });
    }

    public void addEdge(Edge newEdge) {
        Graph graph = mainController.getGraph();
        graphPane.getChildren().add(newEdge);
        newEdge.toBack();
        graph.update();
    }

    @FXML
    private void initialize() {
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void clearGraph() {
        graphPane.getChildren().clear();
    }
}
