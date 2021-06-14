package sample.view.graphic;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class ShopCard extends Rectangle {
    private static ArrayList<ShopCard> allCards=new ArrayList<>();
    private int x1;
    private int y1;
    public ShopCard(int x, int y, Image image){
        super(x,y,325,460);
        this.x1=x;
        this.y1=y;
        this.setFill(new ImagePattern(image));
        allCards.add(this);
    }

    public static ArrayList<ShopCard> getAllCards() {
        return allCards;
    }
}
