package controllers;

import graph.Vertex;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class RenameVertexController extends BasePopup {

    private static MainController mainController;
    private static Vertex vertexToRename;

    public static void setVertexToRename(Vertex vertexToRename) {
        RenameVertexController.vertexToRename = vertexToRename;
    }

    public static void setMainController(MainController mainController) {
        RenameVertexController.mainController = mainController;
    }

    @FXML
    @Override
    protected void submit(Event event) {
        if (text.getText().matches("\\d+")) {
            warnings.setText("Name cannot be a number.");
        } else if (mainController.getGraph().getVertices().stream().anyMatch(v -> v.getId().matches(text.getText()))) {
            warnings.setText("Another vertex already has this name.");
        } else {
            vertexToRename.setId(text.getText());
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    @FXML
    protected void initialize() {
        text.setText(vertexToRename.getId());
        super.initialize();
    }
}
