import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        HomeworkUI homeworkUI = new HomeworkUI();

        Scene scene = new Scene(homeworkUI.getLayout(), 600, 400);

        primaryStage.setTitle("Homework Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
