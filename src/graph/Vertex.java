package graph;

import controllers.GraphPaneController;
import factory.EdgeFactory;
import factory.InfoTextFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

public class Vertex extends Circle {
    public final static Color DEFAULT_COLOR = Color.web("#FF2C16");
    public final static Color DEFAULT_COLOR_WHEN_SELECTED = Color.web("#0E0FA8");
    private final static Color DEFAULT_SECOND_COLOR = Color.web("#EB8243");
    private final List<Edge> edges;
    private final Edge edge;
    private GraphPaneController graphPaneController;
    private Text textID;
    private int startDate;
    private int endDate;

    public Vertex(double centerX, double centerY, double radius, Color color) {
        super(centerX, centerY, radius, color);
        edges = new LinkedList<>();
        edge = new Edge(this);
        startDate = 0;
        endDate = 0;
        setAllMouseEventsToDefault();
    }

    private void handleMousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown() && mouseEvent.isShiftDown()) {
            graphPaneController.renameVertex(this);
        }
        if (mouseEvent.isSecondaryButtonDown()) {
            graphPaneController.removeVertex(this);
        }
        mouseEvent.consume();
    }

    private void handleMouseDragReleased(MouseDragEvent mouseDragEvent) {
        Vertex startVertex = (Vertex) mouseDragEvent.getGestureSource();
        Vertex endVertex = (Vertex) mouseDragEvent.getTarget();

        if (startVertex != endVertex && !startVertex.pointsTo(endVertex)) {
            EdgeFactory factory = new EdgeFactory();
            Edge newEdge = factory.makeEdge(graphPaneController.getGraph()
                            .isOriented(), graphPaneController.getGraph()
                            .isFlowNetwork(),
                    startVertex,
                    endVertex);
            if (graphPaneController.getMainController().getInfoBoxController().getCostAreVisible().isSelected()) {
                graphPaneController.changeCost(newEdge);
            }
            graphPaneController.addEdge(newEdge);
        }
        startVertex.getEdge()
                .resetEdge();
        startVertex.setFill(DEFAULT_COLOR);
    }

    private void handleDragDetected(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown()) {
            if (!mouseEvent.isControlDown() && edge.isNull()) {
                edge.endXProperty()
                        .unbind();
                edge.endYProperty()
                        .unbind();
                edge.setEndX(mouseEvent.getX());
                edge.setEndY(mouseEvent.getY());
                edge.setMouseTransparent(true);
                edge.toBack();
            }
            this.startFullDrag();
        }
    }

    private void handleMouseDragged(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown()) {
            if (mouseEvent.isControlDown()) {
                setCenterX(mouseEvent.getX());
                setCenterY(mouseEvent.getY());
            }
            if (!edge.isNull()) {
                edge.endXProperty()
                        .unbind();
                edge.endYProperty()
                        .unbind();
                edge.setEndX(mouseEvent.getX());
                edge.setEndY(mouseEvent.getY());
                this.setFill(DEFAULT_SECOND_COLOR);
            }
        }
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        this.setFill(DEFAULT_SECOND_COLOR);
        InfoTextFactory factory = new InfoTextFactory();
        factory.setInfoText(graphPaneController.getHelpInfo(), graphPaneController.getGraph()
                .isOriented());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        this.setFill(DEFAULT_COLOR);
        graphPaneController.getHelpInfo().setText(InfoTextFactory.INFO_GRAPH);
    }

    private void handleMouseDragEntered(MouseDragEvent mouseEvent) {
        this.setFill(DEFAULT_SECOND_COLOR);
    }

    private void handleMouseDragExited(MouseDragEvent mouseEvent) {
        this.setFill(DEFAULT_COLOR);
    }

    public boolean pointsTo(Vertex v) {
        return edges.stream()
                .anyMatch(e -> e.isIncidentTo(v));
    }

    public boolean isAdjacentTo(Vertex v) {
        return edges.stream()
                .anyMatch(e -> e.isIncidentTo(v))
                || v.getEdges()
                .stream()
                .anyMatch(e -> e.isIncidentTo(this));
    }

    public void injectGraphPaneController(GraphPaneController graphPaneController) {
        this.graphPaneController = graphPaneController;
    }

    @Override
    public String toString() {
        return getId();
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public Edge getEdge() {
        return edge;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Text getTextID() {
        return textID;
    }

    public Edge getEdgeFromAdjacentVertex(Vertex v) {
        return edges.stream()
                .filter(e -> e.isIncidentTo(v))
                .findFirst()
                .orElseGet(() -> v.getEdges()
                        .stream()
                        .filter(e -> e.isIncidentTo(this))
                        .findFirst()
                        .orElse(null));
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public GraphPaneController getGraphPaneController() {
        return graphPaneController;
    }

    public String getStringData() {
        return getId() + ";" + getCenterX() + ";" + getCenterY();
    }

    ///////////////////////////////////////////// Setters /////////////////////////////////////////////
    public void setTextID() {
        textID = new Text();
        textID.textProperty()
                .bind(idProperty());
        textID.xProperty().bind(centerXProperty().subtract(textID.layoutBoundsProperty().getValue()
                        .getWidth() / 2));
        textID.yProperty().bind(centerYProperty().add(textID.layoutBoundsProperty().getValue()
                        .getHeight() / 4));
        textID.setMouseTransparent(true);
    }

    public void setAllMouseEventsToNull() {
        this.setOnMousePressed(null);
        this.setOnMouseDragReleased(null);
        this.setOnDragDetected(null);
        this.setOnMouseDragged(null);
        this.setOnMouseEntered(null);
        this.setOnMouseExited(null);
        this.setOnMouseDragEntered(null);
        this.setOnMouseDragExited(null);
    }

    public void setAllMouseEventsToDefault() {
        this.setOnMousePressed(this::handleMousePressed);
        this.setOnMouseDragReleased(this::handleMouseDragReleased);
        this.setOnDragDetected(this::handleDragDetected);
        this.setOnMouseDragged(this::handleMouseDragged);
        this.setOnMouseEntered(this::handleMouseEntered);
        this.setOnMouseExited(this::handleMouseExited);
        this.setOnMouseDragEntered(this::handleMouseDragEntered);
        this.setOnMouseDragExited(this::handleMouseDragExited);
    }
}
