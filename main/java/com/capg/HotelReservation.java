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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
	
	public static void setDates() throws InvalidInputException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
		System.out.println("Check-In date(ddMMMyyyy),Check-Out date(ddMMMyyyy):");
		String temp = sc.nextLine();
		String[] dates=temp.split(",");
		Pattern pattern = Pattern.compile("[0-9]{2}[A-Z][a-z]{2}[0-9]{4}");
		Matcher matcher1=pattern.matcher(dates[0]);
		Matcher matcher2=pattern.matcher(dates[1]);
		if(matcher1.matches()) {
			try {
				checkin = dateFormat.parse(dates[0]);
			} 
			catch (ParseException e) {
				System.out.println("Cannot Parse Checkin Date");
			}
		}	
		else {
			throw new InvalidInputException("Invalid Checkin Date");
		}
		if(matcher2.matches()) {
			try {
				checkout = dateFormat.parse(dates[1]);
			}
			catch (ParseException e) {
				System.out.println("Cannot Parse Checkout Date");
			}
		}
		else {
			throw new InvalidInputException("Invalid Checkout Date");
		}
	}
	
	public static Map<Hotel,Integer> findCheapestHotel() throws InvalidInputException {
		setPrices();
		System.out.println(hotelList);
		setDates();
		Map<Hotel,Integer> hotelCost=new HashMap<Hotel,Integer>();
		Map<Hotel,Integer> cheapList=new HashMap<Hotel,Integer>();
		hotelCost = hotelList.stream().collect(Collectors.toMap(h->h, h->calcTotal(h)));
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
	
	public static ArrayList<Hotel> findCheapestBestRatedHotel() throws InvalidInputException {
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
			}
		});
		list.stream().forEach
        (k-> System.out.println("Cheapest Best Rated Hotel is: \n"+k.getName()+", Total Rates: $"+calcTotal(k)));
		
		return list;
	}
	
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
	
	public static Map<Hotel,Integer> findBestRatedHotel() throws InvalidInputException {
		setPrices();
		System.out.println(hotelList);
		setDates();
		Map<Hotel,Integer> ratingMap=new HashMap<Hotel,Integer>() ;
		Map<Hotel,Integer> highestRatedMap=new HashMap<Hotel,Integer>();
		ratingMap=hotelList.stream().collect(Collectors.toMap(h->h, h->h.getRating()));
		int maxRating=Collections.max(ratingMap.values());
		ratingMap.forEach((k,v)->{
			if(v==maxRating) {
				highestRatedMap.put(k,calcTotal(k));
			}
		});
		highestRatedMap.entrySet().stream().forEach
         (k-> System.out.println("Highest Rated Hotel is: \n"+k.getKey().getName()+", Total Rates: $"+k.getValue()));
		 
		return highestRatedMap;
	}
	
	public static void setPrices() {
		System.out.println("Enter 1 for regular Customer and 2 for Reward Customer");
		int choice=Integer.parseInt(sc.nextLine());
		if(choice==1) {
			Hotel lakeWood=new Hotel("Lakewood",110,90,3);
			Hotel bridgeWood=new Hotel("Bridgewood",150,50,4);
			Hotel ridgeWood=new Hotel("Ridgewood",220,150,5);
			hotelList.add(lakeWood);
			hotelList.add(bridgeWood);
			hotelList.add(ridgeWood);
		}
		else if(choice==2) {
			Hotel lakeWood=new Hotel("Lakewood",80,80,3);
			Hotel bridgeWood=new Hotel("Bridgewood",110,50,4);
			Hotel ridgeWood=new Hotel("Ridgewood",100,40,5);
			hotelList.add(lakeWood);
			hotelList.add(bridgeWood);
			hotelList.add(ridgeWood);
		}
		else {
			System.out.println("Invalid Input try again");
			setPrices();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to Hotel Reservation");
		try {
			findBestRatedHotel();
		} catch (InvalidInputException e) {
			System.out.println(e);
		}
	}
}
