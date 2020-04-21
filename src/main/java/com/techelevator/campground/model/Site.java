package com.techelevator.campground.model;

public class Site {

	private int site_id;
	private int site_number;
	private int max_occupancy;
	private boolean accessible;
	private int max_rv_length;
	private boolean utilities;

	public int getSite_id() {
		return site_id;
	}

	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}

	public int getSite_number() {
		return site_number;
	}

	public void setSite_number(int site_number) {
		this.site_number = site_number;
	}

	public int getMax_occupancy() {
		return max_occupancy;
	}

	public void setMax_occupancy(int max_occupancy) {
		this.max_occupancy = max_occupancy;
	}

	public boolean isAccessible() {
		return accessible;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public int getMax_rv_length() {
		return max_rv_length;
	}

	public void setMax_rv_length(int max_rv_length) {
		this.max_rv_length = max_rv_length;
	}

	public boolean isUtilities() {
		return utilities;
	}

	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	
	
	@Override
	public String toString() {
		String result = "";
		result += this.getSite_number() + "\t" + this.getMax_occupancy() + "\t";
		result += this.isAccessible() + "\t";
		result += this.getMax_rv_length() + "\t";
		result += this.isUtilities();
		return result;
	}

}
