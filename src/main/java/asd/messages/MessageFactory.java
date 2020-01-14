package asd.messages;

import asd.entities.Gameboard.MessageType;

public class MessageFactory {


	
	public IMessage getMessage(MessageType messageType) throws MessageTypeNotSupportedException {

			switch (messageType) {

				case EATENTAIL:
					return new EatenTailMessage();
				case RANINTOWALL:
					return new WallMessage();
				default:
					throw new MessageTypeNotSupportedException("Message type not supported");
			}
		}

}
 