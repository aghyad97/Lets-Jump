package PlayerSystem;

import java.io.IOException;
import java.util.Scanner;

//Player class is to create an instance of player
public class Player {
	int score = 0;
	String name = null;
	static CreateFile cf = null;
	static String path = null;

	public Player(String n, int s) throws IOException {
		name = n;
		score = s;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// SaveScore method is to store the scores of the players each time a player
	// finishes playing
	public void SaveScore() throws IOException {
		CreateFile File = new CreateFile("PlayersScores.txt");// Create a file to store the scores of the players
		String sc = String.valueOf(score);
		CreateFile.TextAppend(name + " " + sc + "\n");
	}

	// DonePlay method is to close the file after finish playing
	public static void DonePlay() throws IOException {
		CreateFile.closefile();
	}

}
