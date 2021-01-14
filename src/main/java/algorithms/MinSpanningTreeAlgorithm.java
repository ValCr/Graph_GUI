package algorithms;

import graph.Constants;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Iterator;
import java.util.List;

/**
 * Defines the methods and variables used by the minimum spanning tree algorithms.
 *
 * @see Kruskal
 * @see Prim
 */
public abstract class MinSpanningTreeAlgorithm extends Algorithms {
    protected List<Edge> tree;
    protected List<Vertex> vertices;

    protected MinSpanningTreeAlgorithm(Graph graph) {
        super(graph);
    }

    @Override
    public abstract void apply();

    @Override
    public void setUpEvents() {
        super.setUpEvents();
        if (graph.isConnected()) {
            vertices = graph.getVertices();
            mainController.getGraphPaneController().getInfoAlgo().setText("Computation of a minimum spanning tree");
            apply();
            drawAnimation();
        } else {
            mainController.getGraphPaneController().getInfoAlgo().setText("Select a connected component of the graph");
            mainController.getGraph().getVertices().forEach(v -> {
                v.setOnMouseEntered(v::handleMouseEntered);
                v.setOnMouseExited(v::handleMouseExited);
                v.setOnMousePressed(mouseEvent -> {
                    mainController.setAllVertexEventsToNull();
                    DFS dfs = new DFS(graph);
                    dfs.setStartVertex(v);
                    dfs.apply();
                    vertices = dfs.getOrderOfDiscovery();
                    apply();
                    drawAnimation();
                });
            });
        }
    }

    @Override
    public void drawAnimation() {
        double animationSpeed =
                mainController.getGraphPaneController().getAnimationSpeed().getValue() / (tree.size() + 1);
        Iterator<Edge> it = tree.iterator();
        // color the graph with with the spanning tree found
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(animationSpeed), event -> {
            if (it.hasNext()) {
                Edge e = it.next();
                e.getStart().setFill(Constants.VERTEX_COLOR_WHEN_VISITED);
                e.getEnd().setFill(Constants.VERTEX_COLOR_WHEN_VISITED);
                e.getStart().getEdgeFromAdjacentVertex(e.getEnd()).setStroke(Constants.VERTEX_COLOR_WHEN_VISITED);
            }
        }));
        timeline.setCycleCount(tree.size());
        timeline.play();

        timeline.setOnFinished(event -> {
            waitForUserInputToEndAlgorithm();
            mainController.getGraphPaneController()
                    .getInfoAlgo()
                    .setText("Minimum spanning tree weight : " + getTreeWeight());
        });
    }

    private double getTreeWeight() {
        return tree.stream().mapToDouble(Edge::getCost).sum();
    }
}
