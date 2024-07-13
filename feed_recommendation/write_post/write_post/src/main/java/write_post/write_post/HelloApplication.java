package write_post.write_post;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(root, 600, 600);
            stage.setScene(scene);
            stage.setTitle("Write Post Application");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


