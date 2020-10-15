package com.capg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class HotelReservation {
	private static Scanner sc = new Scanner(System.in);	
	private static ArrayList<Hotel> hotelList = new ArrayList<>();
	private static Date checkin,checkout;
	
	public ArrayList<Hotel> getHotelList() {
		return hotelList;
	}
	
	public static void addHotel(){
		System.out.println("Enter the name of hotel:");
		String name = sc.nextLine();
		System.out.println("Enter Weekday price:");
		int priceWeekday =Integer.parseInt(sc.nextLine());
		System.out.println("Enter Weekend price:");
		int priceWeekend=Integer.parseInt(sc.nextLine());
		Hotel temp = new Hotel(name,priceWeekday,priceWeekend);
		hotelList.add(temp);
	}
	
	public static String findCheapestHotel() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
		System.out.println("Check-In date(ddMMMyyyy),Check-Out date(ddMMMyyyy):");
		String temp = sc.next();
		String[] dates=temp.split(",");
		try {
			checkin = dateFormat.parse(dates[0]);
		} 
		catch (ParseException e) {
			System.out.println("invalid checkin date");
		}
		try {
			checkout = dateFormat.parse(dates[1]);
		}
		catch (ParseException e) {
			System.out.println("invalid checkout date");
		}
		long difference = checkout.getTime() - checkin.getTime();
	    int numDays = (int) (difference / (1000*60*60*24))+1;
		Hotel cheapestHotel=hotelList.get(0);
		for(Hotel h:hotelList) {
			if(h.getPriceWeekday()<cheapestHotel.getPriceWeekday()) {
				cheapestHotel=h;
			}
		}
		int price=cheapestHotel.getPriceWeekday();
		String hotelName=cheapestHotel.getName();
		int totalAmt= (int)numDays*price;
		System.out.println("Cheapest Hotel for the given dates is \n"+hotelName+", Total Rates: $"+totalAmt);
		return hotelName;
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to Hotel Reservation");
		Hotel lakeWood=new Hotel("Lakewood",110,90);
		Hotel bridgeWood=new Hotel("Bridgewood",110,50);
		Hotel ridgeWood=new Hotel("Ridgewood",110,150);
		hotelList.add(lakeWood);
		hotelList.add(bridgeWood);
		hotelList.add(ridgeWood);
		System.out.println(hotelList);
		findCheapestHotel();
	}
}
