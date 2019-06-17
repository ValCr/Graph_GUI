package graph;

import controllers.GraphPaneController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Edge extends Line {

    private final static float DEFAULT_STROKE_WIDTH = 3.0f;
    private Vertex start;
    private Vertex end;
    private GraphPaneController graphPaneController;

    public Edge(Vertex start) {
        super();
        this.start = start;
        this.setStrokeWidth(DEFAULT_STROKE_WIDTH);
    }

    public Edge(Vertex start, Vertex end) {
        super(start.getCenterX(), start.getCenterY(), end.getCenterX(), end.getCenterY());
        this.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        this.start = start;
        this.end = end;

        this.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isSecondaryButtonDown()) {
                graphPaneController.removeEdge(this);
            }
        });

        this.setOnMouseEntered(mouseEvent -> this.setStroke(Color.GREY));

        this.setOnMouseExited(mouseEvent -> this.setStroke(Color.BLACK));
    }

    public boolean isNull() {
        return this.getStartX() == this.getStartY() && this.getEndX() == this.getEndY();
    }

    public void resetEdge() {
        this.setStartX(0.0f);
        this.setStartY(0.0f);
        this.setEndX(0.0f);
        this.setEndY(0.0f);
    }

    public boolean isIncidentTo(Vertex v) {
        return start == v || end == v;
    }

    public void injectGraphPaneController(GraphPaneController graphPaneController) {
        this.graphPaneController = graphPaneController;
    }

    public void updatePositionToIncidentVertex(Vertex vertex) {
        if (vertex == start) {
            this.setStartX(vertex.getCenterX());
            this.setStartY(vertex.getCenterY());
        } else if (vertex == end) {
            this.setEndX(vertex.getCenterX());
            this.setEndY(vertex.getCenterY());
        }
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }

    public Vertex getComplementaryAdjacentVertex(Vertex v) {
        return v == start ? end : start;
    }

    ///////////////////////////////////////////// Setters /////////////////////////////////////////////
    public void setStart(Vertex start) {
        this.setStartX(start.getCenterX());
        this.setStartY(start.getCenterY());
        this.start = start;
    }

    public void setEnd(Vertex end) {
        this.setEndX(end.getCenterX());
        this.setEndY(end.getCenterY());
        this.end = end;
    }
}
