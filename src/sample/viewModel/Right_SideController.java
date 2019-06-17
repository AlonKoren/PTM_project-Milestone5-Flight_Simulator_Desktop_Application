package sample.viewModel;

import alon.flightsim.FlyMain;
import alon.flightsim.client.Client;
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
    Property<Boolean> autopilotProperty=new SimpleBooleanProperty();
    Property<Boolean> manualProperty=new SimpleBooleanProperty();


    public Right_SideController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../JavaFX Components/right_side.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        joystick.setDisable(true);
        bind();
    }
    private void bind(){
        autopilotProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue)
            {
                System.out.println("enable" + " autopilot");
                joystick.setDisable(true);
                flyMain.setAutoPilot(true);
            }
        });
        manualProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue)
            {
                System.out.println(("disable")+" autopilot");
                joystick.setDisable(false);
                joystick.getCurrentValues();
                flyMain.setAutoPilot(false);
            }
        });

        displayScript.scriptProperty.bind(radioButtons.scriptProperty);
        displayScript.scriptProperty.addListener((observable, oldValue, newValue) -> {
            flyMain.runSimulator(newValue);
//                System.out.println(newValue);
        });
        radioButtons.bindRadio(autopilotProperty,manualProperty);
    }
    public void setClient(Client client) {
        joystick.setClient(client);
    }
}
