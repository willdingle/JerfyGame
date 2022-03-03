package com.willdingle.jerfygame.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import com.willdingle.jerfygame.entities.Player;

public class Save {

	public static String[] load(File file) {
		String fileContents[] = new String[5];
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
		
		if(fileContents[4] != null) {
			
		}
		return fileContents;
	}
	
	public static void loadInv(String inv) {
		String[] itemsTemp = inv.split(",");
		String[] items = new String[itemsTemp.length * 2];
		
		int tempIndex = 0;
		for(int n = 0; n < itemsTemp.length; n++) {
			items[n] = itemsTemp[tempIndex].split(";");
		}
	}
	
	public static void create(File file, String name) {
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(name + "\n0" + "\n1" + "\n1" +"\n");
			writer.close();
		} catch (IOException e) {
			System.out.println("Error creating new save");
			e.printStackTrace();
		}
	}
	
	public static void write(File file, String fileName, String mode, Player player) {
		String fileContents[] = new String[5];
		fileContents = load(file);
		
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(fileContents[0] + "\n");
			
			switch(mode) {
			case "pos":
				break;
			case "area":
				break;
			case "inv":
				for(int n = 1; n < 4; n++) {
					writer.write(fileContents[n] + "\n");
				}
				for(int n = 0; n < player.inv.length; n++) {
					if(n == 0) writer.write(player.inv[n][0] + ";" + player.inv[n][1]);
					else writer.write("," + player.inv[n][0] + ";" + player.inv[n][1]);
				}
				break;
			case "equipped":
				break;
			case "health":
				break;
			case "money":
				break;
			}
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
