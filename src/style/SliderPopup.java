//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package style;

import javafx.beans.Observable;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;

class SliderPopup extends PopupControl {
    private static final String DEFAULT_STYLE_CLASS = "slider-popup";
    private DoubleProperty value = new SimpleDoubleProperty();

    SliderPopup() {
        this.getStyleClass().add("slider-popup");
    }

    DoubleProperty valueProperty() {
        return this.value;
    }

    double getValue() {
        return this.value.get();
    }

    void setValue(double value) {
        this.value.set(value);
    }

    protected Skin<?> createDefaultSkin() {
        return new SliderPopup.SliderPopupSkin(this);
    }

    private class SliderPopupSkin implements Skin<SliderPopup> {
        SliderPopup skinnable;
        Label valueText;

        SliderPopupSkin(SliderPopup control) {
            this.skinnable = control;
            this.valueText = new Label();
            this.valueText.textProperty().bind(new StringBinding() {
                {
                    super.bind(new Observable[]{SliderPopupSkin.this.skinnable.valueProperty()});
                }

                protected String computeValue() {
                    return String.valueOf(Math.round(SliderPopupSkin.this.skinnable.getValue()));
                }
            });
        }

        public SliderPopup getSkinnable() {
            return this.skinnable;
        }

        public Node getNode() {
            return this.valueText;
        }

        public void dispose() {
        }
    }
}
