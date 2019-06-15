package sample.viewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Ip_PortController extends Pane
{
    @FXML
    TextField ipTextField;
    @FXML
    TextField portTextField;
    @FXML
    Button accept;

    StringProperty ipProperty,portProperty;

    public Ip_PortController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../JavaFX Components/ip-port.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        ipProperty=new SimpleStringProperty();
        portProperty=new SimpleStringProperty();

    }

    public void bind(StringProperty ipProperty,StringProperty portProperty){
//        System.out.println("bind");
        ipProperty.bind(this.ipProperty);
        portProperty.bind(this.portProperty);
    }

    @FXML
    public void sendData(MouseEvent mouseEvent)
    {
//        System.out.println("send data");
        ipProperty.setValue(ipTextField.getText());
        portProperty.setValue(portTextField.getText());
    }

    @FXML
    public void closeWindow(MouseEvent sendData)
    {
//        System.out.println("closeWindow");
        ((Stage)this.getScene().getWindow()).close();
    }
}
