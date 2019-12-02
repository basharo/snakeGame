package sample.src.sample;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;



//Labels
public class Gameboard {
	private Group group;
	private Stage stage;
	private MessageFactory messageFactory= new MessageFactory();

    enum MessageType 
    { 
        EATENTAIL, RANINTOWALL;
    } 

    public Gameboard(Group group, Stage stage) {
    	this.group = group;
    	this.stage = stage;   	
    }

    void setStartInfo() {
    	Label startInfo = new Label("\nTo move press w/a/s/d. \nDon't hit the walls!");
    	startInfo.setFont(new Font("Calibri",50));
    	startInfo.setTextFill(Color.BLACK);

        group.getChildren().add(startInfo);
    }

    public void setDeathTouchWall(Score score) {
    	IMessage wallMessage = messageFactory.getMessage(MessageType.RANINTOWALL);
    	setMessageLabel(score, wallMessage);

    }

    public void setDeathTouchTail(Score score) {
    	IMessage eatenTailMessage = messageFactory.getMessage(MessageType.EATENTAIL);
    	setMessageLabel(score, eatenTailMessage);
  
    }
    
	public void setMessageLabel(Score score, IMessage statusMessage) {
        Label statusLabel = new Label(statusMessage.getMessageString()
                + "\nPress R for respawn" + "\nScore: " + score.getScore());
        statusLabel.setFont(new Font("Calibri",80));
        statusLabel.setTextFill(Color.BLACK);
        group.getChildren().clear();
        statusLabel.relocate(200, stage.getHeight()/2-200);
        group.getChildren().add(statusLabel);
	}

}
