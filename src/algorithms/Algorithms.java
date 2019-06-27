package algorithms;

import controllers.GraphPaneController;
import controllers.MainController;
import graph.Edge;
import graph.Graph;
import graph.Vertex;

public abstract class Algorithms {
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
        mainController.getInfosBoxController().getInfoBox().setDisable(true);
        mainController.setAllVertexEventsToNull();
        mainController.setAllEdgesEventsToNull();
    }

    protected void resetDefaultGraphBehavior() {
        resetGraphColor();
        GraphPaneController controller = mainController.getGraphPaneController();
        controller.getInfoAlgo()
                .textProperty()
                .unbind();
        controller.getGraphPane()
                .setOnMousePressed(controller::addVertex);
        controller.getHelpInfo().setVisible(true);
        mainController.setAllVertexEventsToDefault();
        mainController.setAllEdgesEventsToDefault();
        mainController.getInfosBoxController()
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
