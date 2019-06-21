package controllers;

import algorithms.BFS;
import algorithms.DFS;
import algorithms.SearchingAlgorithm;
import graph.Graph;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InfoBoxController {
    @FXML
    private VBox infoBox;
    @FXML
    private Text maxIndegree;
    @FXML
    private Text minIndegree;
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

    @FXML
    private void depthFirstSearch() {
        mainController.getMainSplitPane()
                .setDisable(true);
        SearchingAlgorithm dfs = new DFS(mainController.getGraph());
        dfs.injectMainController(mainController);
        dfs.apply(mainController.getGraph()
                .getVertices()
                .get(0));
        dfs.drawAnimation();
    }

    @FXML
    private void breadthFirstSearch() {
        mainController.getMainSplitPane()
                .setDisable(true);
        SearchingAlgorithm bfs = new BFS(mainController.getGraph());
        bfs.injectMainController(mainController);
        bfs.apply(mainController.getGraph()
                .getVertices()
                .get(0));
        bfs.drawAnimation();
    }

    public void bindInfoToGraph() {
        Graph graph = mainController.getGraph();
        graphOrder.textProperty()
                .bind(Bindings.concat("Order : ",
                        graph.orderProperty()
                                .asString()));
        graphSize.textProperty()
                .bind(Bindings.concat("Size : ",
                        graph.sizeProperty()
                                .asString()));
        maxDegree.textProperty()
                .bind(Bindings.createStringBinding(
                        () -> "Max " + (orientedGraph.isSelected() ? "outdegre : " : "degree : ") + graph.getMaxDegree(),
                        orientedGraph.selectedProperty(),
                        graph.maxDegreeProperty()
                ));
        minDegree.textProperty()
                .bind(Bindings.createStringBinding(
                        () -> "Min " + (orientedGraph.isSelected() ? "outdegre : " : "degree : ") + graph.getMinDegree(),
                        orientedGraph.selectedProperty(),
                        graph.minDegreeProperty()
                ));
        maxIndegree.textProperty()
                .bind(Bindings.createStringBinding(
                        () -> (orientedGraph.isSelected() ? "Max indegree : " + graph.getMaxIndegree() : ""),
                        orientedGraph.selectedProperty(),
                        graph.maxIndegreeProperty()
                ));
        minIndegree.textProperty()
                .bind(Bindings.createStringBinding(
                        () -> (orientedGraph.isSelected() ? "Min indegree : " + graph.getMinIndegree() : ""),
                        orientedGraph.selectedProperty(),
                        graph.minIndegreeProperty()
                ));
    }


    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public CheckBox getOrientedGraphCheckBox() {
        return orientedGraph;
    }
}