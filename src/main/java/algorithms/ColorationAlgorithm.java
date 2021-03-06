package algorithms;

import graph.Constants;
import graph.Graph;
import graph.Vertex;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Defines the variables used by coloration algorithms.
 *
 * @see Bipartition
 */
public abstract class ColorationAlgorithm extends Algorithms {
    protected Map<Vertex, Color> colors;
    protected boolean isBipartite;

    protected ColorationAlgorithm(Graph graph) {
        super(graph);
        colors = new HashMap<>(graph.getOrder());
        graph.getVertices().forEach(v -> colors.put(v, Constants.VERTEX_DEFAULT_COLOR));
        isBipartite = false;
    }

    @Override
    public abstract void apply();

    @Override
    public void setUpEvents() {
        super.setUpEvents();
        apply();
        drawAnimation();
    }

    @Override
    public void drawAnimation() {
        mainController.getGraphPaneController()
                .getInfoAlgo()
                .setText(String.format("Graph is%s bipartite", isBipartite ? "" : " not"));
        if (!isBipartite) {
            waitForUserInputToEndAlgorithm();
            return;
        }
        double animationSpeed =
                mainController.getGraphPaneController().getAnimationSpeed().getValue() / (colors.size() + 1);
        Iterator<Vertex> it = graph.getVertices().iterator();
        // color the graph with with the spanning tree found
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(animationSpeed), event -> {
            if (it.hasNext()) {
                Vertex v = it.next();
                v.setFill(colors.get(v));
            }
        }));
        timeline.setCycleCount(colors.size());
        timeline.play();

        timeline.setOnFinished(event -> waitForUserInputToEndAlgorithm());
    }
}

