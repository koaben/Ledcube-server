package nl.first8.ledcube.gui;

import java.util.ArrayList;
import java.util.Collection;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import nl.first8.ledcube.CubeException;
import nl.first8.ledcube.CubeInput;
import nl.first8.ledcube.CubeOutput;
import nl.first8.ledcube.InputToCubeOutputListener;
import nl.first8.ledcube.SysoutCube;
import nl.first8.ledcube.UsbCube;
import nl.first8.ledcube.WebInput;
import nl.first8.ledcube.WebOutputListener;
import nl.first8.ledcube.snake.Direction;
import nl.first8.ledcube.snake.SnakeDeathException;
import nl.first8.ledcube.snake.SnakeGame;

public class LedCubeApplication extends Application {

    private static boolean START_SNAKE = false;
    private static final boolean FULL_SCREEN = false;

    double anchorX, anchorY;

    private double anchorAngleX = 0;
    private double anchorAngleY = 0;

    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    private PerspectiveCamera scenePerspectiveCamera = new PerspectiveCamera(false);
    private JavaFXCube javaFxCube;
    private SnakeGame game;
    Collection<CubeOutput> outputs = new ArrayList<>();
    Collection<CubeInput> inputs = new ArrayList<>();

    public static void main(String[] args) {
        START_SNAKE = (args.length > 0 && args[0].equalsIgnoreCase("snake"));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Led Cube");
        primaryStage.setFullScreen(FULL_SCREEN);

        javaFxCube = new JavaFXCube(8, LedColor.OFF);

        setupInputOutputs();

        final Group root = new Group();
        final Scene scene = new Scene(root, 1200, 800, true);
        scene.setFill(Color.BLACK);
        root.getChildren().add(javaFxCube);

        positionLedCube(scene);
        setupMouseHandling(scene);
        PointLight pointLight = setupCamera();

        scene.setCamera(scenePerspectiveCamera);
        root.getChildren().addAll(pointLight, scenePerspectiveCamera);

        scene.setOnKeyReleased(e -> handleKeyEvents(e));

        primaryStage.setScene(scene);
        primaryStage.show();

        if (START_SNAKE) {
            startGame();
        }
    }

    private void setupInputOutputs() {
        game = new SnakeGame();
        
//        outputs.add(game);
        CubeOutput screenOutput = new ScreenCube(javaFxCube);
        outputs.add(screenOutput);

        CubeOutput sysoutCube = new SysoutCube();
        outputs.add(sysoutCube);

        try {
            CubeOutput usbOutput = new UsbCube();
            usbOutput.init();
            outputs.add(usbOutput);
        } catch (CubeException e) {
            System.err.println("No USB cube found");
        }
        
        CubeInput screenInput = javaFxCube;
        CubeInput snakeInput = game;
        
//        CubeInput webInput = new WebInput("http://ledcube.first8.nl/ledcube", 1000L);
//        inputs.add(webInput);
        
        inputs.add(screenInput);
        inputs.add(snakeInput);

        // hook up all inputs to outputs
        for (CubeInput input : inputs) {
            for (CubeOutput output : outputs) {
                
                if (  (input==game && output==game)){
                    // skip these since it updates itself
                } else {
                    System.out.println("Hooking up " + input.getName() + " to update " + output.getName());
                    input.addLedCubeListener( new InputToCubeOutputListener(output));
                }
            }
            
            input.addLedCubeListener(new WebOutputListener());
        }
        
    }

    private void handleKeyEvents(KeyEvent e) {
        switch (e.getCode()) {
        case A:
            game.changeDirection(Direction.LEFT);
            break;
        case D:
            game.changeDirection(Direction.RIGHT);
            break;
        case W:
            game.changeDirection(Direction.FORWARD);
            break;
        case S:
            game.changeDirection(Direction.BACKWARD);
            break;
        case O:
            game.changeDirection(Direction.UP);
            break;
        case L:
            game.changeDirection(Direction.DOWN);
            break;
        case ESCAPE:
            System.exit(1);
            break;
        default:
            break;
        }
    }

    private void startGame() {
        Thread t = new Thread(() -> {
            for (;;) {
                game.reset();
                try {
                    for (;;) {
                        game.tick();
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SnakeDeathException e) {
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private PointLight setupCamera() {
        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateX(600);
        pointLight.setTranslateY(400);
        pointLight.setTranslateZ(-3000);
        return pointLight;
    }

    private void setupMouseHandling(final Scene scene) {
        Bounds b = javaFxCube.getBoundsInLocal();
        double xt = (b.getMaxX() - b.getMinX()) / 2.0;
        double yt = (b.getMaxY() - b.getMinY()) / 2.0;
        double zt = (b.getMaxZ() - b.getMinZ()) / 2.0;

        Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);

        xRotate.setPivotX(xt);
        xRotate.setPivotY(yt);
        xRotate.setPivotZ(zt);

        yRotate.setPivotX(xt);
        yRotate.setPivotY(yt);
        yRotate.setPivotZ(zt);

        javaFxCube.getTransforms().setAll(xRotate, yRotate);
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();

            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
            angleX.set(anchorAngleX + anchorY - event.getSceneY());
        });
    }

    private void positionLedCube(final Scene scene) {
        Bounds b = javaFxCube.getBoundsInLocal();
        javaFxCube.setTranslateX((scene.getWidth() - b.getWidth()) / 2.0);
        javaFxCube.setTranslateY((scene.getHeight() - b.getHeight()) / 2.0);
        javaFxCube.setTranslateZ(100);
    }
}