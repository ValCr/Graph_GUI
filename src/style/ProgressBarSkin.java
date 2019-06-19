//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package style;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProgressBarSkin extends SkinBase<ProgressBar> {
    private static final int NUMBER_DOTS = 5;
    private static final String DOT_STYLE_CLASS = "dot";
    private static final String SINGLE_DOT_STYLE_CLASS_PREFIX = "dot_";
    private static final int SCREEN_OFFSET = 50;
    private static final int MS_BETWEEN_DOTS = 200;
    private double barWidth;
    private StackPane bar;
    private StackPane track;
    protected List<Region> dots = new ArrayList(5);
    private Animation indeterminateAnimation;
    private Rectangle clip;

    public ProgressBarSkin(ProgressBar control) {
        super(control);
        this.barWidth = (double)((int)(control.getWidth() - this.snappedLeftInset() - this.snappedRightInset()) * 2) * Math.min(1.0D, Math.max(0.0D, control.getProgress())) / 2.0D;
        control.progressProperty().addListener((observable) -> {
            this.updateProgress();
        });
        control.widthProperty().addListener((observable) -> {
            this.updateProgress();
        });
        this.initialize();
    }

    protected void initialize() {
        this.createDots();
        this.clip = new Rectangle();
        this.track = new StackPane();
        this.track.getStyleClass().setAll(new String[]{"track"});
        this.bar = new StackPane();
        this.bar.getStyleClass().setAll(new String[]{"bar"});
        this.getChildren().setAll(new Node[]{this.track, this.bar});
        this.getChildren().addAll(this.dots);
    }

    protected void updateProgress() {
        ProgressIndicator control = (ProgressIndicator)this.getSkinnable();
        boolean isIndeterminate = control.isIndeterminate();
        if (!isIndeterminate) {
            this.barWidth = (double)((int)(control.getWidth() - this.snappedLeftInset() - this.snappedRightInset()) * 2) * Math.min(1.0D, Math.max(0.0D, control.getProgress())) / 2.0D;
            ((ProgressBar)this.getSkinnable()).requestLayout();
        }

    }

    private void createDots() {
        for(int i = 0; i < 5; ++i) {
            Region dot = new Region();
            dot.getStyleClass().setAll(new String[]{"dot", "dot_" + (i + 1)});
            dot.setLayoutX(-50.0D);
            this.dots.add(dot);
        }

    }

    private Transition createAnimation() {
        ParallelTransition parallelTransition = new ParallelTransition();

        for(int i = 0; i < 5; ++i) {
            Region dot = (Region)this.dots.get(i);
            Transition transition = this.createAnimationForDot(dot, i);
            transition.setDelay(Duration.millis((double)(i * 200)));
            parallelTransition.getChildren().add(transition);
        }

        parallelTransition.setCycleCount(-1);
        return parallelTransition;
    }

    private Transition createAnimationForDot(Region dot, int dotNumber) {
        double controlWidth = ((ProgressBar)this.getSkinnable()).getBoundsInLocal().getWidth();
        double firstStop = 0.6D * controlWidth;
        SequentialTransition sequentialTransition = new SequentialTransition();
        TranslateTransition firstTranslation = new TranslateTransition(Duration.millis(1800.0D), dot);
        firstTranslation.setFromX(0.0D);
        firstTranslation.setToX(firstStop - (double)(dotNumber * 8));
        firstTranslation.setInterpolator(Interpolator.SPLINE(0.2135D, 0.9351D, 0.7851D, 0.964D));
        TranslateTransition secondTranslation = new TranslateTransition(Duration.millis(600.0D), dot);
        secondTranslation.setToX(controlWidth + 50.0D);
        secondTranslation.setInterpolator(Interpolator.SPLINE(0.9351D, 0.2135D, 0.964D, 0.7851D));
        sequentialTransition.getChildren().addAll(new Animation[]{firstTranslation, secondTranslation});
        return sequentialTransition;
    }

    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        ProgressBar control = (ProgressBar)this.getSkinnable();
        boolean isIndeterminate = control.isIndeterminate();
        this.track.resizeRelocate(contentX, contentY, contentWidth, contentHeight);
        this.track.setVisible(true);
        if (isIndeterminate) {
            if (this.indeterminateAnimation != null) {
                this.indeterminateAnimation.stop();
            }

            for(int i = 0; i < 5; ++i) {
                Region dot = (Region)this.dots.get(i);
                dot.resize(dot.prefWidth(-1.0D), dot.prefHeight(-1.0D));
                dot.setLayoutY(0.0D);
            }

            this.indeterminateAnimation = this.createAnimation();
            if (((ProgressBar)this.getSkinnable()).isVisible()) {
                this.indeterminateAnimation.play();
            }

            this.clip.setLayoutY(contentX);
            this.clip.setLayoutY(contentY);
            this.clip.setWidth(contentWidth);
            this.clip.setHeight(contentHeight);
            control.setClip(this.clip);
        } else {
            if (this.indeterminateAnimation != null) {
                this.indeterminateAnimation.stop();
                this.indeterminateAnimation = null;
            }

            control.setClip((Node)null);
            this.bar.resizeRelocate(contentX, contentY, this.barWidth, contentHeight);
        }

    }

    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        if (!((ProgressBar)this.getSkinnable()).isIndeterminate()) {
            return topInset + this.track.prefHeight(-1.0D) + bottomInset;
        } else {
            double maxDotsHeight = 0.0D;

            Region dot;
            for(Iterator var13 = this.dots.iterator(); var13.hasNext(); maxDotsHeight = Math.max(dot.prefHeight(-1.0D), maxDotsHeight)) {
                dot = (Region)var13.next();
            }

            return topInset + maxDotsHeight + bottomInset;
        }
    }

    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        if (!((ProgressBar)this.getSkinnable()).isIndeterminate()) {
            return topInset + this.track.maxHeight(-1.0D) + bottomInset;
        } else {
            double maxDotsHeight = 0.0D;

            Region dot;
            for(Iterator var13 = this.dots.iterator(); var13.hasNext(); maxDotsHeight = Math.max(dot.maxHeight(-1.0D), maxDotsHeight)) {
                dot = (Region)var13.next();
            }

            return topInset + maxDotsHeight + bottomInset;
        }
    }

    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        if (!((ProgressBar)this.getSkinnable()).isIndeterminate()) {
            return topInset + this.track.minHeight(-1.0D) + bottomInset;
        } else {
            double maxDotsHeight = 0.0D;

            Region dot;
            for(Iterator var13 = this.dots.iterator(); var13.hasNext(); maxDotsHeight = Math.max(dot.minHeight(-1.0D), maxDotsHeight)) {
                dot = (Region)var13.next();
            }

            return topInset + maxDotsHeight + bottomInset;
        }
    }
}
