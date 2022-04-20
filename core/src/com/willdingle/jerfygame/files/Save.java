package com.willdingle.jerfygame.files;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
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
		return fileContents;
	}
	
	public static String[] loadInv(String inv) {
		String[] itemsTemp = inv.split(",");
		String[] items = new String[itemsTemp.length * 2];
		
		int itemIndex = 0;
		for(int n = 0; n < itemsTemp.length; n++) {
			String[] temp = itemsTemp[n].split(";");
			items[itemIndex] = temp[0];
			items[itemIndex + 1] = temp[1];
			itemIndex += 2;
		}
		return items;
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
	
	public static void write(File file, String mode, Player player) {
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
	
	public static void delete(String fileName) {
		File file = new File(System.getenv("appdata") + "/Jerfy/" + fileName);
		file.delete();
		file = null;
	}
}
