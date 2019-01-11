package com.qa.teslacar;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import com.jayway.restassured.path.json.JsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HighestProfitGeneratingCar {
	
	File file  = new File("C:\\Users\\rupal\\eclipse-workspace\\TeslaCarAssignmentProject\\src\\test\\java\\com\\qa\\teslacar\\TeslaCars.json");
	// Extracting the json response from the teslaCarfile
	JsonPath jsonResponse = JsonPath.from(file);
	ObjectMapper mapper = new ObjectMapper();
	int totalCars = jsonResponse .get("Car.size()");
	
		public int teslaCarsWithNotes(){
			int blueTeslaCars =0;
			
			for(int CarCount= 0; CarCount< totalCars ;CarCount++) {
				if(jsonResponse .getString("Car["+CarCount+"].metadata.Color").equals("Blue") &&
					jsonResponse .getString("Car["+CarCount+"].make").equals("Tesla")) {
					blueTeslaCars ++;				
					// Question 1:  printing just  the notes for tesla Cars present			
					//System.out.println("A Blue Teals Car found with Note as "+jsonResponse .getString("Car["+CarCount+"].metadata.Notes" ));			
					
				}
				
				
			}

			// Question 1: Print all the blue Teslas received in the web service response.
			//System.out.println("Total Number of Blue Tesla Cars are ::"+blueTeslaCars );
		
			
			return blueTeslaCars  ;
		}
		
			
		public int priceWithoutDiscount() {		
		//Code on getting the min price 
		int minPrice=jsonResponse .get("Car.perdayrent.Price.min()");
		
		//Question 2: Return  cars which have the lowest per day rental cost a. Price only
	//	System.out.println(" Lowest Price without discount: "+ jsonResponse .get("Car.findAll{it->it.perdayrent.Price == "+ minPrice+"}") );
	//	System.out.println();
		 
		 return minPrice;
		}

//		b. Price after discounts
		
	List <Double> ListCarsDiscount = new ArrayList<Double>();
	public Double priceAfterDiscount( ) {
		Double  lowPriceDiscount ;
		Double  CalPriceDiscount = null ;
		double price ;
		int discount;
		
		//calculating discounts for all the cars
		for(Double CarCount=0.0;CarCount< totalCars;CarCount++) {
			price=jsonResponse .get("Car["+CarCount+"].perdayrent.Price");//price of the car per day
			discount=jsonResponse .get("Car["+CarCount+"].perdayrent.Discount");//discount of the car per day
			CalPriceDiscount= (double) (price-(price*discount/100));//calculating the rate after discount in percentage
			ListCarsDiscount.add(CalPriceDiscount);// list of all the rates after discount
		}
		
		//Finding lowest pricing and discount in all the cars
		lowPriceDiscount=Collections.min(ListCarsDiscount);
		//finding and displaying all the cars which has less price per day based on price and discount  
		for(int CarCount=0;CarCount< totalCars;CarCount++) {
			price=jsonResponse .get("Car["+CarCount+"].perdayrent.Price");
			discount=jsonResponse .get("Car["+CarCount+"].perdayrent.Discount");
			CalPriceDiscount=(Double) (price-(price*discount/100));
			if(CalPriceDiscount==lowPriceDiscount) {//checking for minimum discount 
				//CalPriceDiscount= 	jsonResponse .get("Car["+CarCount+"]");// retrieving the cars based on the min rate after discount
			
			}
		}
		return CalPriceDiscount;
	}

		
		
	// Q3 : Finding the highest revenue generated car	
		
	List<Double> listRevenueCars=new ArrayList<Double>();
	public Double highestRevenueCar(Double RevenueCarIndex) {
		
		int  Days;
		//Calculating the revenues of all the cars
		for(int CarCount=0;CarCount< totalCars ;CarCount++) {
			Days=jsonResponse .get("Car["+CarCount+"].metrics.rentalcount.yeartodate");//no.of rental days
			listRevenueCars.add(ListCarsDiscount.get(CarCount)*Days);//adding revenues to list
		}
		//Finding maximum revenue in the list of revenue generating Cars
//		System.out.println("The highest revenue generating car \n"+ jsonResponse .get("Car["+RevenueCarIndex+"]"));
		return RevenueCarIndex= (double) listRevenueCars.indexOf(Collections.max(listRevenueCars));
	
	}
		
	//Calculating highest profit car
	
	List<Double> listProfit=new ArrayList<Double>();
	 public Double higestProfitCar(Double ProfitIndex ) {
		 
		Double yoyManintenanceCost,depreciation;
		for(int CarCount=0;CarCount< totalCars ;CarCount++) {
			yoyManintenanceCost= jsonResponse .get("Car["+CarCount+"].metrics.yoymaintenancecost");
			depreciation= jsonResponse .get("Car["+CarCount+"].metrics.depreciation");
			listProfit.add(listRevenueCars.get(CarCount)-(yoyManintenanceCost+depreciation));
		}
//		System.out.println("The highest Profit generating car:\n"+jsonResponse .get("Car["+ProfitIndex+"]")+"\n");
		return	ProfitIndex=  (double) listProfit.indexOf(Collections.max(listProfit));
	
		
	}
	

}


		


