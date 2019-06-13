package sample.viewModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class JoystickController extends Pane {

    @FXML
    private Circle outerjoystick, btn_joystick;


    private double radius = 0;
    private double centerX = 0; // mouse location
    private double centerY = 0;

    private double initializedCenterX = 0; // real location
    private double initializedCenterY = 0;

    public JoystickController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../JavaFX Components/joystick.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initialize();
    }

    public void initialize() {
        initializedCenterX = btn_joystick.getLayoutX();
        initializedCenterY = btn_joystick.getLayoutY();

//        mapDisplayer.setMapData(null, 0, 0, 0, 0); // map initialized to null ( white blocks )

        System.out.println(initializedCenterX + "," + initializedCenterY);
    }


    public void circleOnMouseDraggedEventHandler(MouseEvent mouseEvent) {
        System.out.println("circleOnMouseDraggedEventHandler");
        dragable(mouseEvent);
    }

    public void circleOnMousePressedEventHandler(MouseEvent mouseEvent) {
        System.out.println("circleOnMousePressedEventHandler");
        dragable(mouseEvent);

    }

    public void circleOnMouseReleasedEventHandler(MouseEvent mouseEvent) {
        System.out.println("circleOnMouseReleasedEventHandler");
        dragable_exit();
    }


    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1-x2) * (x1-x2) + (y1-y2) * (y1-y2));
    }

    public void dragable(MouseEvent event) {
        if(radius == 0) {
            radius = outerjoystick.getRadius();
            centerX = (btn_joystick.localToScene(btn_joystick.getBoundsInLocal()).getMinX() + btn_joystick.localToScene(btn_joystick.getBoundsInLocal()).getMaxX())/2;
            centerY = (btn_joystick.localToScene(btn_joystick.getBoundsInLocal()).getMinY() + btn_joystick.localToScene(btn_joystick.getBoundsInLocal()).getMaxY())/2;

			/*System.out.println("-----------------------------------------");
			System.out.println("Center: (X,Y) = " + centerX + "," + centerY);
			System.out.println("Layout: (X, Y) = " + initializedCenterX + "," + initializedCenterY);
			System.out.println("-----------------------------------------");*/
        }

        double x1 = event.getSceneX();
        double y1 = event.getSceneY();
        double x2, y2;
        final int div=2;
        double distance = dist(event.getSceneX(), event.getSceneY(), centerX, centerY);
        if(distance <= radius/div) {
            btn_joystick.setLayoutX(initializedCenterX + x1 - centerX);
            btn_joystick.setLayoutY(initializedCenterY + y1 - centerY);

            x2 = x1;
            y2 = y1;
        }
        else
        {
            if(x1 > centerX) {
                double alfa = Math.atan((y1-centerY)/(x1-centerX));
                double w = radius * Math.cos(alfa);
                double z = radius * Math.sin(alfa);

                x2 = centerX + w/div;
                y2 = centerY + z/div;
            }
            else
            {
                double alfa = Math.atan((centerY - y1) / (centerX - x1));
                double w = radius * Math.cos(alfa);
                double z = radius * Math.sin(alfa);

                x2 = centerX - w/div;
                y2 = centerY - z/div;
            }

            btn_joystick.setLayoutX(initializedCenterX + x2 - centerX);
            btn_joystick.setLayoutY(initializedCenterY + y2 - centerY);
        }

//        // Setting the Aileron & Elevator values
//        aileron.set((x2 - centerX) / radius);
//        elevator.set((centerY - y2) / radius);
//
//        viewModel.updateAileronAndElevator();
        //System.out.println("(airleron, elevator) = ("+aileron.get()+","+elevator.get()+")");
    }

    public void dragable_exit() {
        btn_joystick.setLayoutX(initializedCenterX);
        btn_joystick.setLayoutY(initializedCenterY);

//        aileron.set(0);
//        elevator.set(0);
    }
}
