package StartegyDesign;

public class MediumDifficulty implements Difficulty {

	@Override
	public int getInitialTime() { // 2 seconds to react initially, as difficulty is medium
		int time = 2000;
		return time;
	}
	
	@Override
	public double getScoreMultiplier() { // score is not changed, as difficulty is medium
		double scoreMultiplier = 1;
		return scoreMultiplier;
	}
}
