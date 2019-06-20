package graph;

import controllers.GraphPaneController;
import info.HelpText;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

public class Vertex extends Circle {
    private final static Color DEFAULT_COLOR = Color.web("#FF2C16");
    private final static Color DEFAULT_SECOND_COLOR = Color.web("#EB8243");
    private List<Edge> edges;
    private Edge edge;
    private GraphPaneController graphPaneController;
    private Text text;

    public Vertex(double centerX, double centerY, double radius, Color color) {
        super(centerX, centerY, radius, color);
        edges = new LinkedList<>();
        if (graphPaneController == null) {
            edge = new Edge(this);
        } else {
            edge = graphPaneController.graphIsOriented() ? new Arc(this) : new Edge(this);
        }

        this.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown() && mouseEvent.isShiftDown()) {
                graphPaneController.renameVertex(this);
            }
            if (mouseEvent.isSecondaryButtonDown()) {
                graphPaneController.removeVertex(this);
            }
            mouseEvent.consume();
        });

        this.setOnMouseDragReleased(mouseDragEvent -> {
            Vertex startVertex = (Vertex) mouseDragEvent.getGestureSource();
            Vertex endVertex = (Vertex) mouseDragEvent.getTarget();

            if (startVertex != endVertex && !startVertex.isAdjacentTo(endVertex)) {
                Edge newEdge = graphPaneController.graphIsOriented() ? new Arc(startVertex, endVertex) : new Edge(startVertex, endVertex);
                startVertex.getEdges().add(newEdge);
                if (!graphPaneController.graphIsOriented()) {
                    endVertex.getEdges().add(newEdge);
                }
                newEdge.injectGraphPaneController(graphPaneController);
                graphPaneController.addEdge(newEdge);
            }
            startVertex.getEdge().resetEdge();
            startVertex.setFill(DEFAULT_COLOR);
        });

        this.setOnDragDetected(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                if (!mouseEvent.isControlDown() && edge.isNull()) {
                    edge.endXProperty().unbind();
                    edge.endYProperty().unbind();
                    edge.setEndX(mouseEvent.getX());
                    edge.setEndY(mouseEvent.getY());
                    edge.setMouseTransparent(true);
                    edge.toBack();
                }
                this.startFullDrag();
            }
        });

        this.setOnMouseDragged(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                if (mouseEvent.isControlDown()) {
                    setCenterX(mouseEvent.getX());
                    setCenterY(mouseEvent.getY());
                }
                if (!edge.isNull()) {
                    edge.endXProperty().unbind();
                    edge.endYProperty().unbind();
                    edge.setEndX(mouseEvent.getX());
                    edge.setEndY(mouseEvent.getY());
                    this.setFill(DEFAULT_SECOND_COLOR);
                }
            }
        });

        this.setOnMouseEntered(mouseEvent -> {
            this.setFill(DEFAULT_SECOND_COLOR);
            graphPaneController.getInfoText().setText(
                    graphPaneController.graphIsOriented() ? HelpText.INFO_VERTEX_ARC : HelpText.INFO_VERTEX_EDGE
            );
        });

        this.setOnMouseExited(mouseEvent -> {
            this.setFill(DEFAULT_COLOR);
            graphPaneController.getInfoText().setText(HelpText.INFO_GRAPH);
        });

        this.setOnMouseDragEntered(mouseEvent -> this.setFill(DEFAULT_SECOND_COLOR));

        this.setOnMouseDragExited(mouseEvent -> this.setFill(DEFAULT_COLOR));
    }

    public static Color getDefaultColor() {
        return DEFAULT_COLOR;
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

    public Text getText() {
        return text;
    }

    ///////////////////////////////////////////// Setters /////////////////////////////////////////////
    public void setText() {
        text = new Text();
        text.textProperty().bind(idProperty());
        text.xProperty().bind(centerXProperty().add(-text.getLayoutBounds().getWidth() / 2));
        text.yProperty().bind(centerYProperty().add(text.getLayoutBounds().getHeight() / 4));
        text.setMouseTransparent(true);
    }


}
