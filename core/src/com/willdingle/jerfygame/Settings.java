package com.willdingle.jerfygame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Settings {
	
	public static void create(File file) {
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write("vsync=true");
			writer.close();
		} catch (IOException e) {
			System.out.println("Error creating settings file");
			e.printStackTrace();
		}
	}
	
	public static String[] load(File file) {
		String fileContents[] = new String[1];
		try {
			Scanner reader = new Scanner(file);
			
			int line = 0;
			while(reader.hasNextLine()) {
				fileContents[line] = reader.nextLine();
				line++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileContents;
	}

}
