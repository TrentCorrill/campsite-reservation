package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	public List<Integer > sitesAvailableForDateRange(Campground campground, LocalDate startDate, LocalDate endDate);
	
	public Long makeReservation(Reservation reservation, Site site);
}
