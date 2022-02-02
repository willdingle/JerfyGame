package com.willdingle.jerfygame.files;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Save {

	public static String[] load(File file) {
		String fileContents[] = new String[4];
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
	
	public static void create(File file, String name) {
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(name + "\n0" + "\n1" + "\n1");
			writer.close();
		} catch (IOException e) {
			System.out.println("Error creating new save");
			e.printStackTrace();
		}
	}
}
