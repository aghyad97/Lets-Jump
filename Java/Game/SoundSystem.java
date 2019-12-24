package Game;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
//SoundSystem is to play some sound files and control the sounds
public class SoundSystem{
	private static File file;
	private static Clip clip;
	//Constructor is to create a File 
	public SoundSystem() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		file = new File("");
	}
	//Sound method is to store the paths of all sound files 
	static String soundfile(int ch) {
		@SuppressWarnings("unused")
		String file;
		if(ch == 1) return "SoundFiles\\fail.wav";
		else if(ch == 2) return "SoundFiles\\gameover.wav";
		else if(ch == 3) return "SoundFiles\\intro.wav";
		else if(ch == 4) return "SoundFiles\\jump.wav";
		else if(ch == 5) return "SoundFiles\\jump_super.wav";
		else if(ch == 6) return "SoundFiles\\losing.wav";
		else if(ch == 7) return "SoundFiles\\score.wav";
		else if(ch == 8) return "SoundFiles\\start.wav";
		else if(ch == 9) return "SoundFiles\\warning.wav";
		else if(ch == 10) return "SoundFiles\\wow.wav";
		else return "Error";
	}
	//Play method is to play the sounds with a parameter "ch" to choose which sound file
	void play(int ch) {
		try {
			file = new File(soundfile(ch));//Create the sound file with the passed argument
			clip = AudioSystem.getClip();//Get the clip 
			clip.open(AudioSystem.getAudioInputStream(file));
			if(clip.getMicrosecondLength()/1000 < 3) { // if the sound file is less than 3 seconds, it will loop for more than once
				clip.loop(clip.getFrameLength());//loop according to the length of the sound file
				Thread.sleep(clip.getFrameLength());//Sleep the thread until the sound file finish playing
				clip.start();//Start the clip
			}
			else clip.start(); // else just play the clip 
		}catch(Exception e) {
			System.out.println("Error!"); 
			e.printStackTrace();
		}
	}
	//Stop method is to stop the sound file
	void stop() {
		clip.stop();
		//clip.close();
	}
}