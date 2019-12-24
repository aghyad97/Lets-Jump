package TemplateDesign;

import Game.Game;
import MessageBasedArchitecture.*;

public abstract class GameMessageHandling {
	public final boolean Recipe(Proxy p, Game g) { // recipe for template design
		sendMessage(p); // sends appropriate message to dispatcher
		long timer = checking(g); // checks from arduino if player has done appropriate action, and returns the time at which the action was done
		
		if (timer == -1) 
			return false; // if timer is -1, than player failed to do required action
		
		int roundScore = calculateRoundScore(g.time, timer);
        System.out.println("Score earned this round = " + roundScore); // displays current round score
        g.score += roundScore; // increments current round score to total score
		return true;
	}

	private final int calculateRoundScore(int timeToReact, long timer) { // if reacted in the first half of the given time, get full score
		   // if not, get (1 - ((time spent) / (time to react))) * 100 as score
		long timeSpent = System.currentTimeMillis() - (timer - timeToReact); // timer holds the (system time at round
																				// start) + (timeToReact)
		// subtracting timeToReact from timer gives us time at round start
		// and finally (current time) - (round start time) gives us time spent
		// to jump/raise hand
		int roundScore;
		float timeRatio = ((float) timeSpent) / timeToReact;

		roundScore = (int) ((1 - timeRatio) * 100.0);
		if (roundScore > 80)
			return 100;
		return roundScore;
	}
	
	protected abstract long checking(Game g);
	
	protected abstract void sendMessage(Proxy p);
}
