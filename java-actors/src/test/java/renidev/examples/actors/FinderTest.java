package renidev.examples.actors;

import org.junit.Test;
import static org.junit.Assert.*;

public class FinderTest {

	/*
	@Test
	public void test_sequencialMax(){
		Finder finder = new Finder();
		int max = finder.sequencialMax(new int[]{1,2,3,4,5});
		assertEquals(5, max);
	}
	
	
	@Test
	public void test_sequencialMax_from_file(){
		Finder finder = new Finder();
		String filepath = FinderTest.class.getClassLoader().getResource("number.txt").getFile();
		int max = finder.sequencialMax(Utils.read5000IntsFromFile(filepath));
		assertEquals(9998, max);
	}
	*/
	
	@Test
	public void test_concurrentMax_from_file(){
		Finder finder = new Finder();
		String filepath = FinderTest.class.getClassLoader().getResource("number.txt").getFile();
		int max = finder.concurrentMax(Utils.read5000IntsFromFile(filepath));
		assertEquals(9998, max);
	}
}
