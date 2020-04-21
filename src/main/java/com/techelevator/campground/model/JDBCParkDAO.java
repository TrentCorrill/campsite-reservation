package com.techelevator.campground.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> parkList = new ArrayList<>();
		String sql = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

		while (results.next()) {
			Park p = new Park();
			p.setId(results.getLong("park_id"));
			p.setName(results.getString("name"));
			p.setLocation(results.getString("location"));
			p.setEstablishDate(results.getDate("establish_date").toLocalDate());
			p.setArea(results.getDouble("area"));
			p.setVisitors(results.getInt("visitors"));
			p.setDescription(results.getString("description"));
			parkList.add(p);
		}
		return parkList;
	}
	
}
