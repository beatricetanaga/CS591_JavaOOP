package gui.Manager;

import javax.swing.*;

import account.*;
import manager.*;
import transaction.*;
import database.*;
import gui.Client.LoginPage;
import bankATM.*;
import bankATM.Currency;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.*;

public class ManagerHomePage {

	
	public static Manager bankManager = null;

	public static void createSettings() {
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

		JButton changeDateTime = new JButton("Change Date and Time");
		changeDateTime.setBounds(188, 120, 180, 45);
		panel.add(changeDateTime);

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

	public static void createManageAccounts() {
		JFrame frame = new JFrame("Manage Accounts");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);
		JButton manageAccounts = new JButton("Manage Accounts");
		manageAccounts.setBounds(180, 50, 200, 45);
		panel.add(manageAccounts);

		JButton viewAccounts = new JButton("View Clients");

		viewAccounts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<Client> clients = bankManager.getClients();
				Client client = clients.get(0);
				JFrame approved = new JFrame("Bank Clients List:");
				approved.setSize(650, 500);
				approved.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				approved.setBounds(100, 100, 491, 310);
				approved.getContentPane().setLayout(null);

				JTextArea textField = new JTextArea();
				textField.setEditable(false);

				String str = "";
				for (int i = 0; i < clients.size(); ++i)
					str += "Client " + (i + 1) + ": " + client.getPerson() + ". Overall balance: "
							+ client.getOverallBalance() + " " + Currency.USD + "\n";
				textField.setText(str);

				JScrollPane scroll = new JScrollPane(textField);
				scroll.setBounds(10, 11, 455, 249); // <-- THIS

				approved.getContentPane().add(scroll);

				approved.setVisible(true);

			}

		});
		viewAccounts.setBounds(188, 120, 180, 45);
		panel.add(viewAccounts);

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

	public static void createLoans() {
		JFrame frame = new JFrame("Manage Loans");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

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

		JButton loanRequests = new JButton("Loan Requests");
		loanRequests.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

				ManageLoans.loanRequests();
			}

		});
		// loanRequests.setBounds(50,20,75,50);
		// panel.add(loanRequests);
		loanRequests.setBounds(188, 120, 180, 45);
		panel.add(loanRequests);

		JButton collectLoanMoney = new JButton("Collect Loan Money");
		collectLoanMoney.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManageLoans.collectLoanMoney();
			}

		});
		collectLoanMoney.setBounds(188, 170, 180, 50);
		panel.add(collectLoanMoney);

		JButton viewLoans = new JButton("View Loans");
		viewLoans.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				ManageLoans.viewLoans();
			}

		});
		viewLoans.setBounds(188, 225, 180, 50);
		panel.add(viewLoans);
		frame.setVisible(true);

	}

	public static void createServiceFee() {
		JFrame frame = new JFrame("Manage Service Fees");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

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

		////////////////
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		String[] buttonFeeTypes = { "Withdraw Fee", "Transfer Fee", "Deposit Fee", "Open/Close Account Fee",
				"Checking Account Transaction Fees" };
		String[] feeTypes = { "withdraw", "transfer", "deposit", "account", "checking" };

		for (int i = 0; i < buttonFeeTypes.length; i++) {
			JButton chooseFeeButton = new JButton(buttonFeeTypes[i]);
			String type = feeTypes[i];
			chooseFeeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					ManageServiceFees.updateFee(type);

				}

			});
			panel.add(chooseFeeButton);
		}

		JScrollPane scroll = new JScrollPane(panel);

		frame.getContentPane().add(scroll);
		/////////////

		frame.setVisible(true);
	}

	public static void createTransactions() {
		JFrame frame = new JFrame("Transactions");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

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

		JButton viewAll = new JButton("View All Transactions");
		viewAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManageTransactions.viewAllTransactions();

			}

		});
		viewAll.setBounds(180, 120, 200, 40);
		panel.add(viewAll);

		JButton viewSpecific = new JButton("View Specific Day Transaction");
		viewSpecific.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManageTransactions.specificTransaction();

			}

		});
		viewSpecific.setBounds(180, 175, 230, 40);
		panel.add(viewSpecific);

		frame.setVisible(true);
	}

	private static void createStocks() {
		JFrame frame = new JFrame("Manage Stocks");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

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

		JButton addStock = new JButton("Add New Stock");
		addStock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManageStocks.addNewStock();

			}

		});
		addStock.setBounds(180, 175, 230, 40);
		panel.add(addStock);

		JButton updateStock = new JButton("Update Stock");
		updateStock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManageStocks.updateStock();

			}

		});
		updateStock.setBounds(180, 235, 230, 40);
		panel.add(updateStock);

		frame.setVisible(true);
	}

	public static void createInterest() {
		JFrame frame = new JFrame("Manage Interests");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

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

		////////////////
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		String[] buttonRateTypes = { "Loan Interest", "Savings Interest" };
		String[] feeTypes = { "loan", "savings" };

		for (int i = 0; i < buttonRateTypes.length; i++) {
			JButton chooseFeeButton = new JButton(buttonRateTypes[i]);
			String type = feeTypes[i];
			chooseFeeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					ManageInterest.updateInterest(type);

				}

			});
			panel.add(chooseFeeButton);
		}

		JScrollPane scroll = new JScrollPane(panel);

		frame.getContentPane().add(scroll);
		/////////////

		frame.setVisible(true);
	}

	public static void placeButtons(Manager m) {
		bankManager = m;
		JFrame frame = new JFrame("Bank Account");
		// Setting the width and height of frame
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Creating panel. This is same as a div tag in HTML We can create several
		 * panels and add them to specific positions in a JFrame. Inside panels we can
		 * add text fields, buttons and other components.
		 */
		JPanel panel = new JPanel();
		// adding panel to frame
		frame.add(panel);
		
		panel.setLayout(null);

		// setBounds() method takes in four parameters: x-coordinate, y-coordinate,
		// width, height
		JButton managerHomePage = new JButton("Manager Home Page");
		managerHomePage.setBounds(180, 50, 200, 45);
		panel.add(managerHomePage);

		JButton settings = new JButton("Settings");
		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createSettings();

			}

		});
		settings.setBounds(400, 50, 100, 20);
		panel.add(settings);

		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(400, 75, 100, 20);
		panel.add(exitButton);
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoginPage.placeButtons();
			}

		});

		JButton accounts = new JButton("Accounts");
		// upon clicking accounts, go to the Manage Accounts page (implement that
		// function within this class)
		// after clicking any button in Manage Accounts, close the frame for Manage
		// Accounts (but keep this one open) and
		// create new classes after that. do the same for the other main buttons
		accounts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createManageAccounts();

			}

		});
		accounts.setBounds(50, 120, 200, 35);
		panel.add(accounts);

		JButton loans = new JButton("Loans");
		loans.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createLoans();

			}

		});
		loans.setBounds(50, 160, 200, 35);
		panel.add(loans);

		JButton serviceFee = new JButton("Service Fee");
		serviceFee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createServiceFee();

			}

		});
		serviceFee.setBounds(50, 200, 200, 35);
		panel.add(serviceFee);

		JButton transactions = new JButton("Transactions");
		transactions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createTransactions();

			}

		});
		transactions.setBounds(350, 120, 200, 35);
		panel.add(transactions);

		JButton stocks = new JButton("Stocks");
		stocks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createStocks();

			}

		});
		stocks.setBounds(350, 160, 200, 35);
		panel.add(stocks);

		JButton interestRate = new JButton("Interest Rate");
		interestRate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createInterest();

			}

		});
		interestRate.setBounds(350, 200, 200, 35);
		panel.add(interestRate);
		frame.setVisible(true);
	}

}
