package gui.Client;

import account.*;
import manager.*;
import transaction.*;
import database.*;
import gui.Account.View_Account_Info;
import gui.Withdraw.Withdraw_Account;
import bankATM.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class ClientAccountInfo {
	public static void displayAccounts() {
		JFrame frame = new JFrame("Client Accounts");
		frame.setSize(650, 500);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

		JButton back = new JButton("Home");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				ClientHomePage.placeButtons(null);
			}

		});
		back.setBounds(50, 20, 75, 50);
		panel.add(back);

		JButton openNewAcc = new JButton("Open New Account");
		openNewAcc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createNewAccount();

			}

		});
		openNewAcc.setBounds(400, 20, 175, 50);
		panel.add(openNewAcc);

		DBAccount obj = new DBAccount();
		ArrayList<Account> accList = null;
		try {
			accList = obj.retrieveClientAccounts(ClientHomePage.client);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JLabel displayCurrent = new JLabel("My Accounts: "); // empty for now, fetch all stocks from backend
		displayCurrent.setBounds(200, 70, 150, 40);
		panel.add(displayCurrent);

		panel.setSize(650, 500);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		if (accList != null) {
			int i = 0;
			for (Account acc : accList) {
				i++;
				JButton chooseStockButton = new JButton("\nAccount " + i + ": " + acc.getType() + "\n");

				// this jbutton will lead to the update stock page
				chooseStockButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						View_Account_Info.placeComponents(acc);
					}
				});
				panel.add(chooseStockButton);
			}
		}

		frame.setVisible(true);
	}

	public static void createNewAccount() {
		JFrame frame = new JFrame("Open New Account");
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
            	back.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                ClientHomePage.placeButtons(null);
            }
        });
		back.setBounds(50, 20, 75, 50);
		panel.add(back);

		JButton savings = new JButton("Savings");
		savings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createNewAccPage(Type.SavingsAccount);
			}
		});
		savings.setBounds(190, 120, 150, 40);
		panel.add(savings);

		JButton checking = new JButton("Checking");
		checking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createNewAccPage(Type.CheckingAccount);
			}

		});
		checking.setBounds(190, 170, 150, 40);
		panel.add(checking);

		JButton security = new JButton("Security");
		security.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createNewAccPage(Type.SecurityAccount);

			}

		});
		security.setBounds(190, 220, 150, 40);
		panel.add(security);

		frame.setVisible(true);
	}

	private static void createNewAccPage(Type accType) {
		JFrame frame = new JFrame("New " + accType + " ");
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
				ClientHomePage.placeButtons(null);
			}

		});
		back.setBounds(50, 20, 75, 50);
		panel.add(back);

		JLabel header = new JLabel("New " + accType );
		header.setBounds(200, 40, 200, 40);
		panel.add(header);

		if (accType.equals(Type.SecurityAccount)) {
			JLabel securityAccReminder = new JLabel(
					"Security account only allowed for Clients with minimum $10k in Savings");
			securityAccReminder.setBounds(120, 90, 500, 40);
			panel.add(securityAccReminder);
		}

		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(ClientHomePage.client.getAccountsOfType(accType)!=null) {
						// TODO Auto-generated method stub
						JFrame approved = new JFrame("Already have "+accType);
						approved.setSize(650, 500);
						approved.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						JPanel panel = new JPanel();
						approved.add(panel);
						panel.setLayout(null);
						JLabel successful = new JLabel("Already have "+accType);
						successful.setBounds(200, 150, 300, 100);
						panel.add(successful);
						JButton back = new JButton("Back");
						back.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								approved.dispatchEvent(new WindowEvent(approved, WindowEvent.WINDOW_CLOSING));
								ClientHomePage.placeButtons(null);
							}

						});
						back.setBounds(50, 20, 75, 50);
						panel.add(back);
						approved.setVisible(true);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (accType.equals(Type.CheckingAccount)) {
					try {
						ClientHomePage.client.openCheckingAccount();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (accType.equals(Type.LoansAccount)) {
					ClientHomePage.client.openLoansAccount();
				}else if (accType.equals(Type.SavingsAccount)) {
					try {
						ClientHomePage.client.openSavingsAccount();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if (accType.equals(Type.SecurityAccount)) {
					try {
						ClientHomePage.client.openSecurityAccount();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				// TODO Auto-generated method stub
				JFrame approved = new JFrame("Account Created Successfully");
				approved.setSize(650, 500);
				approved.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel panel = new JPanel();
				approved.add(panel);
				panel.setLayout(null);
				JLabel successful = new JLabel("Created successfully");
				successful.setBounds(200, 150, 300, 100);
				panel.add(successful);
				JButton back = new JButton("Back");
				back.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						approved.dispatchEvent(new WindowEvent(approved, WindowEvent.WINDOW_CLOSING));
						ClientHomePage.placeButtons(null);
					}

				});
				back.setBounds(50, 20, 75, 50);
				panel.add(back);
				approved.setVisible(true);

			}

		});
		create.setBounds(180, 140, 200, 40);
		panel.add(create);

		frame.setVisible(true);
	}

}
