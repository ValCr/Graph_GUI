package graph;

import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.shape.Line;

public class Arc extends Edge {
    private final static float ARROW_LENGTH = 15.0f;
    private final static float ARROW_HALF_WIDTH = 10.0f;

    public Arc(Vertex start, Vertex end) {
        super(start,
                end);
        shapes.getChildren()
                .add(getArrow());
    }

    private Group getArrow() {
        Line line1 = new Line();
        Line line2 = new Line();

        // set the arrow's color and stroke width same as arc
        line1.strokeProperty()
                .bind(strokeProperty());
        line2.strokeProperty()
                .bind(strokeProperty());
        line1.strokeWidthProperty()
                .bind(strokeWidthProperty());
        line2.strokeWidthProperty()
                .bind(strokeWidthProperty());

        // move the arrow with the arc
        line1.startXProperty()
                .bind(endXProperty());
        line1.startYProperty()
                .bind(endYProperty());
        line1.endXProperty()
                .bind(Bindings.createDoubleBinding(
                        () -> line1.getStartX() - ARROW_LENGTH * getUnitVectorX()
                                + ARROW_HALF_WIDTH * -getUnitVectorY(),
                        startXProperty(),
                        endXProperty(),
                        startYProperty(),
                        endYProperty()
                ));
        line1.endYProperty()
                .bind(Bindings.createDoubleBinding(
                        () -> line1.getStartY() - ARROW_LENGTH * getUnitVectorY()
                                + ARROW_HALF_WIDTH * getUnitVectorX(),
                        startXProperty(),
                        endXProperty(),
                        startYProperty(),
                        endYProperty()
                ));

        line2.startXProperty()
                .bind(endXProperty());
        line2.startYProperty()
                .bind(endYProperty());
        line2.endXProperty()
                .bind(Bindings.createDoubleBinding(
                        () -> line2.getStartX() - ARROW_LENGTH * getUnitVectorX()
                                - ARROW_HALF_WIDTH * -getUnitVectorY(),
                        startXProperty(),
                        endXProperty(),
                        startYProperty(),
                        endYProperty()
                ));
        line2.endYProperty()
                .bind(Bindings.createDoubleBinding(
                        () -> line2.getStartY() - ARROW_LENGTH * getUnitVectorY()
                                - ARROW_HALF_WIDTH * getUnitVectorX(),
                        startXProperty(),
                        endXProperty(),
                        startYProperty(),
                        endYProperty()
                ));
        return new Group(line1,
                line2);
    }

    private double getUnitVectorY() {
        return (getEndY() - getStartY()) / length();
    }

    private double getUnitVectorX() {
        return (getEndX() - getStartX()) / length();
    }

}
