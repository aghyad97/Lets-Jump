package TemplateDesign;

import java.io.IOException;

import Game.Game;
import MessageBasedArchitecture.Proxy;
import MessageBasedArchitecture.msg;

public class HandHandling extends GameMessageHandling {

	@Override
	protected void sendMessage(Proxy p) {
		// TODO Auto-generated method stub
		msg handMessage = new msg((byte) 0b01, (byte) 0b100100); // // creating and sending message to led arduino
		try {
			p.send_msg(handMessage); // sends message to dispatcher to be sent to XBee
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // to start looking for a hand raise and flash hand LEDs
	}

	@Override
	protected long checking(Game g) {
		boolean hand = false;
		long timer = System.currentTimeMillis() + g.time; // adds 'time' seconds to the current time
		while (timer >= System.currentTimeMillis()) { // loops for 'time' seconds
			if (g.m.getID() == 0b01) { // checks if appropriate message was recieved
				if (g.m.getPayload() == 0b110000) {
					System.out.println("Nice hand!");
					hand = true;
					break; // exit out of timer loop
				}
			}
		}
		if (!hand)
			timer = -1; // if hand is false, player never hand, so set timer to -1
		g.m.resetMessage(); // resets message of the game
		return timer; // returns time at which player hand
	}

}
