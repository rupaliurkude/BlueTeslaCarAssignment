package com.qa.teslacartest;

import static org.junit.Assert.*;
import org.testng.annotations.Test;

import com.qa.teslacar.HighestProfitGeneratingCar;



public class TeslaCarWithNotesTest {
	
	HighestProfitGeneratingCar hp = new HighestProfitGeneratingCar();
	

@Test	//by default highest priority is 0
public void test1() {
	assertNotNull(hp.teslaCarsWithNotes());
	
}
	
@Test	(priority =1)
public void test2() {
	
	assertEquals(hp.teslaCarsWithNotes(), 2);
}

@Test	(priority =2)//should fail the test
public void test3() {
	
	assertEquals(hp.teslaCarsWithNotes(), 3);
}

	
}
