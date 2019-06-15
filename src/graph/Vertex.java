package graph;

import controllers.GraphPaneController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.LinkedList;
import java.util.List;

public class Vertex extends Circle {

    private List<Edge> edges;
    private Edge edge;
    private GraphPaneController graphPaneController;

    public Vertex(double centerX, double centerY, double radius, Color color) {
        super(centerX, centerY, radius, color);
        edges = new LinkedList<>();
        edge = new Edge(this);

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
            Vertex startVertex = (Vertex) mouseDragEvent.getGestureSource();
            Vertex endVertex = (Vertex) mouseDragEvent.getTarget();

            if (startVertex != endVertex && !startVertex.isAdjacentTo(endVertex)) {
                Edge newEdge = new Edge(startVertex, endVertex);
                startVertex.getEdges().add(newEdge);
                endVertex.getEdges().add(newEdge);
                graphPaneController.addEdge(newEdge);
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

    private boolean isAdjacentTo(Vertex endVertex) {
        return edges.stream().anyMatch(e -> e.isIncidentTo(endVertex));
    }

    public void injectGraphPaneController(GraphPaneController graphPaneController) {
        this.graphPaneController = graphPaneController;
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public Edge getEdge() {
        return edge;
    }

    public List<Edge> getEdges() {
        return edges;
    }

}
