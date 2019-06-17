package controllers;

import graph.Graph;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

public class MainController {
    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private GraphPaneController graphPaneController;
    @FXML
    private InfosBoxController infosBoxController;

    private Graph graph;

    public Graph getGraph() {
        return graph;
    }

    @FXML
    private void initialize() {
        graph = new Graph();
        infosBoxController.injectMainController(this);
        graphPaneController.injectMainController(this);
        infosBoxController.bindInfosToGraph();
    }

    public void clearGraph() {
        graph.getVertices().clear();
        graphPaneController.clearGraph();
        graph.update();
    }
}
