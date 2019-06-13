package sample.viewModel;

import javafx.beans.property.Property;
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
        displayScript.scriptProperty.bind(radioButtons.scriptProperty);
    }

}
