package sample.viewModel;

import alon.flightsim.FlyMain;
import alon.flightsim.client.Client;
import alon.flightsim.client.SimpleClient;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class SampleController extends SplitPane
{
    final Client client=new SimpleClient();

    Property<Boolean> booleanProperty=new SimpleBooleanProperty();
    Property<Boolean> enableProperty =new SimpleObjectProperty<>(false);

    @FXML
    MapController leftSide;

    @FXML
    Right_SideController rightSide;


    public SampleController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../JavaFX Components/full_app.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        bind();
    }

    public void bind(){
        leftSide.bind(client,enableProperty);
        rightSide.setClient(client);
        enableProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue) rightSide.enableAll();
        });
    }
}
