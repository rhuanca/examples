package renidev.examples.actors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {
	public static int[] read5000IntsFromFile(String filepath){
		int numbers[] = new int[5000];
		Scanner scanner;
		try {
			scanner = new Scanner(new File(filepath));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		for(int i=0; i<5000;i++) {
			numbers[i] = scanner.nextInt();
		}
		return numbers;
	}
}
      