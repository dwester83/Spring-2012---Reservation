/*
 * Author: Daniel Wester
 * 
 * Date: 05/06/2012
 * 
 * Course: Comp 2243-01, Spring 2012
 * 
 * Assignment: PGM5
 * 
 * Description: This program will run a reservation system that can set seats.
 * It will allow someone to add first, buisness, and economy class seats.
 * It will also let the person reset the seating and display any errors that occur.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReservationSystem extends JFrame {
	
	SeatingPlan seats = new SeatingPlan();
	
	JTextArea descriptionJTA, seatingJTA, errorJTA;
	JLabel rowJL, letterJL;
	JButton setSeatJB, resetJB, exitJB;
	JRadioButton firstClassJRB, businessClassJRB, economyClassJRB;
	ButtonGroup classBG;
	JComboBox rowJCB, letterJCB;
	JPanel northPanel, centerPanel, centerLeftPanel, southPanel, southBottomPanel;
	
	char alphabet = seats.STARTING_LETTER;
	String[] seatsLetter = new String [(seats.NUMBER_OF_SEATS_LEFT_CENTER_AISLE + seats.NUMBER_OF_SEATS_RIGHT_CENTER_AISLE)];
	String[] seatsRow = new String [seats.NUMBER_OF_ROWS];
	
	public ReservationSystem() {
		
		//Description Fields
		descriptionJTA = new JTextArea(" Welcome, this program is used to book seats for the airline.\n");
		descriptionJTA.setLineWrap(true);
		descriptionJTA.setEditable(false);
		descriptionJTA.setBackground(null);
		seatingJTA = new JTextArea(seats.toString());
		seatingJTA.setFont(new Font("monospaced", Font.PLAIN, 12));
		seatingJTA.setEditable(false);
		seatingJTA.setBackground(null);
		rowJL = new JLabel("Row");
		rowJL.setPreferredSize(new Dimension(80,30));
		letterJL = new JLabel("Letter");
		letterJL.setPreferredSize(new Dimension(80,30));
		errorJTA = new JTextArea();
		errorJTA.setForeground(Color.red);
		errorJTA.setBackground(null);
		errorJTA.setEditable(false);
		
		//Radio Buttons
		firstClassJRB = new JRadioButton ("First Class Seat");
		businessClassJRB = new JRadioButton ("Business Class Seat");
		economyClassJRB = new JRadioButton ("Economy Class Seat");
		firstClassJRB.addActionListener(new RadioButtonSelect());
		businessClassJRB.addActionListener(new RadioButtonSelect());
		economyClassJRB.addActionListener(new RadioButtonSelect());
		classBG = new ButtonGroup();
		classBG.add(firstClassJRB);
		classBG.add(businessClassJRB);
		classBG.add(economyClassJRB);
		
		//Combo Box Setup
		rowJCB = new JComboBox();
		letterJCB = new JComboBox();
		rowJCB.setPreferredSize(new Dimension(80,30));
		letterJCB.setPreferredSize(new Dimension(80,30));
		
		for (int i = 0; i < (seats.NUMBER_OF_SEATS_LEFT_CENTER_AISLE + seats.NUMBER_OF_SEATS_RIGHT_CENTER_AISLE); i++) {
			seatsLetter[i] = String.format("" + alphabet);
			alphabet++;
		}
		for (int i = 0; i < seats.NUMBER_OF_ROWS; i++) {
			seatsRow[i] = String.format("" + (i + 1));
		}
		
		//Selecting First Class at the start.
		firstClassJRB.setSelected(true);
		for (int i = 0; i < seats.LAST_FIRST_CLASS_ROW; i++)
			rowJCB.addItem(seatsRow[i]);
		for (int i = 0; i < (seats.NUMBER_OF_SEATS_LEFT_CENTER_AISLE + seats.NUMBER_OF_SEATS_RIGHT_CENTER_AISLE); i++)
			letterJCB.addItem(seatsLetter[i]);
		
		//Buttons setup
		setSeatJB = new JButton ("Set Seat");
		setSeatJB.addActionListener(new SeatButtonHandler());
		resetJB = new JButton ("Reset");
		resetJB.addActionListener(new ResetButtonHandler());
		exitJB = new JButton ("Exit");
		exitJB.addActionListener(new ExitButtonHandler());
		
		//Building the GUI
		Container pane = getContentPane();
		northPanel = new JPanel(new GridLayout (1,1));
		centerPanel = new JPanel(new GridLayout (1,2));
		centerLeftPanel = new JPanel(new FlowLayout (FlowLayout.LEFT));
		southPanel = new JPanel(new GridLayout (2,1));
		southBottomPanel = new JPanel(new GridLayout (1,3));
		northPanel.add(descriptionJTA);
		centerLeftPanel.add(firstClassJRB);
		centerLeftPanel.add(businessClassJRB);
		centerLeftPanel.add(economyClassJRB);
		centerLeftPanel.add(rowJL);
		centerLeftPanel.add(letterJL);
		centerLeftPanel.add(rowJCB);
		centerLeftPanel.add(letterJCB);
		centerPanel.add(centerLeftPanel);
		centerPanel.add(seatingJTA);
		southBottomPanel.add(setSeatJB);
		southBottomPanel.add(resetJB);
		southBottomPanel.add(exitJB);
		southPanel.add(errorJTA);
		southPanel.add(southBottomPanel);
		
		//Display the GUI
		setLayout(new BorderLayout());
		setSize(400,430);
		setTitle("Airline Reservation Program");
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	//Seat Button
	private class SeatButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			int rowLoc;
			char letterLoc;
			
			rowLoc = Integer.parseInt((String)rowJCB.getSelectedItem());
			letterLoc = ((String)letterJCB.getSelectedItem()).charAt(0);
			
			try {
				if (firstClassJRB.isSelected()) {
					seats.setFirstClass(rowLoc,letterLoc);
					if (seats.checkFirstClassFull()) {
						firstClassJRB.setEnabled(false);
						setSeatJB.setEnabled(false);
					}
				} else if (businessClassJRB.isSelected()) {
					seats.setBusinessClass(rowLoc,letterLoc);
					if (seats.checkBusinessClassFull()) {
						businessClassJRB.setEnabled(false);
						setSeatJB.setEnabled(false);
					}
				} else if (economyClassJRB.isSelected()) {
					seats.setEconomyClass(rowLoc,letterLoc);
					if (seats.checkEconomyClassFull()) {
						economyClassJRB.setEnabled(false);
						setSeatJB.setEnabled(false);
					}
				}
			} catch (ArrayIndexOutOfBoundsException aie) {
				errorJTA.setText(" " + aie.getMessage());
				return;
			} catch (NumberFormatException nfe) {
				errorJTA.setText(" " + nfe.getMessage());
				return;
			} catch (Exception exc) {
				errorJTA.setText(" You hit an error that wasn't found. E-mail Dan Wester to fix it.");
				return;
			}
			
			errorJTA.setText("");
			seatingJTA.setText(seats.toString());
			
		}
	}
	
	//Radio Button Listener
	private class RadioButtonSelect implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			rowJCB.removeAllItems();
			letterJCB.removeAllItems();
			setSeatJB.setEnabled(true);
			errorJTA.setText("");
			if (firstClassJRB.isSelected()) {
				for (int i = 0; i < seats.LAST_FIRST_CLASS_ROW; i++)
					rowJCB.addItem(seatsRow[i]);
				for (int i = 0; i < (seats.NUMBER_OF_SEATS_LEFT_CENTER_AISLE + seats.NUMBER_OF_SEATS_RIGHT_CENTER_AISLE); i++)
					letterJCB.addItem(seatsLetter[i]);
			} else if (businessClassJRB.isSelected()) {
				for (int i = seats.LAST_FIRST_CLASS_ROW; i < seats.LAST_BUISNESS_CLASS_ROW; i++)
					rowJCB.addItem(seatsRow[i]);
				for (int i = 0; i < (seats.NUMBER_OF_SEATS_LEFT_CENTER_AISLE + seats.NUMBER_OF_SEATS_RIGHT_CENTER_AISLE); i++)
					letterJCB.addItem(seatsLetter[i]);
			} else if (economyClassJRB.isSelected()) {
				for (int i = seats.LAST_BUISNESS_CLASS_ROW; i < seats.NUMBER_OF_ROWS; i++)
					rowJCB.addItem(seatsRow[i]);
				for (int i = 0; i < (seats.NUMBER_OF_SEATS_LEFT_CENTER_AISLE + seats.NUMBER_OF_SEATS_RIGHT_CENTER_AISLE); i++)
					letterJCB.addItem(seatsLetter[i]);
			}
		}
	}
	
	//Reset Button
	private class ResetButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			seats.resetSeats();
			firstClassJRB.setEnabled(true);
			businessClassJRB.setEnabled(true);
			economyClassJRB.setEnabled(true);
			setSeatJB.setEnabled(true);
			firstClassJRB.setSelected(true);
			rowJCB.removeAllItems();
			letterJCB.removeAllItems();
			errorJTA.setText("");
			for (int i = 0; i < seats.LAST_FIRST_CLASS_ROW; i++)
				rowJCB.addItem(seatsRow[i]);
			for (int i = 0; i < (seats.NUMBER_OF_SEATS_LEFT_CENTER_AISLE + seats.NUMBER_OF_SEATS_RIGHT_CENTER_AISLE); i++)
				letterJCB.addItem(seatsLetter[i]);
			seatingJTA.setText(seats.toString());
		}
	}
	
	//Exit Button
	private class ExitButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			System.exit(0);
		}
	}
	
	//Main
	public static void main (String [] args) {
		new ReservationSystem();
	}
}