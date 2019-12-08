package asd.entities;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.LinkedList;

import asd.arch.Control;
import asd.arch.GameLoop;

public class Snake {


	private static final long START_FRAMEDELAY = 250000000;
	private static final long MAX_FRAMEDELAY = 80000;
	private static final long DECREASE_DELAY = 6000000;  //von speedRefresh abziehen
    public long frameDelay = START_FRAMEDELAY; //250-300 mill. guter Startwert
    private int helpX;
    private int helpY;
    private static int rectangleWidth = 20;
    private static int rectangleHeight = 20;

    //GameObject food = new GameObject();
    private Rectangle head = new Rectangle(20, 20); // hier Initialisiert, weil in mehreren Methoden
    private LinkedList<Rectangle> snake = new LinkedList<Rectangle>();

    public Snake(Group group, Stage stage) {
        snake.add(head);
        snake.getFirst().relocate(stage.getWidth() / 2, stage.getHeight() / 2);
        group.getChildren().add(snake.getFirst());

    }

    public long getframeDelay() {
        return frameDelay;
    }


    public void respawn(Group group, FoodObject food, Score score, Stage stage, Control control) {
        group.getChildren().clear();
        snake.clear();

        snake.add(head);
        snake.getFirst().relocate(stage.getWidth() / 2, stage.getHeight() / 2);
        group.getChildren().add(snake.getFirst());
        food.setFood(); // setet neues random food und getchilded es
        score.scoreRespawn(group); // respawn Mehtode f�r Score
        frameDelay = START_FRAMEDELAY; // zur�ck zum Standardwert


        control.stopMovement();

    }

    public void snakeDead(Group group, Control control, Stage stage) {
        //Last Minute - wird gebraucht um Score nicht zu fr�h zu l�schen (�berlegung nur respawn zu verwenden mit dieser implementierung fehlgeschlagen)

        group.getChildren().clear();
        snake.clear();
        snake.add(head);
        snake.getFirst().relocate(stage.getWidth() / 2, stage.getHeight() / 2);

        frameDelay = START_FRAMEDELAY; // zur�ck zum Standardwert
        control.stopMovement();

    }


    private void eat(Group group, Score score, FoodObject food) {//added ein tail rectangle, �bernimmt color von food,erh�ht score um 1, macht schneller
        snake.add(new Rectangle(20, 20));
        snake.getLast().setFill(Color.color(food.getColor()[0], food.getColor()[1], food.getColor()[2])); //holt sich aus deathsoundMedia GameObject die Color von Food f�r sein Tail
        group.getChildren().add(snake.getLast()); //bringt den tail auf die Szene
        score.upScoreValue(); // added +1 zu scoreValue

        if (frameDelay >= MAX_FRAMEDELAY) { //maximale Grenze sonst wirds zu schnell
            frameDelay -= DECREASE_DELAY;
            System.out.println(frameDelay);
        }

    }

    public void collision(FoodObject food, Group group, Bounds foodBound, Score score, Control control, Stage stage, Gameboard gameboard) { //gameobject sind obstacles so wie Food, Boundarys f�r Collisions
        Bounds headBox = head.getBoundsInParent(); // erstellt eine Boundary um den Snakekopf


        if (headBox.intersects(foodBound)) {//�berpr�fung Collision Head mit Food Boundary
            eat(group, score, food);
            food.setFood();
            GameLoop.playEatsound();
        }

        if (head.getLayoutX() <= 0 || head.getLayoutX() >= stage.getWidth() - 30 || // �berpr�fung ob Head den Rand trifft
                head.getLayoutY() <= 0 || head.getLayoutY() >= stage.getHeight() - 54) {
            snakeDead(group, control, stage);
            gameboard.setDeathTouchWall(score);
            GameLoop.playDeathsound();
            //GameLoop.stopIngamemusic();
            //GameLoop.restartGameovermusic();
        }


        for (int i = 1; i < this.snake.size(); i++) { //�berpr�fung Snake beisst sich in den oasch
            if (headBox.intersects(this.snake.get(i).getBoundsInParent())) {
                System.err.println("Dead");
                snakeDead(group, control, stage);
                gameboard.setDeathTouchTail(score);
                GameLoop.playDeathsound();
                //GameLoop.stopIngamemusic();
                //GameLoop.restartGameovermusic();
            }
        }
    }


    public void moveSnake(int dx, int dy, Stage stage) { //dx bzw dy ist jeweils + oder - speed, war zuvor 5
        int helpX;
        int helpY;
        if (dx != 0 || dy != 0) { //gibt es �berhaupt dx/dy werte (wenn wir stehen z.B. nicht)
            LinkedList<Rectangle> snakehelp = new LinkedList<Rectangle>();

            for (int i = 0; i < snake.size(); i++) {

                snakehelp.add(new Rectangle());

                snakehelp.get(i).relocate(snake.get(i).getLayoutX(), snake.get(i).getLayoutY());
            }

            snake.getFirst().relocate((int) snake.getFirst().getLayoutX() + dx, (int) snake.getFirst().getLayoutY() + dy);//moved erstmal nur den Kopf


            for (int i = 1; i < snake.size(); i++) {

                helpX = (int) snakehelp.get(i - 1).getLayoutX();
                helpY = (int) snakehelp.get(i - 1).getLayoutY();
                snake.get(i).relocate(helpX, helpY);
            }
        }
    }
}
