package controllers;

import graph.Graph;
import javafx.fxml.FXML;

public class MainController {
    @FXML
    private GraphPaneController graphPaneController;
    @FXML
    private InfoBoxController infosBoxController;

    private Graph graph;

    public Graph getGraph() {
        return graph;
    }

    @FXML
    private void initialize() {
        graph = new Graph();
        infosBoxController.injectMainController(this);
        graphPaneController.injectMainController(this);
        infosBoxController.bindInfoToGraph();
    }

    public void clearGraph() {
        graph.getVertices().clear();
        graph.getEdges().clear();
        graphPaneController.clearGraph();
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public GraphPaneController getGraphPaneController() {
        return graphPaneController;
    }

    public InfoBoxController getInfosBoxController() {
        return infosBoxController;
    }
}
