package graph;

import controllers.GraphPaneController;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Edge extends Line {

    private final static float DEFAULT_STROKE_WIDTH = 3.0f;
    private Vertex start;
    private Vertex end;
    private GraphPaneController graphPaneController;
    protected Group shapes;

    public Edge(Vertex start) {
        super();
        this.shapes = new Group(this);
        this.startXProperty().bind(start.centerXProperty());
        this.startYProperty().bind(start.centerYProperty());
        this.endXProperty().bind(start.centerXProperty());
        this.endYProperty().bind(start.centerYProperty());
        this.start = start;
        this.end = start;
        this.setStrokeWidth(DEFAULT_STROKE_WIDTH);
    }

    public Edge(Vertex start, Vertex end) {
        super(start.getCenterX(), start.getCenterY(), end.getCenterX(), end.getCenterY());
        this.shapes = new Group(this);
        this.start = start;
        this.end = end;
        this.startYProperty().bind(start.centerYProperty());
        this.startXProperty().bind(start.centerXProperty());
        this.endXProperty().bind(Bindings.createDoubleBinding(
                () -> end.getCenterX() + (start.getCenterX() - end.getCenterX()) / length() * end.getRadius(),
                end.centerXProperty(), start.centerXProperty()
        ));
        this.endYProperty().bind(Bindings.createDoubleBinding(
                () -> end.getCenterY() + (start.getCenterY() - end.getCenterY()) / length() * end.getRadius(),
                end.centerYProperty(), start.centerYProperty()
        ));
        this.setStrokeWidth(DEFAULT_STROKE_WIDTH);

        this.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isSecondaryButtonDown()) {
                graphPaneController.removeEdge(this);
            }
        });

        this.setOnMouseEntered(mouseEvent -> this.setStroke(Color.GREY));

        this.setOnMouseExited(mouseEvent -> this.setStroke(Color.BLACK));
    }

    protected double length() {
        return Math.sqrt(Math.pow(end.getCenterX() - start.getCenterX(), 2) + Math.pow(end.getCenterY() - start.getCenterY(), 2));
    }

    public boolean isNull() {
        return this.getStartX() == this.getStartY() && this.getEndX() == this.getEndY();
    }

    public void resetEdge() {
        this.endXProperty().bind(start.centerXProperty());
        this.endYProperty().bind(start.centerYProperty());
        this.end = start;
    }

    public boolean isIncidentTo(Vertex v) {
        return start == v || end == v;
    }

    public void injectGraphPaneController(GraphPaneController graphPaneController) {
        this.graphPaneController = graphPaneController;
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }

    public Group getShapes() {
        return shapes;
    }
}
