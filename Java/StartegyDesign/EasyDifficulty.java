package StartegyDesign;

public class EasyDifficulty implements Difficulty {

	@Override
	public int getInitialTime() { // 3 seconds to react initially if easy difficulty
		int time = 3000;
		return time;
	}

	@Override
	public double getScoreMultiplier() { // score is reduced by a factor of 0.75, as difficulty is easy
		double scoreMultiplier = 0.75;
		return scoreMultiplier;
	}
	
}
