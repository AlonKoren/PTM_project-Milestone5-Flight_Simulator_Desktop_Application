package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.viewModel.SampleController;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        SampleController controller=new SampleController();
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(controller, 1123, 456));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
