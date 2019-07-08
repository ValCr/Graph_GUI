package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private CheckMenuItem help;
    private MainController mainController;

    @FXML
    void showHelp() {
        Label helpInfo = mainController.getGraphPaneController().getHelpInfo();
        helpInfo.setVisible(help.isSelected());
    }

    public void close() {
        System.exit(0);
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public CheckMenuItem getHelp() {
        return help;
    }
}
