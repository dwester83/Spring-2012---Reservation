/*
 * Author: Daniel Wester
 *
 * Date: 05/06/2012
 *
 * Course: Comp 2243-01, Spring 2012
 *
 * Assignment: PGM5
 *
 * Description: This class will define a seating plan with first, buisness, economy seats.
 * It will also check if seating classes are full, and represent the seating plan in a string.
 */

/**
 * Represents a seating plan with the ability to set first class, buisness class, and economy class seats.
 *
 * @author Daniel Wester
 * @version 05/06/2012
 */
public class SeatingPlan {
	
	static final int NUMBER_OF_SEATS_LEFT_CENTER_AISLE = 3;
	static final int NUMBER_OF_SEATS_RIGHT_CENTER_AISLE = 3;
	static final int NUMBER_OF_ROWS = 13;
	static final int LAST_FIRST_CLASS_ROW = 2;
	static final int LAST_BUISNESS_CLASS_ROW = 7;
	static final int STARTING_ROW = 1;
	static final char STARTING_LETTER = 'A';
	
	private char[][] seats;
	
	/**
	 * Default constructor will set the size of the seating array and call to the resetSeats method.
	 */
	public SeatingPlan() {
		this.seats = new char[NUMBER_OF_ROWS][NUMBER_OF_SEATS_LEFT_CENTER_AISLE + NUMBER_OF_SEATS_RIGHT_CENTER_AISLE];
		resetSeats();		
	}
	
	/**
	 * Constructor will initalize array values to *.
	 */
	public void resetSeats() {
		for (int i = 0; i < seats.length; i++)
			for (int j = 0; j < seats[i].length; j++)
				this.seats[i][j] = '*';
	}
	
	/**
	 * Sets a first class seat.
	 * @param rowInput int represents the row number being input.
	 * @param charInput char represents the letter being input.
	 * @throws NumberFormatException when the input isn't a first class seat or the seat is already taken.
	 */
	public void setFirstClass(int rowInput, char charInput) {
		int row = rowInput - STARTING_ROW;
		int letter = charInput - STARTING_LETTER;
		checkSeatingSize(rowInput,charInput);
		if (row >= LAST_FIRST_CLASS_ROW) {
			throw new NumberFormatException ("(" + rowInput + "," + charInput + ") is not a first class seat. Please choose a different seat.");
		} else if (seats[row][letter] == 'X') {
			throw new NumberFormatException ("Seat (" + rowInput + "," + charInput + ") is already taken. Please choose a different seat.");
		} else {
			this.seats[row][letter] = 'X';
		}
	}
	
	/**
	 * Sets a business class seat.
	 * @param rowInput int represents the row number being input.
	 * @param charInput char represents the letter being input.
	 * @throws NumberFormatException when the input isn't a business class seat or the seat is already taken.
	 */
	public void setBusinessClass(int rowInput, char charInput) {
		int row = rowInput - STARTING_ROW;
		int letter = charInput - STARTING_LETTER;
		checkSeatingSize(rowInput,charInput);
		if ((row < LAST_FIRST_CLASS_ROW) || (row >= LAST_BUISNESS_CLASS_ROW)) {
			throw new NumberFormatException ("(" + rowInput + "," + charInput + ") is not a business class seat. Please choose a different seat.");
		} else if (seats[row][letter] == 'X') {
			throw new NumberFormatException ("Seat (" + rowInput + "," + charInput + ") is already taken. Please choose a different seat.");
		} else {
			this.seats[row][letter] = 'X';
		}
	}
	
	/**
	 * Sets a economy class seat.
	 * @param rowInput int represents the row number being input.
	 * @param charInput char represents the letter being input.
	 * @throws NumberFormatException when the input isn't a economy class seat or the seat is already taken.
	 */
	public void setEconomyClass(int rowInput, char charInput) {
		int row = rowInput - STARTING_ROW;
		int letter = charInput - STARTING_LETTER;
		checkSeatingSize(rowInput,charInput);
		if (row < LAST_BUISNESS_CLASS_ROW) {
			throw new NumberFormatException ("(" + rowInput + "," + charInput + ") is not a economy class seat. Please choose a different seat.");
		} else if (seats[row][letter] == 'X') {
			throw new NumberFormatException ("Seat (" + rowInput + "," + charInput + ") is already taken. Please choose a different seat.");
		} else {
			this.seats[row][letter] = 'X';
		}
	}
	
