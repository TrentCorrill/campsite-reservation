package com.techelevator.campground.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void setParkCampgrounds(Park park) {
		List<Campground> campgrounds = new ArrayList<>();
		String sql = "SELECT campground_id, name, open_from_mm, open_to_mm, daily_fee FROM campground "
				+ "WHERE park_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, park.getId());
		while (results.next()) {
			Campground c = new Campground();
			c.setCampground_id(results.getLong("campground_id"));
			c.setName(results.getString("name"));
			c.setOpen_from_mm(Month.of(results.getInt("open_from_mm")));
			c.setOpen_to_mm(Month.of(results.getInt("open_to_mm")));
			c.setDaily_fee(results.getBigDecimal("daily_fee"));
			campgrounds.add(c);
			park.addCampground(c);
		}
	}

	@Override
	public Campground getCampgroundById(Integer id) {
		List<Campground> campgrounds = new ArrayList<>();
		String sql = "SELECT campground_id, name, open_from_mm, open_to_mm, daily_fee FROM campground "
				+ "WHERE campground_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
		while (results.next()) {
			Campground c = new Campground();
			c.setCampground_id(results.getLong("campground_id"));
			c.setName(results.getString("name"));
			c.setOpen_from_mm(Month.of(results.getInt("open_from_mm")));
			c.setOpen_to_mm(Month.of(results.getInt("open_to_mm")));
			c.setDaily_fee(results.getBigDecimal("daily_fee"));
			campgrounds.add(c);
		}
		
		return campgrounds.get(0);
	}

}
