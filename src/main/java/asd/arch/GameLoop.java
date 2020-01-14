package asd.arch;

import asd.entities.FoodObject;
import asd.entities.Gameboard;
import asd.entities.Score;
import asd.entities.Snake;
import asd.h2_db.H2DBCreation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class GameLoop extends Application {

    private static File splashFile = new File("src/media/splash.mp4");
    private static Media splashMedia = new Media(splashFile.toURI().toString());
    private static MediaPlayer splashPlayer = new MediaPlayer(splashMedia);
    private static MediaView splashView = new MediaView(splashPlayer);
    private static File ingamemusicFile = new File("src/media/sound/music/ingame2.mp3");
    private static Media ingamemusicMedia = new Media(ingamemusicFile.toURI().toString());
    private static MediaPlayer ingamemusicPlayer = new MediaPlayer(ingamemusicMedia);
    private static File gameovermusicFile = new File("src/media/sound/music/gameover1.mp3");
    private static Media gameovermusicMedia = new Media(gameovermusicFile.toURI().toString());
    private static MediaPlayer gameovermusicPlayer = new MediaPlayer(gameovermusicMedia);
    private static File eatsoundFile = new File("src/media/sound/eat2.mp3");
    private static Media eatsoundMedia = new Media(eatsoundFile.toURI().toString());
    private static MediaPlayer eatsoundPlayer = new MediaPlayer(eatsoundMedia);
    private static File deathsoundFile = new File("src/media/sound/death1.mp3");
    private static Media deathsoundMedia = new Media(deathsoundFile.toURI().toString());
    private static MediaPlayer deathsoundPlayer = new MediaPlayer(deathsoundMedia);
    private Group root = new Group();
    private Pane backgroundPane = new Pane();
    private Group splashscreen = new Group();
    private Image imgSource;
    private BackgroundImage backgroundImage;
    private Background backgroundView;
    private long lastUpdate = 0;
    private static final int SPLASH_WIDTH = 1000;
    private static final int SPLASH_HEIGHT = 500;

    private static H2DBCreation db33 = new H2DBCreation();

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GameLoop.class);

    public static void restartIngamemusic() throws Exception { // Startet Ingame Musik von vorne
        ingamemusicPlayer.seek(Duration.ZERO);
        ingamemusicPlayer.play();
        logger.info("restart ingame");
        db33.insertIntoTable("GameLoop","restart ingame");

    }

    public static void stopIngamemusic() {
        ingamemusicPlayer.stop();
        logger.info("stop ingame");
    }

    public static void restartGameovermusic() {
        gameovermusicPlayer.seek(Duration.ZERO);
        gameovermusicPlayer.play();
        logger.info("Game over");
    }

    public static void stopGameovermusic() {
        gameovermusicPlayer.stop();
        logger.info("stop game");
    }

    public static void playEatsound() {
        eatsoundPlayer.seek(Duration.ZERO);
        eatsoundPlayer.play();
        logger.info("collision - play sound");
    }

    public static void playDeathsound() {
        deathsoundPlayer.seek(Duration.ZERO);
        deathsoundPlayer.play();
    }


    public static void main(String[] args) {
        launch(args);

    }

    @Override

    public void start(final Stage primaryStage) throws Exception {
        final AnimationTimer timer;
        final int offset = 21;
        final Gameboard gameboard;
        final Control control = new Control();
        final Snake snake;
        final FoodObject food;
        final Score score = new Score(root);
        final FadeTransition fadeblacktotransparent;

        final Scene scene = new Scene(backgroundPane, primaryStage.getWidth(), primaryStage.getHeight(),
                Color.DARKGREEN);

        logger.debug("Start of setup");
        logger.info("Start of setup");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        logger.info("primary Stage width: " + bounds.getWidth());
        logger.info("primary Stage heigth: " + bounds.getHeight());

        /** Code moved to setBackground() */

        //setBackground();

        gameboard = new Gameboard(root, primaryStage);
        snake = new Snake(root, primaryStage);
        food = new FoodObject(root, primaryStage);
        food.setFood();

        fadeblacktotransparent = setFadeTransition(primaryStage);

        /** Code moved to setFadeTransition() */

        Scene intro = setSplashScreen(primaryStage);

        /** Code moved to setSplashScreen() */

        primaryStage.setScene(intro);

        primaryStage.setTitle("Rainbow Snake");

        gameboard.setStartInfo();

        primaryStage.show();
        logger.info("show primary Stage");
        splashPlayer.play();
        logger.info("splash player play");

        ingamemusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    control.keyHandler(keyEvent, snake, root, food, score, primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (now - lastUpdate >= snake.getframeDelay()) {

                    int dx = 0, dy = 0;

                    snake.collision(food, root, food.getBound(), score, control, primaryStage, gameboard);

                    if (control.getgoUp())
                        dy += -offset; // offset="speed"
                    else if (control.getgoDown())
                        dy += offset;
                    else if (control.getgoRight())
                        dx += offset;
                    else if (control.getgoLeft())
                        dx += -offset;
                    snake.moveSnake(dx, dy, primaryStage);

                    lastUpdate = now;

                }
            }
        };
        splashPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                primaryStage.setScene(scene);
                fadeblacktotransparent.play();
                logger.info("start animation timer after intro video");
                timer.start();
                try {
                    restartIngamemusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setBackground() {

        imgSource = new Image(".\\media\\image\\grassTile.png");
        backgroundImage = new BackgroundImage(imgSource, BackgroundRepeat.REPEAT.REPEAT, BackgroundRepeat.REPEAT.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        backgroundView = new Background(backgroundImage);
        backgroundPane.setBackground(backgroundView);

        backgroundPane.getChildren().add(root);
    }

    private Scene setSplashScreen(Stage primaryStage) {

        Scene intro = new Scene(splashscreen, primaryStage.getWidth(), primaryStage.getHeight());
        splashscreen.getChildren().add(splashView);
        splashView.setFitHeight(SPLASH_HEIGHT);
        splashView.setFitWidth(SPLASH_WIDTH);
        intro.setFill(Color.BLACK);
        splashView.setX(((primaryStage.getWidth() + (primaryStage.getHeight() / 2)) - SPLASH_WIDTH) / 2);
        splashView.setY((primaryStage.getHeight() - SPLASH_HEIGHT) / 2);

        return intro;

    }

    private FadeTransition setFadeTransition(Stage primaryStage) {

        Rectangle blackrect = new Rectangle();
        blackrect.setFill(Color.BLACK);
        blackrect.setHeight(primaryStage.getHeight());
        blackrect.setWidth(primaryStage.getWidth());
        FadeTransition fadeblacktotransparent = new FadeTransition(Duration.millis(700), blackrect);

        fadeblacktotransparent.setFromValue(1.0);
        fadeblacktotransparent.setToValue(0.0);
        root.getChildren().add(blackrect);

        return fadeblacktotransparent;

    }

}
