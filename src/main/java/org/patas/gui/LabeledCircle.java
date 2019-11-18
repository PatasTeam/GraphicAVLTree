package org.patas.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Point2D;
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
    public LabeledCircle(String text) {
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
        // Move outside of the screen
        relocate(getScene().getWidth() / 2, getScene().getHeight() + 100);
    }

    /**
     * Initializes circle with predetermined styles
     */
    private void configureCircle() {
        Color randomColor = Color.hsb(360 * Math.random(), 1.0, 1.0, 0.7);
        circle = new Circle(0, randomColor);
        circle.radiusProperty().bind(minPrefSize.divide(2.0));
        circle.centerXProperty().bind(widthProperty().divide(2.0));
        circle.centerYProperty().bind(heightProperty().divide(2.0));
    }

    /**
     * Initializes label with predetermined styles
     * @param text the text to be displayed inside the label
     */
    private void configureLabel(String text) {
        label = new Label(text);
        label.fontProperty().bind(Bindings.createObjectBinding(
                () -> new Font(minPrefSize.divide(4.0).doubleValue()),
                minPrefSize
        ));
        label.setTextFill(Color.BLACK);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.prefWidthProperty().bind(widthProperty());
        label.prefHeightProperty().bind(heightProperty());
    }

    /**
     * Moves the circle to the specified point
     * @param size the new size of the circle
     * @param position the point to move the circle to
     */
    public void adjustSizePosition(Point2D size, Point2D position) {
        Timeline tl = new Timeline(new KeyFrame(
                Duration.millis(500),
                new KeyValue(prefWidthProperty(), size.getX()),
                new KeyValue(prefHeightProperty(), size.getY()),
                new KeyValue(layoutXProperty(), position.getX()),
                new KeyValue(layoutYProperty(), position.getY())
        ));
        tl.play();
    }
}
