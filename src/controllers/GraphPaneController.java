package controllers;

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
    private int vertexId;

    @FXML
    void addVertex(MouseEvent event) {
        if (!event.isPrimaryButtonDown()) {
            return;
        }
        ///////////////////////////////////// Logic /////////////////////////////////////
        Graph graph = mainController.getGraph();
        graph.getVertices().add(new Vertex());
        graph.update();

        ///////////////////////////////////// Drawing /////////////////////////////////////
        DrawVertex newVertex = new DrawVertex(event.getX(), event.getY(), 15.0f, Color.RED);
        newVertex.setId(String.valueOf(vertexId++));
        newVertex.injectMainController(mainController);
        newVertex.injectGraphPane(graphPane);
        graphPane.getChildren().addAll(newVertex, newVertex.getEdge());

        graphPane.setOnMouseDragReleased(mouseDragEvent -> {
            DrawVertex startVertex = (DrawVertex) mouseDragEvent.getGestureSource();
            startVertex.getEdge().resetEdge();
        });
    }

    @FXML
    private void initialize() {
        vertexId = 0;
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void clearGraph() {
        vertexId = 0;
        graphPane.getChildren().clear();
    }
}
