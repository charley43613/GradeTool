package root;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/root/GradeForm.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/root/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Grade Tool");
            primaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }




}
