package algorithms;

import controllers.GraphPaneController;
import controllers.MainController;
import graph.Constants;
import graph.Graph;
import graph.Vertex;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Algorithms {
    protected final Graph graph;
    protected final List<Node> shapes;
    protected MainController mainController;

    protected Algorithms(Graph graph) {
        this.graph = graph;
        shapes = new ArrayList<>();
    }

    public abstract void apply();

    public abstract void drawAnimation();

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUpEvents() {
        GraphPaneController controller = mainController.getGraphPaneController();
        controller.getHelpInfo().setVisible(false);
        controller.getGraphPane().setOnMousePressed(null);
        controller.getAnimationSpeed().setVisible(true);
        controller.getAnimationSpeed().toFront();
        mainController.getInfoBoxController().getInfoBox().setDisable(true);
        mainController.setAllVertexEventsToNull();
        mainController.setAllEdgesEventsToNull();
    }

    protected void resetDefaultGraphBehavior() {
        resetGraphColor();
        graph.setContainsCircuit(false);
        GraphPaneController controller = mainController.getGraphPaneController();
        controller.getInfoAlgo()
                .textProperty()
                .unbind();
        controller.getGraphPane()
                .setOnMousePressed(controller::addVertex);
        controller.getHelpInfo().setVisible(mainController.getMenuBarController().getHelp().isSelected());
        controller.getAnimationSpeed().setVisible(false);
        controller.getGraphPane()
                .setOnKeyPressed(null);
        mainController.setAllVertexEventsToDefault();
        mainController.setAllEdgesEventsToDefault();
        mainController.getInfoBoxController()
                .getInfoBox()
                .setDisable(false);
        mainController.getGraphPaneController().getGraphPane().getChildren().removeAll(shapes);
    }

    private void resetGraphColor() {
        graph.getVertices()
                .forEach(v -> v.setFill(Constants.VERTEX_DEFAULT_COLOR));
        graph.getEdges()
                .forEach(e -> e.setStroke(Constants.EDGE_DEFAULT_COLOR));
    }

    protected String verticesToString(List<Vertex> vertices) {
        return vertices.stream().map(Objects::toString).collect(Collectors.joining("->"));
    }

    protected void waitForUserInputToEndAlgorithm() {
        // set graph's behavior and color to default
        mainController.getGraphPaneController()
                .getGraphPane()
                .setOnMousePressed(e -> resetDefaultGraphBehavior());
        mainController.getGraphPaneController()
                .getGraphPane()
                .setOnKeyPressed(e -> resetDefaultGraphBehavior());
    }
}
