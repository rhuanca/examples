package renidev.examples.actors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Utils {
	
	public static int[] readNIntsFromFile(String filepath, int n){
		int numbers[] = new int[n];
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
	
	public static int generateFileWithNInts(String path, int n) {
	    Random r = new Random();
	    int upperLimit = n * 3;
	    int max = 0;
	    try {
	      File file= new File(path);
	      PrintWriter pw = new PrintWriter(file);
	      for(int i=0; i<n; i++) {
	    	  int nextInt = r.nextInt(upperLimit);
	    	  pw.println(nextInt);
	    	  if(nextInt > max) {
	    		  max = nextInt;
	    	  }
	      }
	      pw.close();
	    } catch (IOException e) {
	      throw new RuntimeException("Unable to create file. - " + e.getMessage(),e);
	    }
	    return max;
	}
}
      