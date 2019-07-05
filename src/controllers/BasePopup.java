package controllers;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
}
