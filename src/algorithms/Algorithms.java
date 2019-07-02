package algorithms;

import controllers.GraphPaneController;
import controllers.MainController;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.paint.Color;

public abstract class Algorithms {
    protected static Color DEFAULT_COLOR_WHEN_VISITED = Color.web("#00CC14");
    protected Graph graph;
    protected MainController mainController;

    public Algorithms(Graph graph) {
        this.graph = graph;
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
        controller.getHelpInfo().setVisible(true);
        controller.getAnimationSpeed().setVisible(false);
        mainController.setAllVertexEventsToDefault();
        mainController.setAllEdgesEventsToDefault();
        mainController.getInfoBoxController()
                .getInfoBox()
                .setDisable(false);
    }

    private void resetGraphColor() {
        graph.getVertices()
                .forEach(v -> v.setFill(Vertex.DEFAULT_COLOR));
        graph.getEdges()
                .forEach(e -> e.setStroke(Edge.DEFAULT_COLOR));
    }
}
