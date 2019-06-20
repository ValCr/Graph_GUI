package controllers;

import graph.Vertex;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class RenameVertexController {

    @FXML
    private TextField newName;
    private static Vertex vertexToRename;

    @FXML
    private void close(Event event) {
        vertexToRename.setId(newName.getText());
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> newName.requestFocus());
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            close(keyEvent);
        }
    }

    public static void setVertexToRename(Vertex vertexToRename) {
        RenameVertexController.vertexToRename = vertexToRename;
    }
}
