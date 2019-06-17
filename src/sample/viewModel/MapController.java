package sample.viewModel;

import alon.flightsim.client.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Model.PTMClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MapController extends Pane {

    @FXML
    MapDisplayer mapCanvas;

    Client client;

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

    public void bind(Client client){
        this.client=client;
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
            this.client.connect(ip,Integer.parseInt(port));
//            System.out.println("sim:"+ip+":"+port);

            mapCanvas.connectToServer(this.client);

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
            try {
                PTMClient ptmClient=new PTMClient(ip,Integer.parseInt(port));
                String ans = ptmClient.sendMatrix(mapCanvas.getCoordinates(), (int) mapCanvas.planeX, (int) mapCanvas.planeY, mapCanvas.destX, mapCanvas.destY);
                ptmClient.close();
                System.out.println(ans);
                mapCanvas.showPoints(ans);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("ptm:"+ip+":"+port);
        });
    }
}