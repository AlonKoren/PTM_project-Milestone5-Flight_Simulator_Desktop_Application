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
        primaryStage.setTitle("Alon Koren");
        primaryStage.setScene(new Scene(controller, 1123, 480));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
