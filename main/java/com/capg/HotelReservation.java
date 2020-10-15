package com.capg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HotelReservation {
	private static Scanner sc = new Scanner(System.in);	
	private static ArrayList<Hotel> hotelList = new ArrayList<>();
	private static Date checkin,checkout;
	
	public ArrayList<Hotel> getHotelList() {
		return hotelList;
	}
	
	public void sethotelList(ArrayList<Hotel> hotelList) {
		this.hotelList=hotelList;
	}
	
	public static void addHotel(){
		System.out.println("Enter the name of hotel:");
		String name = sc.nextLine();
		System.out.println("Enter Weekday price:");
		int priceWeekday =Integer.parseInt(sc.nextLine());
		System.out.println("Enter Weekend price:");
		int priceWeekend=Integer.parseInt(sc.nextLine());
		System.out.println("Enter Rating:");
		int rating=Integer.parseInt(sc.nextLine());
		Hotel temp = new Hotel(name,priceWeekday,priceWeekend,rating);
		hotelList.add(temp);
	}
	
	public static Map<Hotel,Integer> findCheapestHotel() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
		System.out.println("Check-In date(ddMMMyyyy),Check-Out date(ddMMMyyyy):");
		String temp = sc.nextLine();
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
		Map<Hotel,Integer> hotelCost=new HashMap<Hotel,Integer>();
		Map<Hotel,Integer> cheapList=new HashMap<Hotel,Integer>();
		for(Hotel h:hotelList) {
			hotelCost.put(h,calcTotal(h));
		}
		int low=Collections.min(hotelCost.values());
		System.out.println("Cheapest Hotel for the given dates is");
		hotelCost.forEach((k,v)->{
			if(v==low) {
				cheapList.put(k, v);
				System.out.println(k.getName()+", Total Rates: $"+v);
			}
		});
		return cheapList;
	}
	
	public static Hotel findCheapestBestRatedHotel() {
		Map<Hotel,Integer>cheapList= findCheapestHotel();
		ArrayList<Integer> ratingList=new ArrayList<Integer>();
		ArrayList<Hotel> list=new ArrayList<Hotel>();
		cheapList.forEach((k,v)->{
			ratingList.add(k.getRating());
		});
		int maxRating=Collections.max(ratingList);
		cheapList.forEach((k,v)->{
			if(k.getRating()==maxRating) {
				list.add(k);
				System.out.println("Highest Rated Cheap Hotel is: \n"+ k.getName()+", Total Rates: $"+v);
			}
		});
		return list.get(0);
	}
	
	//Map<Map<Hotel,Integer>,Integer>
	
	public static int calcTotal(Hotel h) {
		long difference = checkout.getTime() - checkin.getTime();
	    int numDays = (int) (difference / (1000*60*60*24))+1;
		int priceWeekday=h.getPriceWeekday();
		int priceWeekend=h.getPriceWeekend();
		int numOfWeekends=getNumOfWeekends(checkin, checkout);
		int totalAmt= ((int)numDays-numOfWeekends)*priceWeekday+numOfWeekends*priceWeekend;
		return totalAmt;
	}
	
	public static int getNumOfWeekends(Date start,Date end) {
		Calendar calStart=Calendar.getInstance();
		calStart.setTime(start);
		Calendar calEnd=Calendar.getInstance();
		calEnd.setTime(end);
		int count=0;
		String day="";
		String[] strDays = new String[]{
                "Sunday",
                "Monday", 
                "Tuesday",
                "Wednesday",
                "Thusday",
                "Friday",
                "Saturday"
              };
		while(calStart.before(calEnd)|| calStart.equals(calEnd)) {
			day = strDays[calStart.get(Calendar.DAY_OF_WEEK) - 1];
			if(day.equals("Sunday")||day.equals("Saturday")) {
				count++;
			}
			calStart.add(Calendar.DAY_OF_MONTH, 1);
		}
		return count;
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to Hotel Reservation");
		Hotel lakeWood=new Hotel("Lakewood",110,90,3);
		Hotel bridgeWood=new Hotel("Bridgewood",150,50,4);
		Hotel ridgeWood=new Hotel("Ridgewood",220,150,5);
		hotelList.add(lakeWood);
		hotelList.add(bridgeWood);
		hotelList.add(ridgeWood);
		System.out.println(hotelList);
		//findCheapestHotel();
		findCheapestBestRatedHotel();
	}
}
