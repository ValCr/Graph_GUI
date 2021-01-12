package algorithms;

import graph.Constants;
import graph.Graph;
import graph.Vertex;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZombieEpidemic extends Algorithms {
    private static final Color HEALTHY = Constants.VERTEX_DEFAULT_COLOR;
    private static final Color CONTAMINATED = Constants.VERTEX_COLOR_WHEN_VISITED;
    private static final Color BLOCKED = Constants.VERTEX_COLOR_WHEN_SELECTED;
    private final Map<Vertex, Color> colors;

    public ZombieEpidemic(Graph graph) {
        super(graph);
        colors = new HashMap<>(graph.getOrder());
        graph.getVertices().forEach(v -> colors.put(v, HEALTHY));
    }

    @Override
    public void apply() {
        //nothing to do, we run the algorithm at the same time that we draw it because we need user input at every turn
    }

    @Override
    public void drawAnimation() {
        if (zombiesEpidemicCanPropagate()) {
            waitUserToSelectVertex();
        } else {
            gameOver();
        }
    }

    private List<Vertex> getHealthyNeighbors(Vertex v) {
        return v.getEdges()
                .stream()
                .map(e -> e.getOtherEnd(v))
                .filter(ve -> colors.get(ve) == HEALTHY)
                .collect(Collectors.toList());
    }

    private void waitUserToSelectVertex() {
        mainController.getGraphPaneController()
                      .getInfoAlgo()
                      .setText("Select a vertex to be protected from the epidemic");
        mainController.getGraph()
                      .getVertices()
                      .forEach(v -> v.setOnMousePressed(mouseEvent -> {
                          mainController.setAllVertexEventsToNull();
                          mouseEvent.consume();
                          playTurn(v);
                      }));
    }

    private void playTurn(Vertex selectedVertex) {
        if (colors.get(selectedVertex) != CONTAMINATED) {
            colors.put(selectedVertex, BLOCKED);
            selectedVertex.setFill(BLOCKED);
        }
        colors.keySet()
              .removeAll(verticesWhichCantPropagateEpidemic());
        verticesWhichCanPropagateEpidemic().forEach(v -> getHealthyNeighbors(v).forEach(ve -> {
            colors.put(ve, CONTAMINATED);
            ve.setFill(CONTAMINATED);
        }));
        if (zombiesEpidemicCanPropagate()) {
            waitUserToSelectVertex();
        } else {

            gameOver();
        }
    }

    private void gameOver() {
        long verticesContaminated = graph.getVertices()
                                         .stream()
                                         .filter(v -> v.fillProperty()
                                                       .get() == CONTAMINATED)
                                         .count();

        int numberOfVertices = graph.getVertices()
                                    .size();
        mainController.getGraphPaneController()
                      .getInfoAlgo()
                      .setText(String.format("Game over\n\n%d / %d  (%.0f %%) cities were contaminated",
                              verticesContaminated, numberOfVertices,
                              (float) verticesContaminated / numberOfVertices * 100));
        waitForUserInputToEndAlgorithm();
    }

    private List<Vertex> verticesWhichCantPropagateEpidemic() {
        return getContaminatedVertices().stream()
                                        .filter(v -> !hasHealthyNeighbor(v))
                                        .collect(Collectors.toList());
    }

    private List<Vertex> verticesWhichCanPropagateEpidemic() {
        return getContaminatedVertices().stream()
                                        .filter(this::hasHealthyNeighbor)
                                        .collect(Collectors.toList());
    }

    private boolean zombiesEpidemicCanPropagate() {
        return getContaminatedVertices().stream()
                                        .anyMatch(this::hasHealthyNeighbor);
    }

    private List<Vertex> getContaminatedVertices() {
        return colors.keySet()
                     .stream()
                     .filter(v -> colors.get(v) == CONTAMINATED)
                     .collect(Collectors.toList());
    }

    private boolean hasHealthyNeighbor(Vertex v) {
        return v.getEdges()
                .stream()
                .anyMatch(e -> colors.get(e.getOtherEnd(v)) == HEALTHY);
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();
        mainController.getGraphPaneController()
                      .getAnimationSpeed()
                      .setVisible(false);
        mainController.getGraphPaneController()
                      .getInfoAlgo()
                      .setText("Select a vertex to start the epidemic");
        mainController.getGraph()
                      .getVertices()
                      .forEach(v -> {
                          v.setOnMouseEntered(v::handleMouseEntered);
                          v.setOnMouseExited(v::handleMouseExited);
                          v.setOnMousePressed(mouseEvent -> {
                              mainController.setAllVertexEventsToNull();
                              v.setFill(CONTAMINATED);
                              colors.put(v, CONTAMINATED);
                              drawAnimation();
                          });
                      });
    }
}
