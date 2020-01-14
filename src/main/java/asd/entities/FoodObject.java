package asd.entities;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;


public class FoodObject {
    private Rectangle food = new Rectangle(20, 20); //public um X/Y Koordinaten zu bekommen
    private double redPart;
    private double greenPart;
    private double bluePart;
    private Random rand;
    private Bounds fbound;
    private Group groupObject;
    private Stage stageObject;

    public FoodObject(Group groupObject, Stage stageObject) {
    	this.groupObject = groupObject;
    	this.stageObject = stageObject;	
    	rand = new Random();
    }

    double[] getColor() { // returned ein double Array mit den Farben fuer den Schwanz der Schlange, wird nacher von eat aufgerufen
        double[] colors = new double[3];
        colors[0] = redPart;
        colors[1] = greenPart;
        colors[2] = bluePart;
        return colors;

    }


    public void setFood() {
        groupObject.getChildren().remove(food);//um vorheriges Food verschwinden zu lassen

        food.setFill(Color.color(redPart = rand.nextDouble(), greenPart = rand.nextDouble(), bluePart = rand.nextDouble())); // hier werden zufaellige Farben fuer das Food (und damit auch den Tail) uebergeben
        food.relocate(rand.nextInt((int) stageObject.getWidth() - 50), rand.nextInt((int) stageObject.getHeight() - 50)); // Random Location mit Abstand vom Rand jeweils 40
        groupObject.getChildren().add(food);
        fbound = food.getBoundsInParent();

    }

    public Bounds getBound() {
        return fbound;
    }


}
