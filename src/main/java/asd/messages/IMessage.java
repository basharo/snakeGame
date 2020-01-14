package asd.messages;

import asd.entities.Gameboard.MessageType;

public interface IMessage {
	
	String getMessageString();

	MessageType getMsgType();


}
