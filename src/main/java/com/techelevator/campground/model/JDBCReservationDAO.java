package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {
	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Integer> sitesAvailableForDateRange(Campground campground, LocalDate startDate, LocalDate endDate) {
		List<Integer> availableSiteIds = new ArrayList<>();
		
		String sql = "SELECT site.site_id FROM site " + 
				"JOIN campground ON site.campground_id = campground.campground_id " + 
				"WHERE " + 
				"campground.campground_id = ? AND " + 
				"site.site_id " + 
				"NOT IN ( " + 
				"    SELECT reservation.site_id " + 
				"    FROM reservation " + 
				"    WHERE (reservation.start_date, reservation.start_date + reservation.num_days) OVERLAPS (?, ?) " + 
				") " + 
				"ORDER BY site.site_number " +
				"LIMIT 5;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, campground.getCampground_id(), startDate, endDate);
		
		while (results.next()) {
			availableSiteIds.add(results.getInt("site_id"));
			
		}
		return availableSiteIds;
	}
	

	@Override
	public Long makeReservation(Reservation reservation, Site site) {
		String sql = "INSERT into reservation (site_id, name, start_date, num_days, create_date) " +
					 "VALUES (?, ?, ?, ?, ?) RETURNING reservation_id;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, site.getSite_id(), reservation.getName(), reservation.getStartDate(), reservation.getDuration(), LocalDate.now());
		
		Long resID = null;
		if (results.next()) {
			resID = results.getLong("reservation_id");
		}
		
		return resID;
		
		

	}
	
}
