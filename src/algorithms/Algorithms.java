package algorithms;

import controllers.GraphPaneController;
import controllers.MainController;
import graph.Graph;

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
}
