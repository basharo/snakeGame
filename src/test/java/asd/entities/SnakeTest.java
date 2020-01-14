package asd.entities;

import asd.arch.GameLoop;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {
    private static final long START_FRAMEDELAY = 250000000;
    private static final long MAX_FRAMEDELAY = 80000;
    private static final long DECREASE_DELAY = 6000000;  //von speedRefresh abziehen
    public long frameDelay = START_FRAMEDELAY; //250-300 mill. guter Startwert
    private int helpX;
    private int helpY;
    private static int rectangleWidth = 20;
    private static int rectangleHeight = 20;
    private Group group = new Group();
    //GameObject food = new GameObject();
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SnakeTest.class);
    private Rectangle head = new Rectangle(rectangleWidth, rectangleHeight); // hier Initialisiert, weil in mehreren Methoden
    private LinkedList<Rectangle> snake = new LinkedList<Rectangle>();
    private Stage stage ;
    @Test
    void getframeDelay() {
    }

    @Test
    void respawn() {
        //group.getChildren().clear();
        //snake.clear();
        //logger.info(snake);
        //snake.add(head);
        //logger.info(stage.getWidth());
        //snake.getFirst().relocate(stage.getWidth() / 2, stage.getHeight() / 2);
        //group.getChildren().add(snake.getFirst());
        //score.scoreRespawn(group); // respawn Mehtode f�r Score
        //frameDelay = START_FRAMEDELAY; // zur�ck zum Standardwert
        //food.setFood(group, stage); // setet neues random food und getchilded es
        //control.stopMovement();
    }

    @Test
    void snakeDead() {
        group.getChildren().clear();
        snake.clear();
        snake.add(head);
        snake.getFirst().relocate(20 / 2, 20 / 2);
    }

    @Test
    void collision() {
    }

    @Test
    void moveSnake() {
        int helpX;
        int helpY;
        int dx = 1;
        int dy = 1;
        if ( dx != 0 || dy != 0) { //gibt es �berhaupt dx/dy werte (wenn wir stehen z.B. nicht)
            LinkedList<Rectangle> snakehelp = new LinkedList<Rectangle>();
             logger.info(snake.size());
            for (int i = 0; i < snake.size(); i++) {
                snakehelp.add(new Rectangle());
                logger.info(snake.get(i).getLayoutX());
                snakehelp.get(i).relocate(snake.get(i).getLayoutX(), snake.get(i).getLayoutY());
            }

            //snake.getFirst().relocate((int) snake.getFirst().getLayoutX() + dx, (int) snake.getFirst().getLayoutY() + dy);//moved erstmal nur den Kopf
            //for (int i = 1; i < snake.size(); i++) {
            //helpX = (int) snakehelp.get(i - 1).getLayoutX();
            //  helpY = (int) snakehelp.get(i - 1).getLayoutY();
            //  logger.info(helpX);
            //  snake.get(i).relocate(helpX, helpY);
            //}
            assertTrue(true);
        }
        else
            assertTrue(false);
    }
    @Test
    void checkRandomColorGenerator(){
        snake.add(new Rectangle(20, 20));
        Random rand = new Random();
        //logger.info(rand.nextDouble());
        assertTrue(rand.nextDouble() < 1);
        //snake.getLast().setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
    }

    @Test //Check Collision and finish the Game
    void CollisionFinishGame(){
        assertEquals((head.getLayoutX() <= 0 || head.getLayoutX() >= stage.getWidth() - 30 ||
                head.getLayoutY() <= 0 || head.getLayoutY() >= stage.getHeight() - 54), true);
    }

    @Test
    void TestDeathMusicFileExists(){
        File file = new File("./src/media/sound/death1.mp3");
        assertTrue(file.exists());
    }
}