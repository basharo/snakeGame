package asd.entities;

import asd.messages.IMessage;
import asd.messages.MessageFactory;
import asd.messages.MessageTypeNotSupportedException;
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

    public enum MessageType
    {
        EATENTAIL, RANINTOWALL, NonExistant;
    }

    public Gameboard(Group group, Stage stage) {
    	this.group = group;
    	this.stage = stage;   	
    }

    public void setStartInfo() {
    	Label startInfo = new Label("\nTo move press w/a/s/d. \nDon't hit the walls!");
    	startInfo.setFont(new Font("Calibri",50));
    	startInfo.setTextFill(Color.BLACK);

        group.getChildren().add(startInfo);
    }

    public void setDeathTouchWall(Score score) {
        IMessage wallMessage = null;
        try {
            wallMessage = messageFactory.getMessage(MessageType.RANINTOWALL);
        } catch (MessageTypeNotSupportedException e) {
            e.printStackTrace();
        }
        setMessageLabel(score, wallMessage);

    }

    public void setDeathTouchTail(Score score) {
        IMessage eatenTailMessage = null;
        try {
            eatenTailMessage = messageFactory.getMessage(MessageType.EATENTAIL);
        } catch (MessageTypeNotSupportedException e) {
            e.printStackTrace();
        }
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
