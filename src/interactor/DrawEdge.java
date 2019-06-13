package interactor;

import javafx.scene.shape.Line;

public class DrawEdge extends Line {
    public DrawEdge() {
        super();
    }

    //Copy constructor
    public DrawEdge(DrawEdge e) {
        super(e.getStartX(), e.getStartY(), e.getEndX(), e.getEndY());
        this.setStrokeWidth(e.getStrokeWidth());
        this.setFill(e.getFill());
    }

    public DrawEdge(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }

    public DrawEdge(DrawVertex v1, DrawVertex v2) {
        super(v1.getCenterX(), v1.getCenterY(), v2.getCenterX(), v2.getCenterY());
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

    public void setStart(DrawVertex start) {
        this.setStartX(start.getCenterX());
        this.setStartY(start.getCenterY());
    }

    public void setEnd(DrawVertex end) {
        this.setEndX(end.getCenterX());
        this.setEndY(end.getCenterY());
    }

}
