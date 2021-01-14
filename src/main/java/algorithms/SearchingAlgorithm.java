package algorithms;

import graph.Constants;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Defines the methods and variables used by searching algorithms.
 *
 * @see BFS
 * @see DFS
 */
public abstract class SearchingAlgorithm extends Algorithms {
    protected final SimpleListProperty<Vertex> orderOfDiscovery;
    protected Vertex startVertex;

    protected SearchingAlgorithm(Graph graph) {
        super(graph);
        this.orderOfDiscovery = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();
        mainController.getGraphPaneController().getInfoAlgo().setText("Select a starting vertex");
        mainController.getGraph().getVertices().forEach(v -> {
            v.setOnMouseEntered(v::handleMouseEntered);
            v.setOnMouseExited(v::handleMouseExited);
            v.setOnMousePressed(mouseEvent -> {
                mainController.setAllVertexEventsToNull();
                v.setFill(Constants.VERTEX_COLOR_WHEN_SELECTED);
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
        double animationSpeed =
                mainController.getGraphPaneController().getAnimationSpeed().getValue() / orderOfDiscovery.size();

        // color the graph in the order of discovery
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(animationSpeed), event -> {
            if (orderOfDiscovery.get(i.get()) != startVertex) {
                orderOfDiscovery.get(i.get()).setFill(Constants.VERTEX_COLOR_WHEN_VISITED);
            }
            Vertex u;
            Vertex v = orderOfDiscovery.get(i.get());
            for (int j = this instanceof BFS ? 0 : i.get() - 1;
                 j < i.get() && j >= 0; j = this instanceof BFS ? ++j : --j) {
                u = orderOfDiscovery.get(j);
                if (u.isAdjacentTo(v)) {
                    Edge e = u.getEdgeFromAdjacentVertex(v);
                    if (!mainController.getGraph().isOriented() || e.getEnd() == v) {
                        e.setStroke(Constants.VERTEX_COLOR_WHEN_VISITED);
                        break;
                    }
                }
            }
            i.set(i.get() + 1);
        }));
        timeline.setCycleCount(orderOfDiscovery.size());
        timeline.play();

        timeline.setOnFinished(event -> waitForUserInputToEndAlgorithm());
    }

    private void showInfo(IntegerProperty i) {
        mainController.getGraphPaneController()
                .getInfoAlgo()
                .textProperty()
                .bind(Bindings.createStringBinding(() -> this.getClass().getSimpleName() + " : " +
                        orderOfDiscovery.subList(0, Math.min(orderOfDiscovery.size(), i.get() + 1))
                                .stream()
                                .map(Objects::toString)
                                .collect(Collectors.joining("->")), i));
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
