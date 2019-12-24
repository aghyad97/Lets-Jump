package TemplateDesign;

import java.io.IOException;

import Game.Game;
import MessageBasedArchitecture.Proxy;
import MessageBasedArchitecture.msg;

public class JumpHandling extends GameMessageHandling {

	@Override
	protected long checking(Game g) {
		boolean jumped = false;
    	long timer = System.currentTimeMillis() + g.time; // adds 'time' seconds to the current time
        while (timer >= System.currentTimeMillis()) { // loops for 'time' seconds
        	if (g.m.getID() == 0b00) { // checks if appropriate message was recieved
                if(g.m.getPayload() == 0b110100)    {     
		                System.out.println("Nice jump!");
		                jumped = true; 
		                break; // exit out of timer loop
        	}
        	}
    }
        if (!jumped)
        	timer = -1; // if jumped is false, player never jumped, so set timer to -1
        g.m.resetMessage();
        return timer;
	}

	@Override
	protected void sendMessage(Proxy p) {
		// TODO Auto-generated method stub
		msg jumpMessage = new msg((byte) 0b00, (byte) 0b100000); // creating and sending message to led arduino
		
        try {
			p.send_msg(jumpMessage); // sends message to dispatcher to be sent to XBee
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // to start looking for a jump and flash LED strip
	}

}
