package algorithms;

import graph.Edge;
import graph.Graph;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Iterator;
import java.util.List;

public abstract class MinSpanningTreeAlgorithm extends Algorithms {
    protected List<Edge> tree;

    protected MinSpanningTreeAlgorithm(Graph graph) {
        super(graph);
    }

    @Override
    public abstract void apply();

    @Override
    public void setUpEvents() {
        super.setUpEvents();
        mainController.getGraphPaneController().getInfoAlgo().setText("Computation of a minimum spanning tree");
        apply();
        drawAnimation();
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
                e.getStart().setFill(DEFAULT_COLOR_WHEN_VISITED);
                e.getEnd().setFill(DEFAULT_COLOR_WHEN_VISITED);
                e.getStart().getEdgeFromAdjacentVertex(e.getEnd()).setStroke(DEFAULT_COLOR_WHEN_VISITED);
            }
        }));
        timeline.setCycleCount(tree.size());
        timeline.play();

        timeline.setOnFinished(event -> {
            // set graph's behavior and color to default
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                resetDefaultGraphBehavior();
                mainController.getGraphPaneController().getInfoAlgo()
                        .setText("Minimum spanning tree weight : " + getTreeWeight());
            });
            pause.play();
        });
    }

    private double getTreeWeight() {
        return tree.stream().mapToDouble(Edge::getCost).sum();
    }

}
