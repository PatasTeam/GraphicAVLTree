package org.patas.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class LabeledCircle extends Pane {
    private DoubleBinding minPrefSize;
    private Circle circle;
    private Label label;

    /**
     * Creates a new instance of LabeledCircle with a specified label, position and radius.
     * @param text the label assigned to this circle
     */
    public LabeledCircle(String text, TreePane treePane) {
        minPrefSize = Bindings.createDoubleBinding(
                () -> Math.min(prefHeightProperty().doubleValue(), prefWidthProperty().doubleValue()),
                prefHeightProperty(),
                prefWidthProperty()
        );
        configureCircle();
        configureLabel(text);
        translateXProperty().bind(prefWidthProperty().divide(-2.0));
        translateYProperty().bind(prefHeightProperty().divide(-2.0));
        getChildren().addAll(circle, label);
        treePane.getChildren().add(this);
        // Move outside of the screen
        relocate(getScene().getWidth() / 2, getScene().getHeight() + 100);
    }

    /**
     * Initializes circle with predetermined styles
     */
    private void configureCircle() {
        Color randomColor = Color.hsb(360 * Math.random(), 0.8, 1.0);
        circle = new Circle(0, randomColor);
        circle.radiusProperty().bind(minPrefSize.divide(2.0));
        circle.centerXProperty().bind(prefWidthProperty().divide(2.0));
        circle.centerYProperty().bind(prefHeightProperty().divide(2.0));
    }

    /**
     * Initializes label with predetermined styles
     * @param text the text to be displayed inside the label
     */
    private void configureLabel(String text) {
        label = new Label(text);
        label.fontProperty().bind(Bindings.createObjectBinding(
                () -> new Font(minPrefSize.divide(2.0).doubleValue()),
                minPrefSize
        ));
        label.setTextFill(Color.BLACK);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.prefWidthProperty().bind(prefWidthProperty());
        label.prefHeightProperty().bind(prefHeightProperty());
    }

    /**
     * Calculates and moves the circle to its new position
     * @param levelWidth the total level width
     * @param treeHeight the total tree height
     * @param nodesToLeft the nodes to this node's left
     * @param nodesToTop the nodes to this node's top
     */
    public void adjustSizePosition(int levelWidth, int treeHeight, int nodesToLeft, int nodesToTop) {
        prefWidthProperty().unbind();
        DoubleBinding newPrefWidth = getScene().widthProperty().divide(levelWidth);
        prefHeightProperty().unbind();
        DoubleBinding newPrefHeight = getScene().heightProperty().subtract(ControlsPane.HEIGHT).divide(treeHeight);
        layoutXProperty().unbind();
        DoubleBinding newLayoutX = getScene().widthProperty().multiply(nodesToLeft).divide(levelWidth);
        layoutYProperty().unbind();
        DoubleBinding newLayoutY = getScene().heightProperty().multiply(nodesToTop).divide(treeHeight);
        Timeline tl = new Timeline(new KeyFrame(
                Duration.millis(500),
                new KeyValue(prefWidthProperty(), newPrefWidth.getValue()),
                new KeyValue(prefHeightProperty(), newPrefHeight.getValue()),
                new KeyValue(layoutXProperty(), newLayoutX.getValue() + newPrefWidth.getValue() / 2.0),
                new KeyValue(layoutYProperty(), newLayoutY.getValue() + newPrefHeight.getValue() / 2.0)
        ));
        tl.setOnFinished(event -> {
            prefWidthProperty().bind(newPrefWidth);
            prefHeightProperty().bind(newPrefHeight);
            layoutXProperty().bind(newLayoutX.add(newPrefWidth.divide(2.0)));
            layoutYProperty().bind(newLayoutY.add(newPrefHeight.divide(2.0)));
        });
        tl.play();
    }

    /**
     * Returns an array with layoutXProperty and layoutYProperty
     * @return layout X and Y properties
     */
    DoubleProperty[] getPositionBindings() {
        return new DoubleProperty[]{ layoutXProperty(), layoutYProperty() };
    }
}
