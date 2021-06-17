package sample.view.graphic;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.controller.UserLogined;
import sample.model.Deck;
import sample.model.User;

import java.util.ArrayList;

public class DeckMenu extends Application {
    private static Stage stage;
    @FXML
    private AnchorPane innerAnchorPane;
    @FXML
    private Text username;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent parent = FXMLLoader.load(getClass().getResource("DeckMenu.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        username.setText(UserLogined.user.getUsername());
        innerAnchorPane.getChildren().addAll(creatScorePane());
    }

    public ArrayList<DeckMenuAnchorPane> creatScorePane(){
        int i=1;
        for (Deck deck: UserLogined.user.allDecks){
            new DeckMenuAnchorPane(i,deck);
        }
        return DeckMenuAnchorPane.getAllDecks();
    }

    public void AddNewDeckClicked(){

    }
    public void backToMainMenuClicked() throws Exception {
        new MainMenu().start(stage);
    }
}
