package sample.viewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.MapDisplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MapController extends Pane {

    @FXML
    MapDisplayer mapCanvas;

    private static final String folderPath="./src/sample/CSV/";

    public MapController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../JavaFX Components/map.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
//        readCSV();
    }

    public void readCSV(File fileCSV) {
//        File fileCSV = new File("./src/sample/map-Honolulu.csv");
        try {
            mapCanvas.setMapData(fileCSV);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mapCanvas.redraw();
        mapCanvas.movePlane(10,10);
    }

    @FXML
    public void openFileDialogue(MouseEvent mouseEvent)
    {
        System.out.println("Choose CSV file");
        FileChooser chooser=new FileChooser();
        chooser.setInitialDirectory(new File(folderPath));
        File file = chooser.showOpenDialog(null);
        if(file!=null)
        {
            readCSV(file);
        }
    }
    @FXML
    public void connectToServer(MouseEvent mouseEvent)
    {
        Ip_PortController root=new Ip_PortController();
        Stage stage = new Stage();
        stage.setTitle("Enter IP and Port");
        stage.setScene(new Scene(root));
        stage.show();

        StringProperty ipProperty = new SimpleStringProperty();
        StringProperty portProperty=new SimpleStringProperty();
        root.bind(ipProperty,portProperty);
        portProperty.addListener((observable, oldValue, newValue) -> {
            String ip=ipProperty.get();
            String port=newValue;
            //TODO
            System.out.println("sim:"+ip+":"+port);

        });
    }

    public void selectDestination(MouseEvent mouseEvent)
    {
        //System.out.println(mouseEvent.getX()+","+mouseEvent.getY());
        mapCanvas.markDestByMouse(mouseEvent.getX(),mouseEvent.getY());
    }

    public void calcPath(MouseEvent mouseEvent)
    {
        Ip_PortController root=new Ip_PortController();
        Stage stage = new Stage();
        stage.setTitle("Enter IP and Port");
        stage.setScene(new Scene(root));
        stage.show();

        StringProperty ipProperty = new SimpleStringProperty();
        StringProperty portProperty=new SimpleStringProperty();
        root.bind(ipProperty,portProperty);
        portProperty.addListener((observable, oldValue, newValue) -> {
            String ip=ipProperty.get();
            String port=newValue;
            //TODO
            System.out.println("ptm:"+ip+":"+port);
        });
    }
}