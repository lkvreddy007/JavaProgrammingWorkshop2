package com.capg;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;

public class HotelReservationTest {
	
	@Test
	public void givendetailsOf3Hotels_WhenCorrect_ShouldReturnTrue() {
		HotelReservation hotelResv=new HotelReservation();
		hotelResv.getPrices();
		ArrayList<Hotel> val=hotelResv.findCheapestBestRatedHotel();
		Assert.assertEquals(hotelResv.calcTotal(val.get(0)),140);
	}
}
