package com.techelevator;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;
import javax.swing.text.DateFormatter;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.JDBCCampgroundDAO;
import com.techelevator.campground.model.JDBCParkDAO;
import com.techelevator.campground.model.JDBCReservationDAO;
import com.techelevator.campground.model.JDBCSiteDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;
import com.techelevator.campground.view.Menu;

public class CampgroundCLI {

	private static Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;
	private static final String MAIN_MENU_OPTION_PARKS = "List Parks";
	private static final String VIEW_CAMPGROUND_OPTION = "View Campgrounds";
	private static final String SEARCH_FOR_RESERVATION_OPTION = "Search for Reservation";
	private static final String RETURN_TO_PREVIOUS_SCREEN_OPTION = "Return to Previous Screen";
	private static final String[] PARK_INFORMATION_SCREEN_MENU_OPTIONS = { VIEW_CAMPGROUND_OPTION,
			SEARCH_FOR_RESERVATION_OPTION, };
	private static final List<String> PARK_INFORMATION_SCREEN_MENU_OPTIONS_LIST = Arrays
			.asList(PARK_INFORMATION_SCREEN_MENU_OPTIONS);
	private static final String[] CAMPGROUND_SUBMENU = { SEARCH_FOR_RESERVATION_OPTION };
	private static final List<String> CAMPGROUND_SUBMENU_LIST = Arrays.asList(CAMPGROUND_SUBMENU);

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword(System.getenv("DB_PASSWORD"));

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	private CampgroundCLI(DataSource datasource) {
		this.menu = new Menu(System.in, System.out);
		parkDAO = new JDBCParkDAO(datasource);
		campgroundDAO = new JDBCCampgroundDAO(datasource);
		siteDAO = new JDBCSiteDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
	}

	private void run() {
		boolean run = true;
		while (run) {
			System.out.println(MAIN_MENU_OPTION_PARKS);
			// Main menu, pick a park to enter sub menu
			Park selectedPark = menu.getChoiceFromOptions(parkDAO.getAllParks(), "Exit", "Q");
			if (selectedPark == null) {
				run = false;
			} else {
				campgroundDAO.setParkCampgrounds(selectedPark);
				// Print out park information
				System.out.println(selectedPark.getInfo());

				// Handle park information screen menu choice
				String parkInformationChoice;
				// Sub-menu two, view Campgrounds search for reservations
				while ((parkInformationChoice = menu.getChoiceFromOptions(PARK_INFORMATION_SCREEN_MENU_OPTIONS_LIST,
						RETURN_TO_PREVIOUS_SCREEN_OPTION)) != null) {

					System.out.println(parkInformationChoice);
					if (parkInformationChoice.equals(VIEW_CAMPGROUND_OPTION)) {

						String result = "\nPark Campgrounds\n";
						result += selectedPark.getName() + "Park Campgrounds\n";
						System.out.println(selectedPark.getCampgroundInfo());
						String selectedChoice; // Decides whether they want to go back or search for reservation
						while ((selectedChoice = menu.getChoiceFromOptions(CAMPGROUND_SUBMENU_LIST,
								RETURN_TO_PREVIOUS_SCREEN_OPTION)) != null) {
							System.out.println("Search for Campground Reservation");
							Campground c;
							while ((c = menu.getChoiceFromOptions(selectedPark.getCampgrounds())) != null) {
								Long reservevationConfirmation = makeReservation(c);
								if (reservevationConfirmation != null) {
									System.out.format("The reservation has been made and the confirmation id is {%s}\n",
											reservevationConfirmation);
								}
							}
						}
					}
				}
			}
		}
	}

	private Long makeReservation(Campground c) {

		Long reservationID = null;
		boolean makingReservation = true;
		while (makingReservation) {
			Reservation reservationRequest = getReservationRequest();
			List<Site> availableSites = getAvailableSites(reservationRequest, c);
			printReservationChoices(availableSites, reservationRequest, c);

			// Prompts user and stores dates and information regarding their stay
			System.out.print("What site should be reserved? (enter 0 to cancel)? ");
			Scanner sc = new Scanner(System.in);
			String selectedSiteNumber = sc.nextLine();
			if (!selectedSiteNumber.equals("0")) {
				Site selectedSite = null;
				for (Site s : availableSites) {
					if (s.getSite_number() == Integer.parseInt(selectedSiteNumber)) {
						selectedSite = s;
					}
				}
				if (selectedSite != null) {

					System.out.print("What name should the reservation be made under? ");
					String reservationName = sc.nextLine();
					reservationRequest.setName(reservationName);
					reservationID = reservationDAO.makeReservation(reservationRequest, selectedSite);
				}
			}
			makingReservation = false;
		}
		return reservationID;
	}

	private Reservation getReservationRequest() {
		LocalDate arrivalDate = getALocalDate("Enter the arrival date (MM/DD/YYYY): ");
		LocalDate departureDate = getALocalDate("Enter the departure date (MM/DD/YYYY): ");

		return new Reservation(arrivalDate, departureDate);
	}

	private LocalDate getALocalDate(String userPrompt) {
		Scanner sc = new Scanner(System.in);
		LocalDate result = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		while (result == null) {
			System.out.println("Enter the start date (MM/DD/YYYY): ");
			try {
				result = LocalDate.parse(sc.nextLine(), formatter);

			} catch (Exception e) {
				System.out.println("Invalid date. Please try again");
			}
		}
		return result;
	}

	private List<Site> getAvailableSites(Reservation r, Campground c) {

		// Makes a list of available site objects
		List<Integer> availableSitesByID = reservationDAO.sitesAvailableForDateRange(c, r.getStartDate(),
				r.getEndDate());

		List<Site> availableSites = new ArrayList<>();

		for (Integer i : availableSitesByID) {
			availableSites.add(siteDAO.getSiteForSiteId(i));
		}
		return availableSites;
	}

	private void printReservationChoices(List<Site> sites, Reservation r, Campground c) {
		BigDecimal cost = c.getDaily_fee().multiply(BigDecimal.valueOf(r.getDuration()));
		String header = String.format("%-10s %-10s %-15s %-10s %-10s %-10s", "Site No.", "Max Occup.", "Accessible?",
										"RV Len", "Utility", "Cost");
		System.out.println(header);
		for (Site s : sites) {
			String accessible = s.isAccessible() ? "Yes" : "No";
			String utilities = s.isAccessible() ? "Yes" : "N/A";
			String rvLenth = s.getMax_rv_length() > 0 ? Integer.toString(s.getMax_rv_length()) : "N/A";
			System.out.format("%-10s %-10s %-15s %-10s %-10s %-10s\n", s.getSite_number(), s.getMax_occupancy(),
					accessible, rvLenth, utilities, cost);
		}

	}
}
