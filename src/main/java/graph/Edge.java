package graph;

import controllers.GraphPaneController;
import factory.EdgePropertiesFactory;
import factory.InfoTextFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class Edge extends Line {
    protected final Group shapes;
    private final Vertex start;
    private final SimpleDoubleProperty cost;
    protected GraphPaneController graphPaneController;
    private Vertex end;

    public Edge(Vertex start) {
        super();
        this.shapes = new Group(this);
        this.cost = new SimpleDoubleProperty(Constants.EDGE_DEFAULT_COST);
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
        this.setStrokeWidth(Constants.EDGE_DEFAULT_STROKE_WIDTH);
        this.setStroke(Constants.EDGE_DEFAULT_COLOR);
    }

    public Edge(Vertex start, Vertex end) {
        super(start.getCenterX(),
                start.getCenterY(),
                end.getCenterX(),
                end.getCenterY());
        this.shapes = new Group(this);
        this.start = start;
        this.end = end;
        EdgePropertiesFactory factory = new EdgePropertiesFactory();
        this.cost = factory.makeCost(
                start.getGraphPaneController() //graphPaneController isn't initialized, we must get one from the starting vertex
                        .getMainController()
                        .getInfoBoxController()
                        .getCostAreVisible(), start.getGraphPaneController()
                        .getGraph()
                        .isOriented(), this, shapes);
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
        this.setStrokeWidth(Constants.EDGE_DEFAULT_STROKE_WIDTH);
        this.setStroke(Constants.EDGE_DEFAULT_COLOR);

        setAllMouseEventsToDefault();
    }

    private void handleMousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown() && mouseEvent.isShiftDown() && graphPaneController.
                getMainController().getInfoBoxController().getCostAreVisible().isSelected()) {
            graphPaneController.changeCost(this);
        } else if (mouseEvent.isSecondaryButtonDown()) {
            graphPaneController.removeEdge(this);
        }
        mouseEvent.consume();
    }

    private void handleMouseEntered(MouseEvent mouseEvent) {
        this.setStroke(Constants.EDGE_SECOND_COLOR);
        InfoTextFactory factory = new InfoTextFactory(graphPaneController.getGraph());
        factory.setInfoText(graphPaneController.getHelpInfo(),
                graphPaneController.getMainController()
                        .getInfoBoxController()
                        .getCostAreVisible()
                        .isSelected());
    }

    private void handleMouseExited(MouseEvent mouseEvent) {
        this.setStroke(Constants.EDGE_DEFAULT_COLOR);
        graphPaneController.getHelpInfo().setText(InfoTextFactory.INFO_GRAPH);
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

    public SimpleDoubleProperty costProperty() {
        return cost;
    }

    public double getCost() {
        return cost.get();
    }

    ///////////////////////////////////////////// Setters /////////////////////////////////////////////
    public void setCost(double cost) {
        this.cost.set(cost);
    }

    public String getStringData() {
        return start.getId() + ";" + end.getId() + ";" + getCost();
    }

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
