//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package style;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class TextFieldWithButtonSkin extends TextFieldSkin {
    private StackPane rightButton;
    private Region rightButtonGraphic;
    protected TextField textField;

    public TextFieldWithButtonSkin(TextField textField) {
        super(textField);
        this.textField = textField;
        this.rightButton = new StackPane();
        this.rightButton.getStyleClass().setAll(new String[]{"right-button"});
        this.rightButton.setFocusTraversable(false);
        this.rightButtonGraphic = new Region();
        this.rightButtonGraphic.getStyleClass().setAll(new String[]{"right-button-graphic"});
        this.rightButtonGraphic.setFocusTraversable(false);
        this.rightButtonGraphic.setMaxWidth(-1.0D / 0.0);
        this.rightButtonGraphic.setMaxHeight(-1.0D / 0.0);
        this.rightButton.setVisible(false);
        this.rightButtonGraphic.setVisible(false);
        this.rightButton.getChildren().add(this.rightButtonGraphic);
        this.getChildren().add(this.rightButton);
        this.setupListeners();
    }

    private void setupListeners() {
        TextField textField = (TextField)this.getSkinnable();
        this.rightButton.setOnMousePressed((event) -> {
            this.rightButtonPressed();
        });
        this.rightButton.setOnMouseReleased((event) -> {
            this.rightButtonReleased();
        });
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.textChanged();
        });
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            this.focusChanged();
        });
    }

    protected void textChanged() {
        if (this.textField.getText() != null) {
            this.rightButton.setVisible(!this.textField.getText().isEmpty());
            this.rightButtonGraphic.setVisible(!this.textField.getText().isEmpty());
        }
    }

    protected void focusChanged() {
        if (this.textField.getText() != null) {
            this.rightButton.setVisible(this.textField.isFocused() && !this.textField.getText().isEmpty());
            this.rightButtonGraphic.setVisible(this.textField.isFocused() && !this.textField.getText().isEmpty());
        }
    }

    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);
        double clearGraphicWidth = this.snapSize(this.rightButtonGraphic.prefWidth(-1.0D));
        double clearButtonWidth = this.rightButton.snappedLeftInset() + clearGraphicWidth + this.rightButton.snappedRightInset();
        this.rightButton.resize(clearButtonWidth, h);
        this.positionInArea(this.rightButton, x + w - clearButtonWidth, y, clearButtonWidth, h, 0.0D, HPos.CENTER, VPos.CENTER);
    }

    protected void rightButtonPressed() {
    }

    protected void rightButtonReleased() {
    }
}
