package factory;

import graph.Constants;
import graph.Edge;
import graph.FlowEdge;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class EdgePropertiesFactory {
    private double MULTIPLIER_WHEN_ORIENTED = 0.7;

    public SimpleDoubleProperty makeCost(
            CheckBox costIsVisible, boolean graphIsOriented, Edge edge, Group shapes) {
        SimpleDoubleProperty cost = new SimpleDoubleProperty(Constants.EDGE_DEFAULT_COST);
        double MULTIPLIER_WHEN_NOT_ORIENTED = 0.5;
        Text textCost = new Text();
        textCost.setFill(Color.RED);
        shapes.getChildren()
                .add(textCost);
        textCost.textProperty()
                .bind(cost.asString());
        textCost.xProperty()
                .bind(edge.startXProperty()
                        .add(edge.endXProperty()
                                .subtract(edge.startXProperty())
                                .subtract(textCost.layoutBoundsProperty()
                                        .getValue()
                                        .getWidth() / 2)
                                .multiply(graphIsOriented ? MULTIPLIER_WHEN_ORIENTED : MULTIPLIER_WHEN_NOT_ORIENTED)));
        textCost.yProperty()
                .bind(edge.startYProperty()
                        .add(edge.endYProperty()
                                .subtract(edge.startYProperty())
                                .multiply(graphIsOriented ? MULTIPLIER_WHEN_ORIENTED : MULTIPLIER_WHEN_NOT_ORIENTED)
                                .subtract(5)));
        textCost.visibleProperty()
                .bind(costIsVisible.selectedProperty());
        return cost;
    }

    public SimpleIntegerProperty makeCapacity(
            CheckBox flowNetwork, FlowEdge flowEdge, Group shapes) {
        SimpleIntegerProperty capacity = new SimpleIntegerProperty(Constants.FLOW_EDGE_DEFAULT_CAPACITY);
        Text textCapacity = new Text();
        textCapacity.setFill(Color.BLUE);
        shapes.getChildren()
                .add(textCapacity);
        textCapacity.textProperty()
                .bind(Bindings.concat(", ")
                        .concat(capacity.asString()));
        textCapacity.xProperty()
                .bind(flowEdge.startXProperty()
                        .add(flowEdge.endXProperty()
                                .subtract(flowEdge.startXProperty())
                                .subtract(textCapacity.layoutBoundsProperty()
                                        .getValue()
                                        .getWidth() / 2)
                                .add(flowEdge.costProperty()
                                        .asString()
                                        .length()
                                        .multiply(12))
                                .multiply(MULTIPLIER_WHEN_ORIENTED)));
        textCapacity.yProperty()
                .bind(flowEdge.startYProperty()
                        .add(flowEdge.endYProperty()
                                .subtract(flowEdge.startYProperty())
                                .multiply(MULTIPLIER_WHEN_ORIENTED)
                                .subtract(5)));
        textCapacity.visibleProperty()
                .bind(flowNetwork.selectedProperty());
        return capacity;
    }
}
