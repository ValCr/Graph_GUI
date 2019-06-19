//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package style;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.css.*;
import javafx.css.converter.EnumConverter;
import javafx.css.converter.SizeConverter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.ToggleSwitch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToggleSwitchSkin extends SkinBase<ToggleSwitch> {
    private final StackPane thumb;
    private final StackPane thumbArea;
    private final Label label;
    private final StackPane labelContainer;
    private final TranslateTransition transition;
    private DoubleProperty thumbMoveAnimationTime = null;
    private StyleableObjectProperty<ToggleSwitchSkin.ThumbDisplay> thumbDisplay;
    private static final CssMetaData<ToggleSwitch, Number> THUMB_MOVE_ANIMATION_TIME_META_DATA = new CssMetaData<ToggleSwitch, Number>("-thumb-move-animation-time", SizeConverter.getInstance(), 200) {
        public boolean isSettable(ToggleSwitch toggleSwitch) {
            ToggleSwitchSkin skin = (ToggleSwitchSkin)toggleSwitch.getSkin();
            return skin.thumbMoveAnimationTime == null || !skin.thumbMoveAnimationTime.isBound();
        }

        public StyleableProperty<Number> getStyleableProperty(ToggleSwitch toggleSwitch) {
            ToggleSwitchSkin skin = (ToggleSwitchSkin)toggleSwitch.getSkin();
            return (StyleableProperty)skin.thumbMoveAnimationTimeProperty();
        }
    };
    private static final CssMetaData<ToggleSwitch, ToggleSwitchSkin.ThumbDisplay> THUMB_DISPLAY_META_DATA = new CssMetaData<ToggleSwitch, ToggleSwitchSkin.ThumbDisplay>("-toggle-display", new EnumConverter(ToggleSwitchSkin.ThumbDisplay.class)) {
        public boolean isSettable(ToggleSwitch toggleSwitch) {
            ToggleSwitchSkin skin = (ToggleSwitchSkin)toggleSwitch.getSkin();
            return !skin.thumbDisplay.isBound();
        }

        public StyleableProperty<ToggleSwitchSkin.ThumbDisplay> getStyleableProperty(ToggleSwitch toggleSwitch) {
            ToggleSwitchSkin skin = (ToggleSwitchSkin)toggleSwitch.getSkin();
            return skin.thumbDisplayProperty();
        }
    };
    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    public ToggleSwitchSkin(ToggleSwitch control) {
        super(control);
        this.thumbDisplay = new SimpleStyleableObjectProperty<ToggleSwitchSkin.ThumbDisplay>(THUMB_DISPLAY_META_DATA, ToggleSwitchSkin.ThumbDisplay.RIGHT) {
            protected void invalidated() {
                ((ToggleSwitch)ToggleSwitchSkin.this.getSkinnable()).requestLayout();
            }
        };
        this.thumb = new StackPane();
        this.thumbArea = new StackPane();
        this.label = new Label();
        this.labelContainer = new StackPane();
        this.transition = new TranslateTransition(Duration.millis(this.getThumbMoveAnimationTime()), this.thumb);
        this.transition.setInterpolator(Interpolator.EASE_OUT);
        this.label.textProperty().bind(control.textProperty());
        this.getChildren().addAll(new Node[]{this.labelContainer, this.thumbArea, this.thumb});
        this.labelContainer.getChildren().addAll(new Node[]{this.label});
        StackPane.setAlignment(this.label, Pos.CENTER_LEFT);
        this.thumb.getStyleClass().setAll(new String[]{"thumb"});
        this.thumbArea.getStyleClass().setAll(new String[]{"thumb-area"});
        this.thumbArea.setOnMouseReleased((event) -> {
            this.mousePressedOnToggleSwitch(control);
        });
        this.thumb.setOnMouseReleased((event) -> {
            this.mousePressedOnToggleSwitch(control);
        });
        control.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != oldValue) {
                this.selectedStateChanged();
            }

        });
    }

    private void selectedStateChanged() {
        if (this.transition != null) {
            this.transition.stop();
            this.transition.setDuration(Duration.millis(this.getThumbMoveAnimationTime()));
        }

        double thumbAreaWidth = this.snapSize(this.thumbArea.prefWidth(-1.0D));
        Insets thumbAreaPadding = this.thumbArea.getPadding();
        double thumbWidth = this.snapSize(this.thumb.prefWidth(-1.0D));
        double distance = thumbAreaWidth - thumbWidth - thumbAreaPadding.getRight() - thumbAreaPadding.getLeft();
        if (!((ToggleSwitch)this.getSkinnable()).isSelected()) {
            this.thumb.setLayoutX(this.thumbArea.getLayoutX());
            this.transition.setFromX(distance);
            this.transition.setToX(0.0D);
        } else {
            this.thumb.setTranslateX(this.thumbArea.getLayoutX());
            this.transition.setFromX(0.0D);
            this.transition.setToX(distance);
        }

        this.transition.setCycleCount(1);
        this.transition.play();
    }

    private void mousePressedOnToggleSwitch(ToggleSwitch toggleSwitch) {
        toggleSwitch.setSelected(!toggleSwitch.isSelected());
    }

    private DoubleProperty thumbMoveAnimationTimeProperty() {
        if (this.thumbMoveAnimationTime == null) {
            this.thumbMoveAnimationTime = new StyleableDoubleProperty(200.0D) {
                public Object getBean() {
                    return ToggleSwitchSkin.this;
                }

                public String getName() {
                    return "thumbMoveAnimationTime";
                }

                public CssMetaData<ToggleSwitch, Number> getCssMetaData() {
                    return ToggleSwitchSkin.THUMB_MOVE_ANIMATION_TIME_META_DATA;
                }
            };
        }

        return this.thumbMoveAnimationTime;
    }

    private double getThumbMoveAnimationTime() {
        return this.thumbMoveAnimationTime == null ? 200.0D : this.thumbMoveAnimationTime.get();
    }

    private StyleableObjectProperty<ToggleSwitchSkin.ThumbDisplay> thumbDisplayProperty() {
        return this.thumbDisplay;
    }

    private ToggleSwitchSkin.ThumbDisplay getThumbDisplay() {
        return (ToggleSwitchSkin.ThumbDisplay)this.thumbDisplay.get();
    }

    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        ToggleSwitch toggleSwitch = (ToggleSwitch)this.getSkinnable();
        double thumbWidth = this.thumb.prefWidth(-1.0D);
        double thumbHeight = this.thumb.prefHeight(-1.0D);
        this.thumb.resize(this.snapSize(thumbWidth), this.snapSize(thumbHeight));
        double labelWidth = this.labelContainer.prefWidth(-1.0D);
        Insets thumbAreaPadding = this.thumbArea.getPadding();
        double thumbAreaWidth = this.thumbArea.prefWidth(-1.0D);
        double thumbAreaHeight = this.thumbArea.prefHeight(-1.0D);
        double thumbAreaX;
        double labelX;
        if (this.getThumbDisplay().equals(ToggleSwitchSkin.ThumbDisplay.RIGHT)) {
            thumbAreaX = contentWidth - thumbAreaWidth;
            labelX = contentX;
        } else if (this.getThumbDisplay().equals(ToggleSwitchSkin.ThumbDisplay.LEFT)) {
            thumbAreaX = contentX;
            labelX = contentWidth - labelWidth;
        } else {
            thumbAreaX = contentX;
            labelX = 0.0D;
        }

        this.thumbArea.resize(this.snapSize(thumbAreaWidth), this.snapSize(thumbAreaHeight));
        this.thumbArea.setLayoutX(this.snapPosition(thumbAreaX));
        this.thumbArea.setLayoutY(this.snapPosition(contentY));
        if (!this.getThumbDisplay().equals(ToggleSwitchSkin.ThumbDisplay.THUMB_ONLY)) {
            this.labelContainer.resize(this.snapSize(contentWidth - thumbAreaWidth), this.snapSize(thumbAreaHeight));
            this.labelContainer.setLayoutY(this.snapPosition(contentY));
            this.labelContainer.setLayoutX(this.snapPosition(labelX));
        }

        if (!toggleSwitch.isSelected()) {
            this.thumb.setLayoutX(this.snapPosition(thumbAreaX + thumbAreaPadding.getLeft()));
        } else {
            this.thumb.setLayoutX(this.snapPosition(thumbAreaX + thumbAreaWidth - thumbAreaPadding.getRight() - thumbWidth));
        }

        this.thumb.setLayoutY(this.snapPosition(contentY + thumbAreaPadding.getBottom()));
    }

    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return leftInset + this.label.prefWidth(-1.0D) + this.thumbArea.prefWidth(-1.0D) + rightInset;
    }

    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return topInset + Math.max(this.thumb.prefHeight(-1.0D), this.label.prefHeight(-1.0D)) + bottomInset;
    }

    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return leftInset + this.label.prefWidth(-1.0D) + 20.0D + this.thumbArea.prefWidth(-1.0D) + rightInset;
    }

    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return topInset + Math.max(this.thumb.prefHeight(-1.0D), this.label.prefHeight(-1.0D)) + bottomInset;
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return STYLEABLES;
    }

    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    static {
        List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList(SkinBase.getClassCssMetaData());
        styleables.add(THUMB_MOVE_ANIMATION_TIME_META_DATA);
        styleables.add(THUMB_DISPLAY_META_DATA);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }

    private static enum ThumbDisplay {
        LEFT,
        RIGHT,
        THUMB_ONLY;

        private ThumbDisplay() {
        }
    }
}
