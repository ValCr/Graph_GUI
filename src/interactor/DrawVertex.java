package interactor;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.LinkedList;
import java.util.List;

public class DrawVertex extends Circle {
    private List<DrawEdge> edges;
    private DrawEdge edge;

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
                edge.setStrokeWidth(2.0f);
            }
            mouseEvent.consume();
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
}
