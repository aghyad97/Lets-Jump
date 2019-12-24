package Game;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.*;
import MessageBasedArchitecture.*;

public class Main {
    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        Dispatcher d = new Dispatcher("COM4"); // creates dispatcher, opens serial port
        Proxy p1 = new Proxy( d, (byte) 0b00); // creates 3 proxies, one for each arduino, first is jump arduino
        Proxy p2 = new Proxy( d, (byte) 0b01); // hand arduino
        Proxy p3 = new Proxy( d, (byte) 0b10); // led arduino
        Game g = new Game(p1, p2, p3); // pass port to constructor and create a game instance

        p1.setGame(g); // sets game of all proxies
        p2.setGame(g);
        p3.setGame(g);
        g.chooseDifficulty(); // choose difficulty of the game
        g.startGame(); // starts game
    }
}
