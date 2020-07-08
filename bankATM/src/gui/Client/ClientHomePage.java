package gui.Client;

import account.*;
import manager.*;
import transaction.*;
import database.*;
import gui.Transfer.*;
import gui.Withdraw.Withdraw_Page;
import gui.Loan.*;
import gui.Deposite.*;
import gui.Account.*;
import gui.MyTrans.*;
import gui.Stock.Stock_Page;
import bankATM.*;
import bankATM.Currency;
import javax.swing.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.*;

public class ClientHomePage {

	public static Client client = null;

	public static void setup() {
		DBBank dbBankObj = new DBBank();
		Bank bank = null;
		try {
			bank = dbBankObj.retrieveById("testBank");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LoginPage.bank = bank;
		DBClient dbobj = new DBClient();
		try {
			client = dbobj.retrieveById("c7577a78-ec82-4e04-85c6-468f029617e6");
			client = dbobj.retrieveById("c7577a78-ec82-4e04-85c6-468f02961888");
			client = dbobj.retrieveById("c7577a78-ec82-4e04-85c6-468f029617e6");
			client = dbobj.retrieveById("c7577a78-ec82-4e04-85c6-468f02961899");
			client = dbobj.retrieveById("c7577a78-ec82-4e04-85c6-468f029617e6");			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		setup();
		placeButtons(client);
	}

	public static void placeButtons(Client c) {
		if (client == null) {
			client = c;
		}

		JFrame frame = new JFrame("Client Home");
		// Setting the width and height of frame
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		// adding panel to frame
		frame.add(panel);

		panel.setLayout(null);

		// setBounds() method takes in four parameters: x-coordinate, y-coordinate,
		// width, height
		JButton managerHomePage = new JButton("Client Home Page");
		managerHomePage.setBounds(180, 50, 200, 45);
		panel.add(managerHomePage);

		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoginPage.placeButtons();
			}

		});
		exitButton.setBounds(400, 75, 100, 20);
		panel.add(exitButton);

		JButton accounts = new JButton("Accounts");
		accounts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ClientAccountInfo.displayAccounts();
			}

		});
		accounts.setBounds(50, 120, 200, 35);
		panel.add(accounts);

		JButton loans = new JButton("Loans");
		loans.setBounds(50, 160, 200, 35);
		panel.add(loans);
		loans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Loan_Page.placeComponents();
			}
		});

		JButton serviceFee = new JButton("Withdraw");
		serviceFee.setBounds(50, 200, 200, 35);
		panel.add(serviceFee);
		serviceFee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Withdraw_Page.placeComponents();
			}
		});

		JButton dep = new JButton("Deposit");
		dep.setBounds(350, 120, 200, 35);
		panel.add(dep);
		dep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Deposite_Page.placeComponents();
			}
		});

		JButton transfer = new JButton("Transfer");
		transfer.setBounds(350, 160, 200, 35);
		panel.add(transfer);
		transfer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Transfer_Page.placeComponents();
			}

		});

		JButton stocks = new JButton("Stocks");
		stocks.setBounds(350, 200, 200, 35);
		panel.add(stocks);
		stocks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Stock_Page.placeComponents();
			}

		});

		JButton transactionHistory = new JButton("Transaction History");
		transactionHistory.setBounds(180, 260, 200, 35);
		panel.add(transactionHistory);
		transactionHistory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// Mytrans.placeComponents();
				ALL_Trans.placeComponents(null);
			}

		});
		frame.setVisible(true);
	}

	private static void createSettings() {
		JFrame frame = new JFrame("Settings");
		frame.setSize(650, 500);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);

		panel.setLayout(null);
		JButton settings = new JButton("Settings");
		settings.setBounds(180, 50, 200, 45);
		panel.add(settings);

		// JButton changeDateTime = new JButton("Change Date and Time");
		// changeDateTime.setBounds(188, 120, 180, 45);
		// panel.add(changeDateTime);

		JTextField changeDateTime = new JTextField(20);
		changeDateTime.setBounds(240, 115, 150, 25);
		JLabel enterDateTime = new JLabel("Enter new date/time: ");
		enterDateTime.setBounds(110, 100, 150, 50);
		enterDateTime.setLabelFor(changeDateTime);
		panel.add(changeDateTime);
		panel.add(enterDateTime);

		JButton home = new JButton("Home");
		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

			}

		});
		home.setBounds(50, 20, 75, 50);
		panel.add(home);

		frame.setVisible(true);

	}

}
