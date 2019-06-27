package algorithms;

import graph.Graph;
import graph.Vertex;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public abstract class ShortestPathAlgorithm extends Algorithms {
    protected Vertex startVertex;
    protected Vertex endVertex;

    public ShortestPathAlgorithm(Graph graph) {
        super(graph);
    }

    @Override
    public abstract void apply();

    @Override
    public void drawAnimation() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> resetDefaultGraphBehavior());
        pause.play();
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
                        startVertex = v;
                        mainController.getGraphPaneController().getInfoAlgo().setText("Select an ending vertex");
                        mainController.getGraph().getVertices().forEach(u -> {
                            u.setOnMouseEntered(u::handleMouseEntered);
                            u.setOnMouseExited(u::handleMouseExited);
                            u.setOnMousePressed(mouseEvent2 -> {
                                mainController.setAllVertexEventsToNull();
                                endVertex = u;
                                apply();
                                drawAnimation();
                            });
                        });
                    });
                });
    }
}
