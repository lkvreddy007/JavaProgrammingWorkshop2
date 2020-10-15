package com.capg;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;

public class HotelReservationTest {

	@Test
	public void givendetailsOf3Hotels_WhenAddedToHotelList_SizeOfListIs3(){
		HotelReservation temp = new HotelReservation();
		Hotel lakeWood=new Hotel("Lakewood",110,90);
		Hotel bridgeWood=new Hotel("Bridgewood",160,50);
		Hotel ridgeWood=new Hotel("Ridgewood",220,150);
		ArrayList<Hotel> list=temp.getHotelList();
		list.add(lakeWood);
		list.add(bridgeWood);
		list.add(ridgeWood);
		Assert.assertEquals(3, list.size());
	}
	
	@Test
	public void givendetailsOf3Hotels_WhenCorrect_ShouldReturnHotelNameWithLowRate() {
		HotelReservation temp = new HotelReservation();
		Hotel lakeWood=new Hotel("Lakewood",110,90);
		Hotel bridgeWood=new Hotel("Bridgewood",160,50);
		Hotel ridgeWood=new Hotel("Ridgewood",220,150);
		ArrayList<Hotel> list=temp.getHotelList();
		list.add(lakeWood);
		list.add(bridgeWood);
		list.add(ridgeWood);
		Assert.assertEquals("LakeWood",temp.findCheapestHotel());
	}
}
