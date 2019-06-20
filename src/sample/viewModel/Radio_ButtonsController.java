package sample.viewModel;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class Radio_ButtonsController extends Pane {

    private static final String folderPath="../PTM2 Project/scripts";

    Property<String> scriptProperty=new SimpleStringProperty();
    Property<Boolean> autopilotProperty=new SimpleBooleanProperty();
    Property<Boolean> manualProperty=new SimpleBooleanProperty();

    public Radio_ButtonsController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../JavaFX Components/radio_buttons.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


    }
    public void openFileDialogue(MouseEvent mouseEvent)
    {
        System.out.println("hello");
        FileChooser chooser=new FileChooser();
        chooser.setInitialDirectory(new File(folderPath));
        File file = chooser.showOpenDialog(null);
        if(file!=null)
        {
            try
            {
                Stream<String> stream = Files.lines(file.toPath());
                StringBuilder sb = new StringBuilder();
                stream.forEach(str -> sb.append(str).append("\n"));
                showScript(sb.toString());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void showScript(String script)
    {
        scriptProperty.setValue(script);
//        this.script.appendText(script);
    }

    public String getScriptProperty() {
        return scriptProperty.getValue();
    }

    public Property<String> scriptPropertyProperty() {
        return scriptProperty;
    }

    public void bindRadio(Property<Boolean> autopilotProperty,Property<Boolean> manualProperty)
    {
        autopilotProperty.bind(this.autopilotProperty);
        manualProperty.bind(this.manualProperty);
    }

    @FXML
    void autoPilotEnable(MouseEvent mouseEventvent)
    {
        this.autopilotProperty.setValue(true);
        this.manualProperty.setValue(false);
    }
    @FXML
    void autoPilotDisable(MouseEvent mouseEventvent)
    {
        this.manualProperty.setValue(true);
        this.autopilotProperty.setValue(false);
    }

    public void enableAll() {
        this.setDisable(false);
    }
}
