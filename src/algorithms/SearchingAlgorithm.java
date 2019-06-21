package algorithms;

import controllers.MainController;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Objects;
import java.util.stream.Collectors;

public abstract class SearchingAlgorithm {
    private static Color DEFAULT_COLOR_WHEN_VISITED = Color.web("#00CC14");
    protected boolean[] discovered;
    protected Graph graph;
    protected SimpleListProperty<Vertex> orderOfDiscovery;
    private MainController mainController;

    public SearchingAlgorithm(Graph graph) {
        this.graph = graph;
        this.discovered = new boolean[graph.getVertices()
                .size()];
        this.orderOfDiscovery = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public abstract void apply(Vertex vertex);

    public void drawAnimation() {
        final IntegerProperty i = new SimpleIntegerProperty(0);
        showInfo(i);
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0.5),
                        event -> {
                            // color the graph in the order of discovery
                            orderOfDiscovery.get(i.get())
                                    .setFill(DEFAULT_COLOR_WHEN_VISITED);
                            Vertex u;
                            Vertex v = orderOfDiscovery.get(i.get());
                            for (int j = this instanceof BFS ? 0 : i.get() - 1; j < i.get() && j >= 0; j = this instanceof BFS ? ++j : --j) {
                                u = orderOfDiscovery.get(j);
                                if (u.isAdjacentTo(v)) {
                                    u.getEdgeFromAdjacentVertex(v)
                                            .setStroke(DEFAULT_COLOR_WHEN_VISITED);
                                    break;
                                }
                            }

                            i.set(i.get() + 1);

                            // set the original graph's colors
                            if (i.get() == orderOfDiscovery.size()) {
                                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                                pause.setOnFinished(e -> {
                                    resetGraphColor();
                                    mainController.getGraphPaneController()
                                            .getInfoAlgo()
                                            .textProperty()
                                            .unbind();
                                    mainController.getGraphPaneController()
                                            .getGraphPane()
                                            .setDisable(false);
                                    mainController.getInfosBoxController()
                                            .getInfoBox()
                                            .setDisable(false);
                                });
                                pause.play();
                            }
                        }
                )
        );
        timeline.setCycleCount(orderOfDiscovery.size());
        timeline.play();
    }

    private void showInfo(IntegerProperty i) {
        mainController.getGraphPaneController()
                .getInfoAlgo()
                .textProperty()
                .bind(Bindings.createStringBinding(
                        () -> this.getClass()
                                .getSimpleName() + " : " + orderOfDiscovery.subList(0,
                                Math.min(orderOfDiscovery.size(),
                                        i.get() + 1))
                                .stream()
                                .map(Objects::toString)
                                .collect(Collectors.joining("->")),
                        i
                ));
    }


    private void resetGraphColor() {
        graph.getVertices()
                .forEach(v -> v.setFill(Vertex.DEFAULT_COLOR));
        graph.getEdges()
                .forEach(e -> e.setStroke(Edge.DEFAULT_COLOR));
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }
}