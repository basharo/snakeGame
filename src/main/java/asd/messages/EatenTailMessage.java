package asd.messages;

import asd.entities.Gameboard.MessageType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;


public class EatenTailMessage implements IMessage {

	private Random rand = new Random();
	private Collection<String> touchTail= initTouchTail(); //Maybe this should go back to GameBoard
	
    private Collection<String> initTouchTail() {
    	touchTail = new ArrayList<String>();
    	touchTail.add("Touching yourself huh?");
		touchTail.add("Snake ate herself in fury");
		touchTail.add("Not your best day is it?....");
		touchTail.add("Well...you tried...");
		touchTail.add("Stop trying...");
		touchTail.add("You touched that ass (tail..)!");
		return touchTail;
	}
    
    @Override
    public String getMessageString() {
    	return (String) touchTail.toArray()[rand.nextInt(6)];
    }

	@Override
    public MessageType getMsgType(){
    	return MessageType.EATENTAIL;
	}


}
