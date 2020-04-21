package com.techelevator.campground.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getAllSitesForCampground(Campground campground) {
		List<Site> siteList = new ArrayList<>();
		String sql = "SELECT site_id, site_number, max_occupancy, accessible, max_rv_length, utilities WHERE campground_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, campground.getCampground_id());

		while (results.next()) {
			Site s = new Site();
			s.setSite_id(results.getInt("site_id"));
			s.setSite_number(results.getInt("site_number"));
			s.setMax_occupancy(results.getInt("max_occupancy"));
			s.setAccessible(results.getBoolean("accessible"));
			s.setMax_rv_length(results.getInt("max_rv_length"));
			s.setUtilities(results.getBoolean("utilities"));
			siteList.add(s);
		}
		return siteList;
	}

	@Override
	public Site getSiteForSiteId(Integer id) {
		List<Site> siteList = new ArrayList<>();
		String sql = "SELECT site_id, site_number, max_occupancy, accessible, max_rv_length, utilities FROM site WHERE site_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

		while (results.next()) {
			Site s = new Site();
			s.setSite_id(results.getInt("site_id"));
			s.setSite_number(results.getInt("site_number"));
			s.setMax_occupancy(results.getInt("max_occupancy"));
			s.setAccessible(results.getBoolean("accessible"));
			s.setMax_rv_length(results.getInt("max_rv_length"));
			s.setUtilities(results.getBoolean("utilities"));
			siteList.add(s);
		}
		return siteList.get(0);
	}

}
