package StartegyDesign;

public class HardDifficulty implements Difficulty {

	@Override
	public int getInitialTime() { // 1.5 seconds to react initially, as difficulty is hard
		int time = 1500;
		return time;
	}
	
	@Override
	public double getScoreMultiplier() { // score is increased by a factor of 1.5, as difficulty is hard
		double scoreMultiplier = 1.5;
		return scoreMultiplier;
	}
}
