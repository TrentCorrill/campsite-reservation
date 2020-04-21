package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;
	private String escape = null;
	private boolean exitChosen = false;
	private String escapeString = "";

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public <T> T getChoiceFromOptions(List<T> options) {
		T choice = null;
		exitChosen = false;
		while (choice == null && !exitChosen) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		escape = null; // Set escape to null so previous escape options don't persist
		return choice;
	}

	public <T> T getChoiceFromOptions(List<T> options, String escapeString, String escape) {
		this.escape = escape;
		return this.getChoiceFromOptions(options, escapeString);
	}

	public <T> T getChoiceFromOptions(List<T> options, String escapeString) {
		this.escapeString = escapeString;
		return this.getChoiceFromOptions(options);
	}

	private <T> T getChoiceFromUserInput(List<T> options) {
		T choice = null;
		String userInput = in.nextLine();

		if (userInput.equalsIgnoreCase(escape)) {

			exitChosen = true;

		} else {
			try {
				int selectedOption = Integer.valueOf(userInput);
				if (selectedOption > 0 && selectedOption <= options.size()) {
					choice = options.get(selectedOption - 1);
				}
			} catch (NumberFormatException e) {
				// Eat the exception, an error message will be displayed below since choice will
				// Be null
			}
			if (choice == null) {
				out.println("\n*** " + userInput + " is not a valid option ***\n");
			}
		}
		return choice;
	}

	private <T> void displayMenuOptions(List<T> options) {
		out.println();
		int optionNum = 0;
		for (int i = 0; i < options.size(); i++) {
			optionNum = i + 1;
			out.println(optionNum + ") " + options.get(i));
		}
		if (escape == null) {
			escape = "" + (optionNum + 1);
		}
		out.println(this.escape + ") " + escapeString);
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
}
