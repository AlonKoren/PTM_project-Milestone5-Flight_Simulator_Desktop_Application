package sample.viewModel;

import alon.flightsim.FlyMain;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;

import java.io.IOException;

public class Right_SideController extends SplitPane {

    @FXML
    Radio_ButtonsController radioButtons;
    @FXML
    Display_ScriptController displayScript;
    @FXML
    JoystickController joystick;

    FlyMain flyMain=new FlyMain();
    Property<Boolean> booleanProperty=new SimpleBooleanProperty();


    public Right_SideController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../JavaFX Components/right_side.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        bind();
    }
    private void bind(){
        booleanProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println((newValue?"enable":"disable")+" autopilot");
            flyMain.setAutoPilot(newValue);
        });

        displayScript.scriptProperty.bind(radioButtons.scriptProperty);
        displayScript.scriptProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                flyMain.runSimulator(newValue);
//                System.out.println(newValue);
            }
        });
        radioButtons.bindRadio(booleanProperty);
    }
}
