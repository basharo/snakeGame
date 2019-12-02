package sample.src.sample;

import javafx.scene.Group;
import javafx.stage.Stage;
import sample.src.sample.Gameboard.MessageType;

public class MessageFactory {


	
	public IMessage getMessage(MessageType messageType) {
		
		IMessage iMessage = null; //TODO: Make this better. Should include error handeling and never return null value.
		
		switch(messageType) {
		
		case EATENTAIL:
			iMessage = new EatenTailMessage();	
			break;
		case RANINTOWALL:
			iMessage = new WallMessage();	
			break;
		default:
			break;
		}
		
		return iMessage;
	}

}
 