	/**
	 * Checks to make sure the input is a valid seat in the array.
	 * @param rowInput int represents the row number being input.
	 * @param charInput char represents the letter being input.
	 * @throws ArrayIndexOutOfBoundsException if the seat isn't in a location that is valid.
	 */
	private void checkSeatingSize (int rowInput, char charInput) {
		int row = rowInput - STARTING_ROW;
		int letter = charInput - STARTING_LETTER;
		char endingLetter = (char)((STARTING_LETTER + NUMBER_OF_SEATS_LEFT_CENTER_AISLE) + NUMBER_OF_SEATS_RIGHT_CENTER_AISLE - 1);
		if (row < 0 || letter < 0 || letter >= (NUMBER_OF_SEATS_LEFT_CENTER_AISLE + NUMBER_OF_SEATS_RIGHT_CENTER_AISLE) || row >= NUMBER_OF_ROWS)
			throw new ArrayIndexOutOfBoundsException ("(" + rowInput + "," + charInput + ") is not a valid seat. Valid seats are [" + STARTING_ROW + "-" + NUMBER_OF_ROWS + "],[" + STARTING_LETTER + "-" + endingLetter + "].");
	}
	
	/**
	 * Checks if the first class seats are full.
	 * @return boolean if all the first class seats are full.
	 */
	public boolean checkFirstClassFull() {
		for (int i = 0; i < LAST_FIRST_CLASS_ROW; i++)
			for (int j = 0; j < NUMBER_OF_SEATS_LEFT_CENTER_AISLE + NUMBER_OF_SEATS_RIGHT_CENTER_AISLE; j++)
				if (seats[i][j] == '*')
					return false;
		return true;
	}
	
	/**
	 * Checks if the business class seats are full.
	 * @return boolean if all the first class seats are full.
	 */
	public boolean checkBusinessClassFull() {
		for (int i = LAST_FIRST_CLASS_ROW; i < LAST_BUISNESS_CLASS_ROW; i++)
			for (int j = 0; j < NUMBER_OF_SEATS_LEFT_CENTER_AISLE + NUMBER_OF_SEATS_RIGHT_CENTER_AISLE; j++)
				if (seats[i][j] == '*')
					return false;
		return true;
	}
	
	/**
	 * Checks if the economy class seats are full.
	 * @return boolean if all the first class seats are full.
	 */
	public boolean checkEconomyClassFull() {
		for (int i = LAST_BUISNESS_CLASS_ROW; i < NUMBER_OF_ROWS; i++)
			for (int j = 0; j < NUMBER_OF_SEATS_LEFT_CENTER_AISLE + NUMBER_OF_SEATS_RIGHT_CENTER_AISLE; j++)
				if (seats[i][j] == '*')
					return false;
		return true;
	}
	
	/**
	 * Provides a String represention of the seating layout.
	 * @return String showing empty and full seats in the seating layout.
	 */
	public String toString() {
		String output = "	";
		
		char alphabet = STARTING_LETTER;
		for (int i = 0; i < NUMBER_OF_SEATS_LEFT_CENTER_AISLE + NUMBER_OF_SEATS_RIGHT_CENTER_AISLE; i++) {
			if (NUMBER_OF_SEATS_LEFT_CENTER_AISLE == i)
				output = String.format(output + "  ");
			output = String.format(output + "%2s", alphabet);
			alphabet++;
		}
		
		output = String.format(output + "%n%n");
		
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			output = String.format(output + "Row %-5d", i+1);
			for (int j = 0; j < NUMBER_OF_SEATS_LEFT_CENTER_AISLE + NUMBER_OF_SEATS_RIGHT_CENTER_AISLE; j++) {
				if (NUMBER_OF_SEATS_LEFT_CENTER_AISLE == j)
					output = String.format(output + "  ");
				output = String.format(output + seats[i][j] + " ");
			}
			output = String.format(output + "%n");
		}
		output = String.format(output + "%n* -- available seat%nX -- occupied seat");
		return output;
	}
}