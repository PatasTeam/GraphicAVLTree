package org.patas.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

class LabeledCircle extends Pane {
    private DoubleBinding minPrefSize;
    private Circle circle;
    private Label label;

    /**
     * Creates a new instance of LabeledCircle with a specified label, position and radius.
     * @param text the label assigned to this circle
     * @param centerX the horizontal position of the center of the circle in pixels
     * @param centerY the vertical position of the center of the circle in pixels
     * @param radius the radius of the circle in pixels
     */
    public LabeledCircle(String text, double centerX, double centerY, double radius) {
        setPrefSize(2.0 * radius, 2.0 * radius);
        minPrefSize = Bindings.createDoubleBinding(
                () -> Math.min(prefHeightProperty().doubleValue(), prefWidthProperty().doubleValue()),
                prefHeightProperty(),
                prefWidthProperty()
        );
        configureCircle();
        configureLabel(text);
        relocate(centerX, centerY);
        translateXProperty().bind(widthProperty().divide(-2.0));
        translateYProperty().bind(heightProperty().divide(-2.0));
        getChildren().addAll(circle, label);
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
}
