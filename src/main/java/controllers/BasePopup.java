package controllers;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Define the base code for popup window that appear the user changes values in the graph.
 *
 * @see ChangeCostController
 * @see RenameVertexController
 */
public abstract class BasePopup {
    @FXML
    protected Label warnings;
    @FXML
    protected TextField text;

    @FXML
    protected abstract void submit(Event event);

    @FXML
    protected void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            submit(keyEvent);
        }
    }

    @FXML
    protected void initialize() {
        Platform.runLater(() -> text.requestFocus());
    }

    protected void closePopup(Event event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
