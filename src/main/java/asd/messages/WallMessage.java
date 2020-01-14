package asd.messages;

import asd.entities.Gameboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class WallMessage implements IMessage{
	
	 private Random rand = new Random();
	 private Collection<String> touchWall = initTouchWall(); //Maybe this should go back to GameBoard

    private Collection<String> initTouchWall() {
    	touchWall = new ArrayList<String>();
        touchWall.add("Stop touching the wall \nas if it's your boyfriend.....");
        touchWall.add("You never touch me \nin the way you touched \nthat wall :*(");
        touchWall.add("Walls are your favorite \nthing, huh?");
        touchWall.add("The wall you touched is solid,\n no comin through");
        touchWall.add("Wall:1, You:0");
        touchWall.add("Mimimimi you dead!");
        touchWall.add("No one would survive this...");
        touchWall.add("You colored the wall, \nwhat a nice thing to do");
        touchWall.add("No touchy touchy le wall \nmi Friend! ");
        return touchWall;
    }
	 
    @Override
	 public String getMessageString() {
		 
		 return (String) touchWall.toArray()[rand.nextInt(9)];
	 }

    @Override
    public Gameboard.MessageType getMsgType() {
        return Gameboard.MessageType.RANINTOWALL;
    }


}
