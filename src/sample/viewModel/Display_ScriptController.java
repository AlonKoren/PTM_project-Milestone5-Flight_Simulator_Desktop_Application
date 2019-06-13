package sample.viewModel;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Display_ScriptController  extends Pane {

    Property<String> scriptProperty=new SimpleStringProperty();

    @FXML
    private TextArea script;

    public Display_ScriptController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../JavaFX Components/display_script.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initialize();
    }
    private void initialize(){
        scriptProperty.addListener((observable, oldValue, newValue) -> showScript(newValue));
    }

    private void showScript(String script)
    {
        this.script.appendText(script);
    }
}
