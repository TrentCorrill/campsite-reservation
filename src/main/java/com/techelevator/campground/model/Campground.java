package com.techelevator.campground.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public class Campground {

	private Long campground_id;
	private String name;
	private Month open_from_mm;
	private Month open_to_mm;
	private BigDecimal daily_fee;

	public Long getCampground_id() {
		return campground_id;
	}

	public void setCampground_id(Long campground_id) {
		this.campground_id = campground_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Month getOpen_from_mm() {
		return open_from_mm;
	}

	public void setOpen_from_mm(Month open_from_mm) {
		this.open_from_mm = open_from_mm;
	}

	public Month getOpen_to_mm() {
		return open_to_mm;
	}

	public void setOpen_to_mm(Month open_to_mm) {
		this.open_to_mm = open_to_mm;
	}

	public BigDecimal getDaily_fee() {
		return daily_fee;
	}

	public void setDaily_fee(BigDecimal daily_fee) {
		this.daily_fee = daily_fee;
	}
	
	private String monthToString(Month m) {
		String result = m.toString().charAt(0) + m.toString().substring(1).toLowerCase();
		return result;
		
	}
	
	@Override
	public String toString() {
		String result = String.format("%-20s %-10s %-10s %-10s", this.getName(), this.monthToString(getOpen_from_mm()), this.monthToString(this.getOpen_to_mm()), this.getDaily_fee());
		return result;
	}
}
