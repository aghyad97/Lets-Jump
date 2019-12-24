package PlayerSystem;

import java.awt.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//CreateFile class is to create files and record the scores in FileOutputStream
public class CreateFile {
	static String path; // path of the file
	static File myObj = null; // Create a File object 
	static FileOutputStream OS = null;

	// Constructor to create file with given file name
	public CreateFile(String file) throws IOException {
		path = file;
		myObj = new File(path);
		if (myObj.createNewFile()) { // create a file and check if it exists or not
			System.out.println("File created: " + myObj.getName()); // if it is not , file created and print the name of the file to the console
		} else {
			System.out.println("File already exists."); // if it exists, print the file name to the console
		}
		OS = new FileOutputStream(path, true); // create file output stream with the path passed as an argument
	}

	// TextAppend method is to append text to the file and not overwrite it
	static void TextAppend(String score) throws IOException {
		byte[] strToBytes = score.getBytes();
		OS.write(strToBytes);
		OS.flush();
	}

	// Close method is to close the file after done with the file and print the highest scores from the file itself
	static void closefile() throws IOException {
		OS.close();
		findMax();
	}
	// findMax method is to find the highest scores inside the scores file and print it to the console once the player finishes playing
	@SuppressWarnings({ "resource" })
	static void findMax() throws IOException {

		Scanner in = null; // create a scanner to scan the file 
		File F = new File(path);
		in = new Scanner(F);

		ArrayList<Player> list = new ArrayList<Player>(); // create an arraylist to store the highest scores inside it
		String name;
		int score;

		while (in.hasNext()) {
			name = in.next();
			score = in.nextInt();
			list.add(new Player(name, score));
		}
		// sort the list and then print the first five players of the list (highest scores)
		Collections.sort(list, new marksCompare());
		System.out.println("\t\tThe highest scores");
		for (int i = 0; i < 5 && i < (list.size()); i++) {
			System.out.println("\t\t   " + list.get(i).name + " " + list.get(i).score);
		}
	}
}