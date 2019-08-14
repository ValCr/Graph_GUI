package controllers;

import graph.Edge;
import graph.FlowEdge;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ChangeCostController extends BasePopup {
    private static Edge edgeToChangeCost;
    @FXML
    private TextField capacity;

    public static void setEdgeToChangeCost(Edge edge) {
        edgeToChangeCost = edge;
    }

    @Override
    protected void submit(Event event) {
        if (!capacity.isDisable()) {
            if (capacity.getText()
                    .matches("\\d+") && text.getText()
                    .matches("\\d+(\\.\\d+)?") &&
                    Double.valueOf(text.getText()) <= Integer.valueOf(capacity.getText())) {
                ((FlowEdge) edgeToChangeCost).setCapacity(Integer.valueOf(capacity.getText()));
                edgeToChangeCost.setCost(Double.valueOf(text.getText()));
                closePopup(event);
            } else {
                warnings.setText("Capacity must be a positive integer.\n" +
                        "Cost must be a positive number.\n + Cost must be inferior to Capacity");
            }
        } else if (text.getText()
                .matches(("-?\\d+(\\.\\d+)?"))) {
            edgeToChangeCost.setCost(Double.valueOf(text.getText()));
            closePopup(event);
        } else {
            warnings.setText("Cost must be a number.");
        }
    }

    @Override
    @FXML
    protected void initialize() {
        text.setText(String.valueOf(edgeToChangeCost.getCost()));
        if (edgeToChangeCost instanceof FlowEdge) {
            capacity.setText(String.valueOf(((FlowEdge) edgeToChangeCost).getCapacity()));
        } else {
            capacity.setDisable(true);
        }
        super.initialize();
    }
}
