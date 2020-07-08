package gui.Manager;

import javax.swing.*;

import bankATM.*;
import bankATM.Currency;
import database.*;
import manager.*;
import transaction.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Date;
import java.util.*;

public class ManageTransactions {

	public static void viewAllTransactions() {
		JFrame frame = new JFrame("View All Transactions");
		frame.setSize(650, 500);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

			}

		});
		back.setBounds(50, 20, 75, 50);
		panel.add(back);

		JLabel displayCurrent = new JLabel("Display Recent Transactions: ");
		displayCurrent.setSize(650, 500);

		JTextArea textField = new JTextArea();

		DBTransaction tObj = new DBTransaction();
		ArrayList<Transaction> transactions = null;
		try {
			transactions = tObj.retrieveTransactions();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str = "";
		for (Transaction t : transactions) {
			str += t.getId() + ": \n" + t + "\n\n";
		}
		textField.setText(str);

		JScrollPane scroll = new JScrollPane(textField);
		scroll.setBounds(10, 11, 455, 249); // <-- THIS
		frame.getContentPane().add(scroll);
		frame.setLocationRelativeTo(null);

		panel.add(displayCurrent);

		frame.setVisible(true);

	}

	public static void specificTransaction() {
		JFrame frame = new JFrame("View All Transactions");
		frame.setSize(650, 500);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

			}

		});
		back.setBounds(50, 20, 75, 50);
		panel.add(back);

		Date specifiedDate = new Date(2020, 01, 01);
		JButton chooseDates = new JButton("Choose date");
		// all the possible dates are going to need to be connected from the back end
		// here, possibly in the form of multiple JButtons
		chooseDates.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame frame = new JFrame("View All Transactions");
				frame.setSize(650, 500);
				// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel panel = new JPanel();
				frame.add(panel);
				panel.setLayout(null);

				JButton back = new JButton("Back");
				back.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

					}

				});
				back.setBounds(50, 20, 75, 50);
				panel.add(back);

				JLabel displayCurrent = new JLabel("Display Transactions for Specified Date: ");
				displayCurrent.setBounds(200, 70, 150, 40);

				JTextArea textField = new JTextArea();

				DBTransaction tObj = new DBTransaction();
				ArrayList<Transaction> transactions = null;
				try {
					transactions = tObj.retrieveTransactions();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String str = "";
				for (Transaction t : transactions) {
					if (t.getCreated().equals(specifiedDate)) {
						str += t.getId() + ": \n" + t + "\n\n";
					}
				}
				textField.setText(str);

				JScrollPane scroll = new JScrollPane(textField);
				scroll.setBounds(10, 11, 455, 249); // <-- THIS
				frame.getContentPane().add(scroll);
				frame.setLocationRelativeTo(null);

				panel.add(displayCurrent);

				frame.setVisible(true);

			}

		});
		chooseDates.setBounds(180, 70, 150, 40);
		panel.add(chooseDates);

		frame.setVisible(true);
	}

}
