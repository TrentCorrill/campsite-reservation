package com.techelevator.campground.model;

import java.util.List;

public interface CampgroundDAO {

	public void setParkCampgrounds(Park park);
	
	public Campground getCampgroundById (Integer id);

}
