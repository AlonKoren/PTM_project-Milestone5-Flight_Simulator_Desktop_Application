package sample.viewModel;

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
//    @FXML
//    private TextArea script;
//
//    @FXML
//    private Circle smallCircle;
//    double originalSceneX, originalSceneY;
//    double originalTranslateX, originalTranslateY;

    static final String folderPath="../PTM2 Project/scripts";


    public SampleController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../JavaFX Components/full_app.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

//    public void openFileDialogue(MouseEvent mouseEvent)
//    {
//        System.out.println("hello");
//        FileChooser chooser=new FileChooser();
//        chooser.setInitialDirectory(new File(folderPath));
//        File file = chooser.showOpenDialog(null);
//        if(file!=null)
//        {
//            try
//            {
//                Stream<String> stream = Files.lines(file.toPath());
//                StringBuilder sb = new StringBuilder();
//                stream.forEach(str -> sb.append(str).append("\n"));
//                showScript(sb.toString());
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void showScript(String script)
//    {
//        this.script.appendText(script);
//    }
//
//    public void circleOnMouseDraggedEventHandler(MouseEvent mouseEvent) {
//        System.out.println("circleOnMouseDraggedEventHandler");
//    }
//
//    public void circleOnMousePressedEventHandler(MouseEvent mouseEvent) {
//        System.out.println("circleOnMousePressedEventHandler");
//
//    }
//
//    public void circleOnMouseReleasedEventHandler(MouseEvent mouseEvent) {
//        System.out.println("circleOnMouseReleasedEventHandler");
//
//    }
}
