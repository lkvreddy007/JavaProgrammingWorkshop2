package com.capg;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;

public class HotelReservationTest {
	
	@Test
	public void givendetailsOf3Hotels_WhenCorrect_ShouldReturnHotelNameWithHighestRating() {
		HotelReservation temp = new HotelReservation();
		Hotel lakeWood=new Hotel("Lakewood",110,90,3);
		Hotel bridgeWood=new Hotel("Bridgewood",150,50,4);
		Hotel ridgeWood=new Hotel("Ridgewood",220,150,5);
		ArrayList<Hotel> list=temp.getHotelList();
		list.add(lakeWood);
		list.add(bridgeWood);
		list.add(ridgeWood);
		temp.sethotelList(list);
		Map<Hotel,Integer> val=temp.findBestRatedHotel();
		Assert.assertEquals(val.keySet().stream().findFirst().get().getName(),"Ridgewood");
	}
}
