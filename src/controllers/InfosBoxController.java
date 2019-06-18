package controllers;

import graph.Graph;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InfosBoxController {

    @FXML
    private VBox infosBox;
    @FXML
    private CheckBox orientedGraph;
    @FXML
    private Text maxDegree;
    @FXML
    private Text minDegree;
    @FXML
    private Text graphOrder;
    @FXML
    private Text graphSize;
    private MainController mainController;

    @FXML
    private void clearGraph() {
        mainController.clearGraph();
    }

    public void bindInfosToGraph() {
        Graph graph = mainController.getGraph();
        graphOrder.textProperty().bind(Bindings.concat("Order : ", graph.orderProperty().asString()));
        graphSize.textProperty().bind(Bindings.concat("Size : ", graph.sizeProperty().asString()));
        maxDegree.textProperty().bind(Bindings.createStringBinding(
                () -> "Max " + (orientedGraph.isSelected() ? "outdegre :" : "degree :") + graph.getMaxDegree(),
                orientedGraph.selectedProperty(), graph.maxDegreeProperty()
        ));
        minDegree.textProperty().bind(Bindings.createStringBinding(
                () -> "Min " + (orientedGraph.isSelected() ? "outdegre :" : "degree :") + graph.getMinDegree(),
                orientedGraph.selectedProperty(), graph.minDegreeProperty()
        ));
    }


    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public CheckBox getOrientedGraphCheckBox() {
        return orientedGraph;
    }
}