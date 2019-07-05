package controllers;

import graph.Edge;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;


public class ChangeCostController extends BasePopup {
    private static Edge edgeToChangeCost;

    public static void setEdgeToChangeCost(Edge edge) {
        edgeToChangeCost = edge;
    }

    @Override
    protected void submit(Event event) {
        if (text.getText().matches(("-?\\d+(\\.\\d+)?"))) {
            edgeToChangeCost.setCost(Double.valueOf(text.getText()));
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            warnings.setText("Cost must be a number.");
        }
    }

    @Override
    @FXML
    protected void initialize() {
        text.setText(String.valueOf(edgeToChangeCost.getCost()));
        super.initialize();
    }
}
