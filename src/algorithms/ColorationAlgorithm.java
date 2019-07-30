package algorithms;

import graph.Graph;
import graph.Vertex;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class ColorationAlgorithm extends Algorithms {
    protected Map<Vertex, Color> colors;
    protected boolean isBipartite;

    protected ColorationAlgorithm(Graph graph) {
        super(graph);
        colors = new HashMap<>(graph.getOrder());
        graph.getVertices()
                .forEach(v -> colors.put(v, Vertex.DEFAULT_COLOR));
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
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> resetDefaultGraphBehavior());
            pause.play();
            return;
        }
        double animationSpeed = mainController.getGraphPaneController()
                .getAnimationSpeed()
                .getValue() / (colors.size() + 1);
        Iterator<Vertex> it = graph.getVertices()
                .iterator();
        // color the graph with with the spanning tree found
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(animationSpeed), event -> {
            if (it.hasNext()) {
                Vertex v = it.next();
                v.setFill(colors.get(v));
            }
        }));
        timeline.setCycleCount(colors.size());
        timeline.play();

        timeline.setOnFinished(event -> {
            // set graph's behavior and color to default
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(v -> resetDefaultGraphBehavior());
            pause.play();
        });
    }
}

