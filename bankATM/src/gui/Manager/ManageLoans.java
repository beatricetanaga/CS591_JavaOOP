package gui.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import account.*;
import manager.*;
import transaction.*;
import database.*;
import gui.Client.*;
import bankATM.*;
import bankATM.Currency;

public class ManageLoans {

	public static void loanRequests() {
		JFrame frame = new JFrame("Loan Requests");
		frame.setSize(650, 500);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		JPanel panel = new JPanel();
//		frame.add(panel);
//		panel.setLayout(null);
		JPanel panel = new JPanel();

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
		
		panel.setSize(650, 500);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		ArrayList<Loan> loans = ManagerHomePage.bankManager.getRequestedLoans();
		if (loans != null) {
			int i = 0;
			for (Loan loan : loans) {
				i++;
				JButton loanRequestButton = new JButton("Loan Request " + (i + 1) + ": amount = " + loan.getAmount()
						+ " Request Date: " + loan.getRequested());

				loanRequestButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						loanRequestWindow(loan);
					}
				});
				panel.add(loanRequestButton);
			}
		}
		System.out.println("loans " + loans.size());
		JScrollPane scroll = new JScrollPane(panel);
//		scroll.setBounds(10, 11, 455, 249);

		frame.getContentPane().add(scroll);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
	}

	private static void loanRequestWindow(Loan loan) {
		JFrame frame = new JFrame("Loan Request ");
		frame.setSize(650, 500);
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

		JButton approve = new JButton("Approve");
		approve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Update Loan Status
				loan.setStatus(Status.Approved);
				loan.updateDB();

				// TODO Auto-generated method stub
				JFrame approved = new JFrame("Loan Approved");
				approved.setSize(650, 500);
				approved.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel panel = new JPanel();
				approved.add(panel);
				panel.setLayout(null);
				
				JButton managerHomePage = new JButton("Request Approved!");
				managerHomePage.setBounds(180, 50, 200, 45);
				panel.add(managerHomePage);
				
				JButton back = new JButton("Back");
				back.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						approved.dispatchEvent(new WindowEvent(approved, WindowEvent.WINDOW_CLOSING));

					}

				});
				back.setBounds(50, 20, 75, 50);
				panel.add(back);
				approved.setVisible(true);
			}

		});
		approve.setBounds(188, 120, 180, 45);
		panel.add(approve);

		JButton reject = new JButton("Reject");
		reject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame frame = new JFrame("Loan Rejected");
				frame.setSize(650, 500);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel panel = new JPanel();
				frame.add(panel);
				panel.setLayout(null);
				JLabel successful = new JLabel("Rejected");
				successful.setFont(new Font("Verdana", 1, 20));
				panel.add(successful);
				JButton back = new JButton("Back");
				back.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						

						// Update Loan Status
						loan.setStatus(Status.Rejected);
						loan.updateDB();

						// TODO Auto-generated method stub
						JFrame approved = new JFrame("Loan Rejected");
						approved.setSize(650, 500);
						approved.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						JPanel panel = new JPanel();
						approved.add(panel);
						panel.setLayout(null);
						
						JButton managerHomePage = new JButton("Request Rejected!");
						managerHomePage.setBounds(180, 50, 200, 45);
						panel.add(managerHomePage);
						
						JButton back = new JButton("Back");
						back.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								approved.dispatchEvent(new WindowEvent(approved, WindowEvent.WINDOW_CLOSING));

							}

						});
						back.setBounds(50, 20, 75, 50);
						panel.add(back);
						approved.setVisible(true);
						
						
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						
					}

				});
				back.setBounds(50, 20, 75, 50);
				panel.add(back);
				frame.setVisible(true);
			}

		});
		reject.setBounds(188, 170, 180, 45);
		panel.add(reject);

		frame.setVisible(true);

	}

	public static void collectLoanMoney() {
		JFrame frame = new JFrame("Collect Loan Money");
		frame.setSize(650, 500);
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

//		Money loanTotal = 
		
		JLabel display = new JLabel("Display Loan Money Here");
		display.setBounds(188, 120, 150, 80);
		// display.setText("Display Loan Money");
		panel.add(display);

		JButton collect = new JButton("Collect Money");
		collect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					ManagerHomePage.bankManager.collectLoanPayment(LoginPage.bank.getCurrentDate());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JFrame approved = new JFrame("Collect Loan Money Successful");
				approved.setSize(650, 500);
				approved.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel panel = new JPanel();
				approved.add(panel);
				panel.setLayout(null);
				JLabel successful = new JLabel("Successful!");
				successful.setBounds(188, 120, 150, 80);
				panel.add(successful);
				JButton back = new JButton("Back");
				back.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						approved.dispatchEvent(new WindowEvent(approved, WindowEvent.WINDOW_CLOSING));

					}

				});
				back.setBounds(50, 20, 75, 50);
				panel.add(back);
				approved.setVisible(true);

			}

		});
		collect.setBounds(188, 170, 120, 80);
		panel.add(collect);
		frame.setVisible(true);
	}

	public static void viewLoans() {
		JFrame frame = new JFrame("View Loans");
		frame.setSize(650, 500);
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

		JLabel displayLoans = new JLabel("Display Loans Here");
		displayLoans.setBounds(180, 120, 180, 50);
		panel.add(displayLoans);
		
		
		panel.setSize(650, 500);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		ArrayList<Loan> loans = ManagerHomePage.bankManager.getLoans();
		if (loans != null) {
			int i = 0;
			for (Loan loan : loans) {
				i++;
				JButton loanRequestButton = new JButton("Loan Request " + (i + 1) + ": amount = " + loan.getAmount()
						+ " Request Date: " + loan.getRequested());
				panel.add(loanRequestButton);
			}
		}
		System.out.println("loans " + loans.size());
		JScrollPane scroll = new JScrollPane(panel);
//		scroll.setBounds(10, 11, 455, 249);

		frame.getContentPane().add(scroll);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);

	}

}
