package nl.first8.ledcube.gui;

import java.util.ArrayList;
import java.util.List;

import nl.first8.ledcube.CubeInput;
import nl.first8.ledcube.CubeInputListener;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class JavaFXCube extends Group implements CubeInput {

	private int cubeSize;

	private List<Sphere> leds = new ArrayList<>();
	private List<CubeInputListener> listeners = new ArrayList<>();

	public JavaFXCube(int cubeSize, LedColor color) {
		this.cubeSize = cubeSize;

		init(color);
	}
	
	@Override
	public String getName() {
	    return "JavaFXCubeMouseClicks";
	}
	
	public void addLedCubeListener(final CubeInputListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public boolean removeListener(final CubeInputListener listener) {
		return listeners.remove(listener);
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
	                for (LedColor c : LedColor.values()) {
	                    if (c.getColor().equals(m.getDiffuseColor())) {
	                        return c;
	                    }
	                }
	            }
	        }
	        return null;
	    }

	private void init(LedColor initialColor) {
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(initialColor.getColor());

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
	    System.out.println("fire on click");
		Sphere s = (Sphere) event.getSource();
		Coordinate3D c = (Coordinate3D) s.getUserData();
		LedColor color = getColor(c);
		for (CubeInputListener l : listeners) {
	        System.out.println("notifying l for " + color);
	        l.onPixelChange(c.getX(), c.getY(), c.getZ(), color!=LedColor.ON);
	        l.flush();
		}
	}

}
