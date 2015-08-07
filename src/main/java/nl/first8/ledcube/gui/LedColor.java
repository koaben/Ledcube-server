package nl.first8.ledcube.gui;

import javafx.scene.paint.Color;

public enum LedColor {
	ON(Color.RED), OFF (new Color(Color.GRAY.getRed(), Color.GRAY.getGreen(), Color.GRAY.getBlue(), 0.9f));
	
	private final Color color;

	LedColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
