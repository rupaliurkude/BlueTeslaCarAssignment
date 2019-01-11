package com.qa.teslacartest;

import static org.junit.Assert.assertEquals;

import org.testng.annotations.Test;

import com.qa.teslacar.HighestProfitGeneratingCar;

public class MinPriceWithoutDiscountTest {
	
	HighestProfitGeneratingCar hp = new HighestProfitGeneratingCar();
	
		
	@Test	
	public void test1() {
		
		assertEquals(hp.priceWithoutDiscount(), 140);
	}

	@Test	//should fail the test as min price is 140 Audi Car
	public void test2() {
		
		assertEquals(hp.priceWithoutDiscount(), -140);
	}
	
	@Test	//should fail the test as min price is 140 Audi Car
	public void test3() {
		
		assertEquals(hp.priceWithoutDiscount(), 0);
	}
	
	
	

}
