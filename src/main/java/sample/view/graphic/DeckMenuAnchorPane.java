package sample.view.graphic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.controller.DeleteDeck;
import sample.controller.LoginController;
import sample.controller.UserLogined;
import sample.model.Deck;

import java.util.ArrayList;

public class DeckMenuAnchorPane extends AnchorPane {
    private static ArrayList<DeckMenuAnchorPane> allDecks = new ArrayList<>();


    public DeckMenuAnchorPane(int number, Deck deck) {

        this.setTranslateY(number * 30);
        this.setTranslateX(0);
        this.setPrefWidth(1500);
        this.setPrefHeight(40);
        this.setStyle("-fx-background-color: #e4dcf1");

        Label name = new Label();
        name.setText(deck.getName());
        name.setTranslateX(30);
        name.setTranslateY(0);
        name.setFont(new Font("Cambria", 32));
        name.setTextFill(Color.web("#83d0c9", 0.8));
        this.getChildren().add(name);

        Button delete = new Button();
        delete.setText("Delete");
        delete.setTranslateX(1398);
        delete.setTranslateY(0);
        delete.setId("delete" + deck.getName());
        delete.setPrefWidth(50);
        delete.setPrefHeight(38);
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DeleteDeck.deleteDeck(deck, UserLogined.user);
                try {
                    DeckMenu.deleteDeckClicked();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.getChildren().add(delete);


        Button edit = new Button();
        edit.setText("Edit");
        edit.setTranslateX(1449);
        edit.setTranslateY(0);
        edit.setId("edit" + deck.getName());
        edit.setPrefWidth(37);
        edit.setPrefHeight(38);
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UserLogined.deck=deck;
                try {
                    DeckMenu.goToDeckShow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.getChildren().add(edit);

        allDecks.add(this);
    }

    public static ArrayList<DeckMenuAnchorPane> getAllDecks() {
        return allDecks;
    }
}
