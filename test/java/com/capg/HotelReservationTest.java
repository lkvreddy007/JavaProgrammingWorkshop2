package com.capg;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;

public class HotelReservationTest {

	@Test
	public void givendetailsOf3Hotels_WhenAddedToHotelList_SizeOfListIs3(){
		HotelReservation temp = new HotelReservation();
		temp.addHotel();
		ArrayList<Hotel> list=temp.getHotelList();
		Assert.assertEquals(1, list.size());
	}
}
