package com.qa.teslacar;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import org.testng.annotations.Test;
import com.jayway.restassured.path.json.JsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;




public class HighestProfitGeneratingCarTest {
	
	
	@Test
	public void TeslaCarTesting(){
		
		File file ;
		
		 file = new File("C:\\Users\\rupal\\eclipse-workspace\\TeslaCarAssignmentProject\\src\\test\\java\\com\\qa\\teslacar\\TeslaCars.json");

		 ObjectMapper mapper = new ObjectMapper();
		 
		// Extracting the json response from the teslaCarfile
		 
		JsonPath jsonResponse = JsonPath.from(file);
		
		int totalCars = jsonResponse .get("Car.size()");
		
		int blueTeslaCars =0;
		
		for(int CarCount= 0; CarCount< totalCars ;CarCount++) {
			if(jsonResponse .getString("Car["+CarCount+"].metadata.Color").equals("Blue") &&
				jsonResponse .getString("Car["+CarCount+"].make").equals("Tesla")) {
				blueTeslaCars ++;
					
				// Question 1:  printing just  the notes for tesla Cars present
			
				System.out.println("A Blue Teals Car found with Note as "+jsonResponse .getString("Car["+CarCount+"].metadata.Notes" ));
				System.out.println();
			}
			
		}
		
		// Question 1: Print all the blue Teslas received in the web service response.
		System.out.println("Total Number of Blue Tesla Cars are ::"+blueTeslaCars );
		System.out.println();
			
	//Code on getting the min price 
	int minPrice=jsonResponse .get("Car.perdayrent.Price.min()");
	
	//Question 2: Return  cars which have the lowest per day rental cost a. Price only
	System.out.println(" Lowest Price without discount: "+ jsonResponse .get("Car.findAll{it->it.perdayrent.Price == "+ minPrice+"}") );
	System.out.println();

//	b. Price after discounts
	
	int price, discount;
	List<Float> ListCarsDiscount = new ArrayList<Float>();
	float lowPriceDiscount, CalPriceDiscount;
	//calculating discounts for all the cars
	for(int CarCount=0;CarCount< totalCars;CarCount++) {
		price=jsonResponse .get("Car["+CarCount+"].perdayrent.Price");//price of the car per day
		discount=jsonResponse .get("Car["+CarCount+"].perdayrent.Discount");//discount of the car per day
		CalPriceDiscount=price-(price*discount/100);//calculating the rate after discount in percentage
		ListCarsDiscount.add(CalPriceDiscount);// list of all the rates after discount
	}
	
	//Finding lowest pricing and discount in all the cars
	lowPriceDiscount=Collections.min(ListCarsDiscount);
	//finding and displaying all the cars which has less price per day based on price and discount  
	for(int CarCount=0;CarCount< totalCars;CarCount++) {
		price=jsonResponse .get("Car["+CarCount+"].perdayrent.Price");
		discount=jsonResponse .get("Car["+CarCount+"].perdayrent.Discount");
		CalPriceDiscount=price-(price*discount/100);
		if(CalPriceDiscount==lowPriceDiscount)//checking for minimum discount 
			System.out.println("Cars which have the lowest per day rental cost based Price after discounts"+jsonResponse .get("Car["+CarCount+"]")+"\n");// retrieving the cars based on the min rate after discount
			System.out.println();
	}
	
	// Q3 : Finding the highest revenue generated car
	List<Float> listRevenueCars=new ArrayList<Float>();
	int  Days;
	//Calculating the revenues of all the cars
	for(int CarCount=0;CarCount< totalCars ;CarCount++) {
		Days=jsonResponse .get("Car["+CarCount+"].metrics.rentalcount.yeartodate");//no.of rental days
		listRevenueCars.add(ListCarsDiscount.get(CarCount)*Days);//adding revenues to list
	}
	//Finding maximum revenue in the list of revenue generating Cars
	int RevenueCarIndex=listRevenueCars.indexOf(Collections.max(listRevenueCars));
	System.out.println("The highest revenue generating car \n"+ jsonResponse .get("Car["+RevenueCarIndex+"]"));
	System.out.println();
	
	//Calculating highest profit car
	List<Float> listProfit=new ArrayList<Float>();
	float yoyManintenanceCost,depreciation;
	for(int CarCount=0;CarCount< totalCars ;CarCount++) {
		yoyManintenanceCost= jsonResponse .get("Car["+CarCount+"].metrics.yoymaintenancecost");
		depreciation= jsonResponse .get("Car["+CarCount+"].metrics.depreciation");
		listProfit.add(listRevenueCars.get(CarCount)-(yoyManintenanceCost+depreciation));
	}
	int ProfitIndex=listProfit.indexOf(Collections.max(listProfit));
	System.out.println("The highest Profit generating car:\n"+jsonResponse .get("Car["+ProfitIndex+"]")+"\n");
}

}	

	

