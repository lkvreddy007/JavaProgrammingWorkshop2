package com.capg;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;

public class HotelReservationTest {
	
	@Test
	public void givendetailsOf3HotelsForRewardCustomer_WhenCorrect_ShouldReturnTrue() throws InvalidInputException {
		HotelReservation hotelResv=new HotelReservation();
		ArrayList<Hotel> val=hotelResv.findCheapestBestRatedHotel();
		Assert.assertEquals(hotelResv.calcTotal(val.get(0)),140);
	}
}
