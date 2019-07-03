package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ShortestPathAlgorithm extends Algorithms {
    protected Vertex startVertex;
    protected Vertex endVertex;
    protected Map<Vertex, Vertex> predecessors;
    protected Map<Vertex, Double> distances;

    public ShortestPathAlgorithm(Graph graph) {
        super(graph);
    }

    @Override
    public abstract void apply();

    public abstract boolean conditionsAreValid();

    public abstract void updateInfoAlgo();

    @Override
    public void drawAnimation() {
        // set graph's behavior and color to default if algorithm can't be applied
        if (!conditionsAreValid()) {
            updateInfoAlgo();
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> resetDefaultGraphBehavior());
            pause.play();
            return;
        }

        double animationSpeed = mainController.getGraphPaneController().getAnimationSpeed().getValue() / (predecessors
                .size() + 1);
        Iterator<Map.Entry<Vertex, Vertex>> it = predecessors.entrySet().stream().filter(v -> v.getValue() != null)
                .iterator();

        // color the graph with Bellman's algorithm
        Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(animationSpeed), event -> {
            if (it.hasNext()) {
                Map.Entry<Vertex, Vertex> entry = it.next();
                Vertex u = entry.getValue();
                Vertex v = entry.getKey();
                if (u != startVertex && u != endVertex) {
                    u.setFill(DEFAULT_COLOR_WHEN_VISITED);
                }
                if (v != startVertex && v != endVertex) {
                    v.setFill(DEFAULT_COLOR_WHEN_VISITED);
                }
                u.getEdgeFromAdjacentVertex(v).setStroke(DEFAULT_COLOR_WHEN_VISITED);

                // show distance to vertex v
                showDistance(v, distances.get(v));
            }
        }));

        timeline1.setCycleCount(predecessors.size());
        timeline1.play();

        // Show the shortest path between startvertex and endvertex on the graph
        List<Vertex> shortestPath = getPathFromPredecessors();
        Iterator<Vertex> iter = shortestPath.iterator();

        Timeline timeline2 = new Timeline();
        timeline2.getKeyFrames().add(new KeyFrame(Duration.seconds(animationSpeed), event -> {
            Vertex u = iter.next();
            Vertex v = predecessors.get(u);
            if (u != startVertex && u != endVertex) {
                u.setFill(Vertex.DEFAULT_COLOR);
            }
            if (v != null) {
                if (v != startVertex && v != endVertex) {
                    v.setFill(Vertex.DEFAULT_COLOR);
                }
                u.getEdgeFromAdjacentVertex(v).setStroke(Vertex.DEFAULT_COLOR);
            }
        }));

        timeline2.setCycleCount(shortestPath.size());

        timeline1.setOnFinished(event -> timeline2.play());

        timeline2.setOnFinished(event -> {
            // show the shortest path on the info label
            Collections.reverse(shortestPath);
            if (shortestPath.size() == 1 && startVertex != endVertex) {
                mainController.getGraphPaneController().getInfoAlgo()
                        .setText("No path found between " + startVertex.getId() + " and " + endVertex.getId());
            } else {
                mainController.getGraphPaneController().getInfoAlgo().setText(
                        "Shortest path between " + startVertex.getId() + " and " + endVertex
                                .getId() + " : \n" + shortestPath.stream().map(Objects::toString)
                                .collect(Collectors.joining("->")) + ", length : " + distances.get(endVertex));
            }
            // set graph's behavior and color to default
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> resetDefaultGraphBehavior());
            pause.play();
        });
    }

    private List<Vertex> getPathFromPredecessors() {
        List<Vertex> shortestPath = new ArrayList<>();
        Vertex v = endVertex;
        while (v != null) {
            shortestPath.add(v);
            v = predecessors.get(v);
        }
        return shortestPath;
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();
        mainController.getGraphPaneController().getInfoAlgo().setText("Select a starting vertex");
        mainController.getGraph()
                .getVertices()
                .forEach(v -> {
                    v.setOnMouseEntered(v::handleMouseEntered);
                    v.setOnMouseExited(v::handleMouseExited);
                    v.setOnMousePressed(mouseEvent -> {
                        mainController.setAllVertexEventsToNull();
                        v.setFill(Vertex.DEFAULT_COLOR_WHEN_SELECTED);
                        startVertex = v;
                        mainController.getGraphPaneController().getInfoAlgo().setText("Select an ending vertex");
                        mainController.getGraph().getVertices().forEach(u -> {
                            if (u != v) {
                                u.setOnMouseEntered(u::handleMouseEntered);
                                u.setOnMouseExited(u::handleMouseExited);
                            }
                            u.setOnMousePressed(mouseEvent2 -> {
                                mainController.setAllVertexEventsToNull();
                                u.setFill(Vertex.DEFAULT_COLOR_WHEN_SELECTED);
                                endVertex = u;
                                apply();
                                drawAnimation();
                            });
                        });
                    });
                });
    }

    protected void updateDistances(Vertex v, Edge e) {
        Vertex u = e.getOtherEnd(v);
        if (distances.get(v) + e.getCost() < distances.get(u)) {
            distances.put(u, distances.get(v) + e.getCost());
            predecessors.put(u, v);
        }
    }


    private void showDistance(Vertex v, Double distance) {
        Text text = new Text(distance.toString());
        text.setFill(Color.web("#0E0FA8"));
        text.setFont(Font.font("System", FontWeight.BOLD, 16));
        text.xProperty().bind(v.centerXProperty().subtract(text.layoutBoundsProperty().getValue().getWidth() / 2));
        text.yProperty().bind(v.centerYProperty().subtract(v.getRadius() + 5));
        shapes.add(text);
        mainController.getGraphPaneController().getGraphPane().getChildren().add(text);
    }
}
