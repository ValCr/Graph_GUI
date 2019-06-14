package controllers;

import graph.Graph;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.LinkedList;
import java.util.List;

public class DrawVertex extends Circle {

    private List<DrawEdge> edges;
    private DrawEdge edge;
    private MainController mainController;
    private Pane graphPane;

    public DrawVertex(double centerX, double centerY, double radius, Color color) {
        super(centerX, centerY, radius, color);
        edges = new LinkedList<>();
        edge = new DrawEdge();

        this.setOnMousePressed(mouseEvent -> {
            if (edge.isNull()) {
                edge.setStart(this);
                edge.setEndX(mouseEvent.getX());
                edge.setEndY(mouseEvent.getY());
                edge.setMouseTransparent(true);
                edge.toBack();
            }
            mouseEvent.consume();
        });

        this.setOnMouseDragReleased(mouseDragEvent -> {
            DrawVertex startVertex = (DrawVertex) mouseDragEvent.getGestureSource();
            DrawVertex endVertex = (DrawVertex) mouseDragEvent.getTarget();
            int startVertexId = Integer.valueOf(startVertex.getId());
            int endVertexId = Integer.valueOf(endVertex.getId());
            Graph graph = mainController.getGraph();

            // only create a new edge if the end vertex is different from the start vertex and that there is not
            // already an edge between those two vertices
            if (mouseDragEvent.getTarget() != mouseDragEvent.getGestureSource() &&
                    !(graph.getVertices().get(startVertexId).getEdges().contains(graph.getVertices().get(endVertexId)))) {
                DrawEdge newEdge = new DrawEdge(startVertex, endVertex);
                startVertex.getEdges().add(newEdge);
                endVertex.getEdges().add(newEdge);
                graphPane.getChildren().add(newEdge);
                newEdge.toBack();
                graph.getVertices().get(startVertexId).getEdges().add(graph.getVertices().get(endVertexId));
                graph.getVertices().get(endVertexId).getEdges().add(graph.getVertices().get(startVertexId));
                graph.update();

            }
            startVertex.getEdge().resetEdge();
            mouseDragEvent.consume();
        });

        this.setOnDragDetected(mouseEvent -> {
            this.startFullDrag();
        });

        this.setOnMouseDragged(mouseEvent -> {
            edge.setEndX(mouseEvent.getX());
            edge.setEndY(mouseEvent.getY());
        });
    }

    public DrawEdge getEdge() {
        return edge;
    }

    public List<DrawEdge> getEdges() {
        return edges;
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void injectGraphPane(Pane graphPane) {
        this.graphPane = graphPane;
    }
}
