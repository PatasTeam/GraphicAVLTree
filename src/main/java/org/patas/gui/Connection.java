package org.patas.gui;

import javafx.scene.shape.Line;

public class Connection extends Line {
    /**
     * Builds a line connecting two circles
     * @param circle1 a circle to connect
     * @param circle2 a circle to connect
     */
    public Connection(LabeledCircle circle1, LabeledCircle circle2) {
        setStrokeWidth(4.0);
        setViewOrder(1.0);
        startXProperty().bind(circle1.getPositionBindings()[0]);
        startYProperty().bind(circle1.getPositionBindings()[1]);
        endXProperty().bind(circle2.getPositionBindings()[0]);
        endYProperty().bind(circle2.getPositionBindings()[1]);
    }
}
