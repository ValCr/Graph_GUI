package graph;

import javafx.scene.shape.Line;

public class Edge extends Line {

    private final static float defaultStrokeWidth = 2.0f;
    private Vertex start;
    private Vertex end;

    public Edge(Vertex start) {
        super();
        this.start = start;
        this.setStrokeWidth(defaultStrokeWidth);
    }

    public Edge(Vertex start, Vertex end) {
        super(start.getCenterX(), start.getCenterY(), end.getCenterX(), end.getCenterY());
        this.setStrokeWidth(defaultStrokeWidth);
        this.start = start;
        this.end = end;
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

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
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
