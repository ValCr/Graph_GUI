package algorithms;

import controllers.GraphPaneController;
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
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Objects;
import java.util.stream.Collectors;

public abstract class SearchingAlgorithm extends Algorithms {
    private static Color DEFAULT_COLOR_WHEN_VISITED = Color.web("#00CC14");
    protected boolean[] discovered;
    protected Vertex startVertex;
    protected SimpleListProperty<Vertex> orderOfDiscovery;

    public SearchingAlgorithm(Graph graph) {
        super(graph);
        this.discovered = new boolean[graph.getVertices()
                .size()];
        this.orderOfDiscovery = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();
        GraphPaneController controller = mainController.getGraphPaneController();
        controller.getAnimationSpeed().setVisible(true);
        controller.getAnimationSpeed().toFront();
        mainController.getGraph()
                .getVertices()
                .forEach(v -> {
                    controller.getInfoAlgo().setText("Select a starting vertex");
                    v.setOnMouseEntered(v::handleMouseEntered);
                    v.setOnMouseExited(v::handleMouseExited);
                    v.setOnMousePressed(mouseEvent -> {
                        mainController.setAllVertexEventsToNull();
                        setStartVertex(v);
                        apply();
                        drawAnimation();
                    });
                });
    }

    @Override
    public abstract void apply();

    @Override
    public void drawAnimation() {
        final IntegerProperty i = new SimpleIntegerProperty(0);
        showInfo(i);
        double animationSpeed = mainController.getGraphPaneController().getAnimationSpeed()
                .getValue() / orderOfDiscovery.size();

        // color the graph in the order of discovery
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(animationSpeed),
                        event -> {
                            orderOfDiscovery.get(i.get())
                                    .setFill(DEFAULT_COLOR_WHEN_VISITED);
                            Vertex u;
                            Vertex v = orderOfDiscovery.get(i.get());
                            for (int j = this instanceof BFS ? 0 : i.get() - 1; j < i.get() && j >= 0; j = this instanceof BFS ? ++j : --j) {
                                u = orderOfDiscovery.get(j);
                                if (u.isAdjacentTo(v)) {
                                    Edge e = u.getEdgeFromAdjacentVertex(v);
                                    if (!mainController.getGraphPaneController().graphIsOriented() || e.getEnd() == v) {
                                        e.setStroke(DEFAULT_COLOR_WHEN_VISITED);
                                        break;
                                    }
                                }
                            }
                            i.set(i.get() + 1);
                        }
                )
        );
        timeline.setCycleCount(orderOfDiscovery.size());
        timeline.play();

        // set graph's behavior and color to default
        timeline.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                resetDefaultGraphBehavior();
                mainController.getGraphPaneController().getAnimationSpeed().setVisible(false);
            });
            pause.play();
        });
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

    ///////////////////////////////////////////// Setters /////////////////////////////////////////////
    public void setStartVertex(Vertex startVertex) {
        this.startVertex = startVertex;
    }

    ///////////////////////////////////////////// Getters /////////////////////////////////////////////
    public ObservableList<Vertex> getOrderOfDiscovery() {
        return orderOfDiscovery.get();
    }
}
