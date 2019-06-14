package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InfosBoxController {

    @FXML
    private VBox infosBox;

    @FXML
    public Text maxDegree;

    @FXML
    public Text minDegree;

    @FXML
    private Text graphOrder;

    @FXML
    private Text graphSize;

    private MainController mainController;

    @FXML
    public void clearGraph() {
        mainController.clearGraph();
    }

    public void bindInfosToGraph() {
        graphOrder.textProperty().bind(mainController.getGraph().orderProperty().asString());
        graphSize.textProperty().bind(mainController.getGraph().sizeProperty().asString());
        maxDegree.textProperty().bind(mainController.getGraph().maxDegreeProperty().asString());
        minDegree.textProperty().bind(mainController.getGraph().minDegreeProperty().asString());
    }


    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }
}