package graph;

import controllers.GraphPaneController;
import factory.EdgeFactory;
import info.HelpText;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

public class Vertex extends Circle {
    public final static Color DEFAULT_COLOR = Color.web("#FF2C16");
    public final static Color DEFAULT_SECOND_COLOR = Color.web("#EB8243");
    public final static Color DEFAULT_COLOR_WHEN_SELECTED = Color.web("#0E0FA8");
    private List<Edge> edges;
    private Edge edge;
    private GraphPaneController graphPaneController;
    private Text text;
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

    public void handleMousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown() && mouseEvent.isShiftDown()) {
            graphPaneController.renameVertex(this);
        }
        if (mouseEvent.isSecondaryButtonDown()) {
            graphPaneController.removeVertex(this);
        }
        mouseEvent.consume();
    }

    public void handleMouseDragReleased(MouseDragEvent mouseDragEvent) {
        Vertex startVertex = (Vertex) mouseDragEvent.getGestureSource();
        Vertex endVertex = (Vertex) mouseDragEvent.getTarget();

        if (startVertex != endVertex && !startVertex.pointsTo(endVertex)) {
            EdgeFactory factory = new EdgeFactory();
            Edge newEdge = factory.makeEdge(graphPaneController.graphIsOriented(),
                    startVertex,
                    endVertex);
            graphPaneController.addEdge(newEdge);
        }
        startVertex.getEdge()
                .resetEdge();
        startVertex.setFill(DEFAULT_COLOR);
    }

    public void handleDragDetected(MouseEvent mouseEvent) {
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

    public void handleMouseDragged(MouseEvent mouseEvent) {
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
        graphPaneController.getHelpInfo()
                .setText(
                        graphPaneController.graphIsOriented() ? HelpText.INFO_VERTEX_ARC : HelpText.INFO_VERTEX_EDGE
                );
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        this.setFill(DEFAULT_COLOR);
        graphPaneController.getHelpInfo().setText(HelpText.INFO_GRAPH);
    }

    public void handleMouseDragEntered(MouseDragEvent mouseEvent) {
        this.setFill(DEFAULT_SECOND_COLOR);
    }

    public void handleMouseDragExited(MouseDragEvent mouseEvent) {
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

    public Text getText() {
        return text;
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

    public int getStartDate() {
        return startDate;
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

    ///////////////////////////////////////////// Setters /////////////////////////////////////////////
    public void setText() {
        text = new Text();
        text.textProperty()
                .bind(idProperty());
        text.xProperty().bind(centerXProperty().add(-text.layoutBoundsProperty().getValue()
                        .getWidth() / 2));
        text.yProperty().bind(centerYProperty().add(text.layoutBoundsProperty().getValue()
                        .getHeight() / 4));
        text.setMouseTransparent(true);
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
