//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package style;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PasswordFieldSkin extends TextFieldWithButtonSkin {
    private boolean isMaskTextDisabled = false;

    public PasswordFieldSkin(TextField textField) {
        super(textField);
    }

    protected void rightButtonPressed() {
        TextField textField = (TextField)this.getSkinnable();
        this.isMaskTextDisabled = true;
        textField.setText(textField.getText());
        this.isMaskTextDisabled = false;
    }

    protected void rightButtonReleased() {
        TextField textField = (TextField)this.getSkinnable();
        textField.setText(textField.getText());
        textField.end();
    }

    protected String maskText(String txt) {
        if (this.getSkinnable() instanceof PasswordField && !this.isMaskTextDisabled) {
            int n = txt.length();
            StringBuilder passwordBuilder = new StringBuilder(n);

            for(int i = 0; i < n; ++i) {
                passwordBuilder.append('●');
            }

            return passwordBuilder.toString();
        } else {
            return txt;
        }
    }
}
