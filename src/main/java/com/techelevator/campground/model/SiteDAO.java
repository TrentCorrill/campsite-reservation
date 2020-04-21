package com.techelevator.campground.model;

import java.util.List;

public interface SiteDAO {

	public List<Site> getAllSitesForCampground(Campground campground);
	
	public Site getSiteForSiteId(Integer id);
	
	
}
