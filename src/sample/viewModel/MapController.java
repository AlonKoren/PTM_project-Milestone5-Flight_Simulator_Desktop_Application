package sample.viewModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import view.MapDisplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapController extends Pane {

    @FXML
    MapDisplayer mapCanvas;


    public MapController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../JavaFX Components/map.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        readCSV();
    }

private static final String COMMA_DELIMITER=",";

    public void readCSV() {
        File fileCSV = new File("C:\\Users\\Alon Koren\\Desktop\\PTM Project\\javaFX\\src\\sample\\map-Honolulu.csv");
        double[][] coordinates;
        double max;
        double x;
        double y;
        double distance;
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(fileCSV)) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        records.forEach(strings -> {
//            System.out.println(strings.toString());
//        });
        x=Double.parseDouble(records.get(0).get(0));
        y=Double.parseDouble(records.get(0).get(1));
        distance=Double.parseDouble(records.get(1).get(0));
        List<List<String>> lists = records.subList(2, records.size());
        coordinates = lists.stream().map(strings -> strings.stream().mapToDouble(Double::parseDouble).toArray()).toArray(double[][]::new);
        max= Arrays.stream(coordinates).flatMapToDouble(Arrays::stream).max().getAsDouble();
        mapCanvas.setMapData(coordinates,max,x,y, distance);
        System.out.println(max);
        mapCanvas.redraw(max-100);
//        System.out.println("Gil say:");
//        for (double[] coordinate : coordinates) {
//            System.out.println(Arrays.toString(coordinate));
//        }

    }
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
            return values;
        }

    }
}