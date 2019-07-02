package controllers;

import graph.Vertex;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class RenameVertexController {

    private static MainController mainController;
    private static Vertex vertexToRename;
    @FXML
    private Label warnings;
    @FXML
    private TextField newName;

    public static void setVertexToRename(Vertex vertexToRename) {
        RenameVertexController.vertexToRename = vertexToRename;
    }

    public static void setMainController(MainController mainController) {
        RenameVertexController.mainController = mainController;
    }

    @FXML
    private void submit(Event event) {
        if (newName.getText().matches("\\d+")) {
            warnings.setText("Name cannot be a number.");
        } else if (mainController.getGraph().getVertices().stream()
                .anyMatch(v -> v.getId().matches(newName.getText()))) {
            warnings.setText("Another vertex already has this name.");
        } else {
        vertexToRename.setId(newName.getText());
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> newName.requestFocus());
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            submit(keyEvent);
        }
    }
}
