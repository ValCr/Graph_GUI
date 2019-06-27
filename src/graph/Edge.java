package graph;

import controllers.GraphPaneController;
import info.HelpText;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Edge extends Line {
    public final static Color DEFAULT_COLOR = Color.web("#1D2129");
    public final static Color DEFAULT_SECOND_COLOR = Color.web("#3F5E7F");
    public final static float DEFAULT_STROKE_WIDTH = 3.0f;
    private static final int DEFAULT_COST = 1;
    protected Group shapes;
    private Vertex start;
    private Vertex end;
    private GraphPaneController graphPaneController;
    private Integer cost;

    public Edge(Vertex start) {
        super();
        this.shapes = new Group(this);
        this.cost = DEFAULT_COST;
        this.startXProperty()
                .bind(start.centerXProperty());
        this.startYProperty()
                .bind(start.centerYProperty());
        this.endXProperty()
                .bind(start.centerXProperty());
        this.endYProperty()
                .bind(start.centerYProperty());
        this.start = start;
        this.end = start;
        this.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        this.setStroke(DEFAULT_COLOR);
    }

    public Edge(Vertex start, Vertex end) {
        super(start.getCenterX(),
                start.getCenterY(),
                end.getCenterX(),
                end.getCenterY());
        this.shapes = new Group(this);
        this.cost = DEFAULT_COST;
        this.start = start;
        this.end = end;
        this.startYProperty()
                .bind(start.centerYProperty());
        this.startXProperty()
                .bind(start.centerXProperty());
        this.endXProperty()
                .bind(Bindings.createDoubleBinding(
                        () -> end.getCenterX() + (start.getCenterX() - end.getCenterX()) / length() * end.getRadius(),
                        end.centerXProperty(),
                        start.centerXProperty()
                ));
        this.endYProperty()
                .bind(Bindings.createDoubleBinding(
                        () -> end.getCenterY() + (start.getCenterY() - end.getCenterY()) / length() * end.getRadius(),
                        end.centerYProperty(),
                        start.centerYProperty()
                ));
        this.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        this.setStroke(DEFAULT_COLOR);

        setAllMouseEventsToDefault();
    }

    private void handleMousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.isSecondaryButtonDown()) {
            graphPaneController.removeEdge(this);
        }
    }

    private void handleMouseEntered(MouseEvent mouseEvent) {
        this.setStroke(DEFAULT_SECOND_COLOR);
        graphPaneController.getHelpInfo()
                .setText(
                        graphPaneController.graphIsOriented() ? HelpText.INFO_ARC : HelpText.INFO_EDGE
                );
    }

    private void handleMouseExited(MouseEvent mouseEvent) {
        this.setStroke(DEFAULT_COLOR);
        graphPaneController.getHelpInfo()
                .setText(HelpText.INFO_GRAPH);
    }

    protected double length() {
        return Math.sqrt(Math.pow(end.getCenterX() - start.getCenterX(),
                2) + Math.pow(end.getCenterY() - start.getCenterY(),
                2));
    }

    public boolean isNull() {
        return this.getStartX() == this.getStartY() && this.getEndX() == this.getEndY();
    }

    public void resetEdge() {
        this.endXProperty()
                .bind(start.centerXProperty());
        this.endYProperty()
                .bind(start.centerYProperty());
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

    public Vertex getOtherEnd(Vertex v) {
        return start == v ? end : start;
    }

    public Integer getCost() {
        return cost;
    }

    ///////////////////////////////////////////// Setters /////////////////////////////////////////////
    public void setAllMouseEventsToDefault() {
        this.setOnMousePressed(this::handleMousePressed);
        this.setOnMouseEntered(this::handleMouseEntered);
        this.setOnMouseExited(this::handleMouseExited);
    }

    public void setAllMouseEventsToNull() {
        this.setOnMousePressed(null);
        this.setOnMouseEntered(null);
        this.setOnMouseExited(null);
    }
}
