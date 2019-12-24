package StartegyDesign;

public interface Difficulty {  
	public int getInitialTime(); // returns initial time to react, which changes with every difficulty
	public double getScoreMultiplier(); // returns score multiplier, which changes with every difficulty
}
