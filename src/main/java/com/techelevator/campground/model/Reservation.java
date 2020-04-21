package com.techelevator.campground.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {

	private Integer reservation_id;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate createDate;
	
	public Reservation(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.reservation_id = null;
	}
	
	public int getReservation_id() {
		return reservation_id;
	}
	public void setReservation_id(int reservation_id) {
		this.reservation_id = reservation_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	
	public long getDuration() {
		return ChronoUnit.DAYS.between(this.startDate, this.endDate);
	}
	
	public void makeReservation(String name, Integer reservation_id) {
		this.name = name;
		this.reservation_id = reservation_id;
		this.createDate = LocalDate.now();
	}
	
}
