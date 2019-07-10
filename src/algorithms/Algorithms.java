package algorithms;

import controllers.GraphPaneController;
import controllers.MainController;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Algorithms {
    protected static final Color DEFAULT_COLOR_WHEN_VISITED = Color.web("#00CC14");
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
        mainController.setAllVertexEventsToDefault();
        mainController.setAllEdgesEventsToDefault();
        mainController.getInfoBoxController()
                .getInfoBox()
                .setDisable(false);
        mainController.getGraphPaneController().getGraphPane().getChildren().removeAll(shapes);
    }

    private void resetGraphColor() {
        graph.getVertices()
                .forEach(v -> v.setFill(Vertex.DEFAULT_COLOR));
        graph.getEdges()
                .forEach(e -> e.setStroke(Edge.DEFAULT_COLOR));
    }


    protected String verticesToString(List<Vertex> vertices) {
        return vertices.stream().map(Objects::toString).collect(Collectors.joining("->"));
    }
}
