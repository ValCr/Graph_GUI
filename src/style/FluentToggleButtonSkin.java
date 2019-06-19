//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package style;

import javafx.beans.property.BooleanProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.converter.BooleanConverter;
import javafx.scene.control.SkinBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.skin.ToggleButtonSkin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FluentToggleButtonSkin extends ToggleButtonSkin {
    private static final CssMetaData<ToggleButton, Boolean> SHRINK_ANIMATE_ON_PRESS_META_DATA = new CssMetaData<ToggleButton, Boolean>("-shrink-animate-on-press", BooleanConverter.getInstance(), true) {
        public boolean isSettable(ToggleButton button) {
            FluentToggleButtonSkin skin = (FluentToggleButtonSkin)button.getSkin();
            return !skin.shrinkAnimateOnPress.isBound();
        }

        public StyleableProperty<Boolean> getStyleableProperty(ToggleButton button) {
            FluentToggleButtonSkin skin = (FluentToggleButtonSkin)button.getSkin();
            return (StyleableProperty)skin.shrinkAnimateOnPressProperty();
        }
    };
    private BooleanProperty shrinkAnimateOnPress;
    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    public FluentToggleButtonSkin(ToggleButton button) {
        super(button);
        this.shrinkAnimateOnPress = new SimpleStyleableBooleanProperty(SHRINK_ANIMATE_ON_PRESS_META_DATA, true);
        ButtonAnimationHelper.setupButton(button, this.shrinkAnimateOnPressProperty());
    }

    private BooleanProperty shrinkAnimateOnPressProperty() {
        return this.shrinkAnimateOnPress;
    }

    private boolean isShrinkAnimateOnPress() {
        return this.shrinkAnimateOnPress.get();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return STYLEABLES;
    }

    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    static {
        List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList(SkinBase.getClassCssMetaData());
        styleables.add(SHRINK_ANIMATE_ON_PRESS_META_DATA);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }
}
