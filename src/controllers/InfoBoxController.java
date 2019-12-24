package controllers;

import algorithms.*;
import factory.EdgeFactory;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class InfoBoxController {
    @FXML
    private Button zombieButton;
    @FXML
    private CheckBox flowNetwork;
    @FXML
    private Button primButton;
    @FXML
    private CheckBox costAreVisible;
    @FXML
    private Button bellmanButton;
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
    private void changeGraphType() {
        List<Edge> edges = new ArrayList<>();
        EdgeFactory factory = new EdgeFactory(mainController.getGraph());
        mainController.getGraph().getEdges().forEach(e -> {
            Edge newEdge = factory.makeEdge(e.getStart(), e.getEnd());
            newEdge.setCost(e.getCost());
            edges.add(newEdge);
        });
        mainController.getGraphPaneController().removeAllEdges();
        edges.forEach(e -> mainController.getGraphPaneController().addEdge(e));
    }

    @FXML
    private void changeAllCost() {
        if (!costAreVisible.isSelected()) {
            // non oriented graph =  oriented graph + arcs in double direction + cost 1
            mainController.getGraph().getEdges().forEach(e -> e.setCost(1));
        }
    }

    @FXML
    private void checkBipartition() {
        applyAlgorithm(new Bipartition(mainController.getGraph()));
    }

    @FXML
    private void dijsktra() {
        applyAlgorithm(new Dijsktra(mainController.getGraph()));
    }

    @FXML
    private void bellman() {
        applyAlgorithm(new Bellman(mainController.getGraph()));
    }

    @FXML
    private void bellmanFord() {
        applyAlgorithm(new BellmanFord(mainController.getGraph()));
    }

    @FXML
    private void depthFirstSearch() {
        applyAlgorithm(new DFS(mainController.getGraph()));
    }

    @FXML
    private void breadthFirstSearch() {
        applyAlgorithm(new BFS(mainController.getGraph()));
    }

    @FXML
    private void prim() {
        applyAlgorithm(new Prim(mainController.getGraph()));
    }

    @FXML
    private void kruskall() {
        applyAlgorithm(new Kruskal(mainController.getGraph()));
    }

    @FXML
    private void zombieEpidemic() {
        applyAlgorithm(new ZombieEpidemic(mainController.getGraph()));
    }

    private void applyAlgorithm(Algorithms algo) {
        if (!mainController.getGraph().getVertices().isEmpty()) {
            algo.injectMainController(mainController);
            algo.setUpEvents();
        }
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
        bellmanButton.disableProperty().bind(orientedGraph.selectedProperty().not());
        primButton.disableProperty().bind(orientedGraph.selectedProperty());
        graph.orientedProperty().bind(orientedGraph.selectedProperty());
        flowNetwork.disableProperty()
                .bind(orientedGraph.selectedProperty()
                        .not());
        flowNetwork.selectedProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        costAreVisible.setSelected(true);
                    }
                });
        zombieButton.disableProperty()
                    .bind(flowNetwork.selectedProperty());
        costAreVisible.disableProperty()
                .bind(flowNetwork.selectedProperty());
        graph.flowNetworkProperty()
                .bind(flowNetwork.selectedProperty());

    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void complementGraph() {
        List<Vertex> vertices = mainController.getGraph().getVertices();
        List<Edge> oldEdges = new ArrayList<>(mainController.getGraph().getEdges());
        EdgeFactory factory = new EdgeFactory(mainController.getGraph());
        vertices.forEach(v -> vertices.forEach(u -> {
            if (v != u && !v.pointsTo(u)) {
                mainController.getGraphPaneController()
                              .addEdge(factory.makeEdge(v, u));
            }
        }));
        oldEdges.forEach(e -> mainController.getGraphPaneController()
                                            .removeEdge(e));
    }

    public CheckBox getCostAreVisible() {
        return costAreVisible;
    }

    public VBox getInfoBox() {
        return infoBox;
    }

    public CheckBox getOrientedGraph() {
        return orientedGraph;
    }

    public CheckBox getFlowNetwork() {
        return flowNetwork;
    }
}
