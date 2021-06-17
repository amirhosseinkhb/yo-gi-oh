package sample.controller;

import sample.model.Deck;
import sample.model.User;

public class DeckCreat {
    public static String creat(String name, User user) {
        boolean exist = false;

        for (Deck deck : user.allDecks) {
            if (deck.getName().equals(name)) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            Deck deck1 = new Deck(user, name);
            user.allDecks.add(deck1);
            return "deck created successfully!";
        }else {
            return "deck with name +" + name + " already exists";

        }
    }
}
