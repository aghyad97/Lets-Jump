 package Game;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import MessageBasedArchitecture.*;
import PlayerSystem.Player;
import StrategyDesign.*;
import TemplateDesign.GameMessageHandling;
import TemplateDesign.HandHandling;
import TemplateDesign.JumpHandling;
public class Game implements Runnable {
    private Random rand;
    public int score = 0, time;
    private char difficulty;
    public volatile static msg m;
    private GameMessageHandling handHandle, jumpHandle;
    SoundSystem Sound;
	Proxy jumpArduinoProxy, handArduinoProxy, ledArduinoProxy;
	Difficulty d;
    public Game(Proxy p1, Proxy p2, Proxy p3) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        rand = new Random();
        difficulty = 'z';
        jumpArduinoProxy = p1;
        handArduinoProxy = p2;
        ledArduinoProxy = p3;
        Sound = new SoundSystem();
        m = new msg((byte) 0b11111111, (byte) 0b11111111);
        handHandle = new HandHandling();
        jumpHandle = new JumpHandling();
    }
    
    public void run() {
    	try {
			playGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void startGame() {
    	new Thread(this).start(); // Starts the game thread
    }
    public void chooseDifficulty() throws IOException { // chooses a difficulty
    	Sound.play(3);
    	boolean diffSelect = true;
    	System.out.println("Choose a difficulty (z for easy, m for medium, x for hard):");
    	while (diffSelect) { // loops until a correct option has been chosen (either z or x)
	    	difficulty = (char) System.in.read();
	    	if (difficulty == 'z' || difficulty == 'x' || difficulty == 'm') { // z is easy x is hard
	    	diffSelect = false;
	    	System.out.println("Difficulty has been chosen.");
	    	} else {
	    	System.out.println("Incorrect choice entered, please choose either z, m, or x: "); // gets difficulty from
	    	// player
	    	}
	    }
    	
    	if (difficulty == 'z') // create an object of the correct difficulty behavior
    		d = new EasyDifficulty();
    	else if (difficulty == 'm')
    		d = new MediumDifficulty();
    	else
    		d = new HardDifficulty();
    }
	
	private int decreaseTimeToReact(int timeToReact) { // Decreases time to react to appropriate action
		if (timeToReact >= 750)						   // to increase difficulty of game with each round
			timeToReact -= 75;
		return timeToReact;
	}
	
	/* #######################################################################################################################
	   ###										  Beginning of playGame function										  ####
	   ####################################################################################################################### */
	
    public void playGame() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException { // play the game and return the score after game ends
    	System.out.print("Please enter your name: ");
        Scanner sc = new Scanner(System.in);
        String name = sc.next(); // Reads player name from system input
    	Player p = new Player(name, 0); // create a Player object
    	int gesture;
    	
    	Sound.stop();
    	Sound.play(8);
    	Thread.currentThread().sleep(4000); // sleeps until sound is done playing
        boolean failed = false;
        time = d.getInitialTime(); // gets the initial time from the Difficulty
        
                
        while (!failed) { // keeps looping until player has failed jumping/raising hand
        	gesture = rand.nextInt(2); // generates randomly either 0 or 1, 0 for jumping, 1 for raising hand
            // gesture = 0;
            if (gesture == 0) { // jumping
                System.out.println("JUMP!");
              	Sound.play(4);
              	
                boolean jumped = jumpHandle.Recipe(jumpArduinoProxy, this); // starts the recipe of GameMessageHandling template
                															// which sends a message to Xbee and sees if player has jumped
                if (!jumped) { // check if player has jumped or not
                    failed = true; // exit out of loop as player lost
                  	Sound.play(1);
                    System.out.println("You lost!");
                }
            }
            else {// raise hand
                System.out.println("Raise Hand!");
                Sound.play(5);
                
                boolean hand = handHandle.Recipe(handArduinoProxy, this); // starts the recipe of GameMessageHandling template
                														  // which sends a message to Xbee and sees if player has raised hand
                
                if (!hand) { // check if player has raised hand or not
                    failed = true; // exit out of loop as player lost
                  	Sound.play(1);
                    System.out.println("You lost!");
                }
            }
            
            msg endMessage = new msg((byte) 0b11, (byte) 0b110000); // creating and sending message to led arduino
            ledArduinoProxy.send_msg(endMessage); // to show that the round has ended

			Thread.sleep(rand.nextInt(2000) + 2000); // sleeps for 2 to 4 seconds

            time = decreaseTimeToReact(time); // decreases time to react after round is done
        } // end of game loop
        
        score = (int)(score * d.getScoreMultiplier()); // apply a multipler on a score depending which Difficulty was chosen
        
        p.setScore(score); // sets player score
        System.out.println("Got a score of " + score + "!");
        if (score == 0) {
        	Sound.play(2); // plays a sound depending on score
        }
        else if (score <= 100 && score > 0) {
        	Sound.play(10);
        }
		else if (score > 100) {
        	Sound.play(7);
        }
        p.SaveScore(); // saves score
        Player.DonePlay(); // closes scores file
    }

   /* #######################################################################################################################
      ###										  	  End of playGame function											 ####
      ####################################################################################################################### */
}
