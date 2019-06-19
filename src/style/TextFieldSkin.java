//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package style;

import javafx.scene.control.TextField;

public class TextFieldSkin extends TextFieldWithButtonSkin {
    public TextFieldSkin(TextField textField) {
        super(textField);
    }

    protected void rightButtonPressed() {
        ((TextField)this.getSkinnable()).setText("");
    }
}
