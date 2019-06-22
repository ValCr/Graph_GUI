package controllers;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

public class MainController {
    @FXML
    private SplitPane mainSplitPane;
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
        graph.getVertices()
                .clear();
        graph.getEdges()
                .clear();
        graphPaneController.clearGraph();
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public GraphPaneController getGraphPaneController() {
        return graphPaneController;
    }

    public InfoBoxController getInfosBoxController() {
        return infosBoxController;
    }

    public SplitPane getMainSplitPane() {
        return mainSplitPane;
    }

    public void setAllVertexEventsToNull() {
        graph.getVertices().forEach(Vertex::setAllMouseEventsToNull);
    }

    public void setAllEdgesEventsToNull() {
        graph.getEdges().forEach(Edge::setAllMouseEventsToNull);
    }

    public void setAllVertexEventsToDefault() {
        graph.getVertices().forEach(Vertex::setAllMouseEventsToDefault);
    }

    public void setAllEdgesEventsToDefault() {
        graph.getEdges().forEach(Edge::setAllMouseEventsToDefault);
    }
}
