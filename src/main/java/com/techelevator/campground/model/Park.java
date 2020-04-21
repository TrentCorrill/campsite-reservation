package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Park {

	private Long id;
	private String name;
	private String location;
	private LocalDate establishDate;
	private double area;
	private int visitors;
	private String description;
	private List<Campground> campgrounds = new ArrayList<>();
	
	public Park() {
		
	}

	public void addCampground(Campground campground) {
		campgrounds.add(campground);
	}
	
	public String getCampgroundInfo() {
		String result = "";
		for (int i = 1; i < campgrounds.size() + 1; i ++) {
			result += "#" + i + " " + campgrounds.get(i - 1) + "\n";
		}
		return result;
	}

	public List<Campground> getCampgrounds() {
		return campgrounds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(LocalDate establishDate) {
		this.establishDate = establishDate;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getVisitors() {
		return visitors;
	}

	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInfo() {
		String result = "Park Information Screen\n";
		result += this.getName() + "\n";
		result += "Location: " + this.getLocation() + "\n";
		result += "Established: " + this.getEstablishDate() + "\n";
		result += "Area: " + this.getArea() + "\n";
		result += "Annual Visitors: " + this.getVisitors() + "\n";
		result += this.getDescription();
		return result;
	}

	@Override
	public String toString() {
		String result = "Null value for park";
		if (!name.equals(null)) {
			result = name;
		}
		return result;
	}

}
