package sample.view.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameGraphic extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent parent = FXMLLoader.load(getClass().getResource("User1.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

        Parent parent1 = FXMLLoader.load(getClass().getResource("User2.fxml"));
        Scene scene1 = new Scene(parent1);
        Stage stage1=new Stage();
        stage1.setScene(scene1);
        stage1.show();
    }

    public void Back() throws Exception {
        new MainMenu().start(stage);
    }
}
