package nl.first8.ledcube.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import nl.first8.ledcube.CubeOutput;
import nl.first8.ledcube.UsbCube;

public class LedCube extends Group {

	private int cubeSize;
	private LedColor color;

	private List<Sphere> leds = new ArrayList<>();
	private List<LedCubeListener> listeners = new ArrayList<>();

	public LedCube(int cubeSize, LedColor color) {
		this.cubeSize = cubeSize;
		this.color = color;

		init();
	}

	public void addLedCubeListener(final LedCubeListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public boolean removeListener(final LedCubeListener listener) {
		return listeners.remove(listener);
	}

	public synchronized void resetLeds() {
		for (Sphere s : leds) {
			PhongMaterial material = new PhongMaterial(color.getColor());
			s.setMaterial(material);
		}
	}

	public synchronized void changeColor(Coordinate3D coordinate, LedColor color) {
		for (Sphere s : leds) {
			if (s.getUserData().equals(coordinate)) {
				s.setMaterial(new PhongMaterial(color.getColor()));
			}
		}
	}

	   public synchronized LedColor getColor(Coordinate3D coordinate) {
	        for (Sphere s : leds) {
	            if (s.getUserData().equals(coordinate)) {
	                PhongMaterial m = (PhongMaterial) s.getMaterial();
	                Color c = m.getDiffuseColor();
	                if (c.equals(LedColor.ON))  {
	                    return LedColor.ON;
	                } else {
	                    return LedColor.OFF;
	                }
	            }
	        }
	        throw new IllegalArgumentException("Wrong coordinate: " + coordinate);
	    }

	private void init() {
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(color.getColor());

		int ledSpacing = 60;
		int ledSize = 10;

		for (int x = 0; x < cubeSize; x++) {
			for (int y = 0; y < cubeSize; y++) {
				for (int z = 0; z < cubeSize; z++) {
					final Sphere led = new Sphere(ledSize);
					led.setMaterial(material);
					led.setTranslateX(x * (ledSize + ledSpacing) + ledSize);
					led.setTranslateY(y * (ledSize + ledSpacing) + ledSize);
					led.setTranslateZ(z * (ledSize + ledSpacing) + ledSize);
					led.setOnMouseClicked(event -> {
						fireOnClick(event);
					});
					led.setUserData(new Coordinate3D(x, z, cubeSize - y -1));
					getChildren().add(led);
					leds.add(led);
				}
			}
		}

		// add some axis
		Bounds b = getBoundsInLocal();

		Cylinder xAxis = new Cylinder(2, b.getMaxX());
		xAxis.setMaterial(new PhongMaterial(Color.RED));
		xAxis.getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
		xAxis.setTranslateX(b.getMaxX() / 2);
		xAxis.setTranslateY(b.getMaxY() + 20);
		getChildren().add(xAxis);

		Cylinder yAxis = new Cylinder(2, b.getMaxZ());
		yAxis.setMaterial(new PhongMaterial(Color.BLUE));
		yAxis.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
		yAxis.setTranslateX(-20);
		yAxis.setTranslateY(b.getMaxY() + 20);
		yAxis.setTranslateZ(b.getMaxZ() / 2);
		getChildren().add(yAxis);

		Cylinder zAxis = new Cylinder(2, b.getMaxX());
		zAxis.setMaterial(new PhongMaterial(Color.GREEN));
		zAxis.setTranslateX(-20);
		zAxis.setTranslateY(b.getMaxY() / 2);
		getChildren().add(zAxis);
	}

	private synchronized void fireOnClick(MouseEvent event) {
		Sphere s = (Sphere) event.getSource();
		Coordinate3D c = (Coordinate3D) s.getUserData();
		for (LedCubeListener l : listeners) {
		    LedColor color = getColor(c);
			l.onPixelChange(c.getX(), c.getY(), c.getZ(), (color==LedColor.ON));
		}
	}

//	public synchronized void sync(CubeOutput cube) {
//		for (int x = 0; x < 8; x++) {
//			for (int y = 0; y < 8; y++) {
//				for (int z = 0; z < 8; z++) {
//					LedColor color = (cube.getPixel(x, y, z) ? LedColor.ON
//							: LedColor.OFF);
//					changeColor(new Coordinate3D(x, y, z), color);
//				}
//			}
//		}
//	}
}
