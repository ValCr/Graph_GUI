package factory;

import graph.Edge;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;

public class CostFactory {

    public SimpleDoubleProperty makeCost(CheckBox costIsVisible, boolean graphIsOriented, Edge edge, Group shapes) {
        double MULTIPLIER_WHEN_NOT_ORIENTED = 0.5, MULTIPLIER_WHEN_ORIENTED = 0.82;
        SimpleDoubleProperty cost = new SimpleDoubleProperty(Edge.DEFAULT_COST);
        Text textCost = new Text();
        shapes.getChildren().add(textCost);
        textCost.textProperty().bind(cost.asString());
        textCost.xProperty().bind(edge.startXProperty().add(edge.endXProperty().subtract(edge.startXProperty())
                .subtract(textCost.layoutBoundsProperty().getValue().getWidth() / 2)
                .multiply(graphIsOriented ? MULTIPLIER_WHEN_ORIENTED : MULTIPLIER_WHEN_NOT_ORIENTED)));
        textCost.yProperty().bind(edge.startYProperty().add(edge.endYProperty().subtract(edge.startYProperty())
                .multiply(graphIsOriented ? MULTIPLIER_WHEN_ORIENTED : MULTIPLIER_WHEN_NOT_ORIENTED)
                .subtract(5)));
        textCost.visibleProperty().bind(costIsVisible.selectedProperty());
        return cost;
    }
}
