package gui.Loan;

import javax.swing.*;

import account.LoansAccount;
import bankATM.Currency;
import bankATM.*;
import gui.Client.ClientHomePage;
import gui.Client.LoginPage;
import gui.Transfer.Failed_Transfer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class Loan_Request {
	public static void main(String[] args) {

		placeComponents();
	}

	public static void placeComponents() {

		JFrame frame = new JFrame("Loan Request");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		Font f = new Font("Arial", Font.CENTER_BASELINE, 14);
		Font f1 = new Font("Arial", Font.CENTER_BASELINE, 19);

		JLabel title = new JLabel("Loan Request Page", SwingConstants.CENTER);
		title.setBounds(200, 50, 250, 60);
		title.setFont(f1);
		panel.add(title);

		JButton go_back = new JButton("Go back");
		go_back.setBounds(80, 50, 100, 30);
		go_back.setOpaque(true);

		go_back.setFont(f);
		go_back.setBackground(Color.ORANGE);
		panel.add(go_back);
		go_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				go_back.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				Loan_Page.placeComponents();
			}
		});

		JTextField money = new JTextField(20);
		money.setBounds(125, 200, 275, 40);
		JLabel enter = new JLabel("Please enter Amount of Loan to Request", SwingConstants.CENTER);
		enter.setBounds(125, 130, 400, 40);
		enter.setFont(f1);
		enter.setLabelFor(money);
		panel.add(money);
		panel.add(enter);

		JButton currency = new JButton("Currency");
		currency.setBounds(430, 200, 100, 40);
		currency.setFont(f);
		panel.add(currency);

		JButton Enter = new JButton("Enter");
		Enter.setBounds(275, 300, 100, 40);
		Enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				go_back.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				String newRateStr = money.getText();
				float moneyFloat = 0;
				try {
					moneyFloat = Float.valueOf(newRateStr.trim()).floatValue();
					System.out.println("float priceFloat = " + moneyFloat);
				} catch (NumberFormatException nfe) {
					System.out.println("NumberFormatException: " + nfe.getMessage());
				}
				
				LoansAccount acc = null;
				Loan loan = null;
				Money amount = new Money(moneyFloat, Currency.USD);

				try {
					acc = (LoansAccount) ClientHomePage.client.getLoansAccount();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				
				if (moneyFloat > 0 && acc != null) {
					loan = new Loan(acc, amount, LoginPage.bank.getCurrentDate(), LoginPage.bank.getLoansInterest());
					if(loan!= null && loan.getStatus().equals(Status.Approved)) {
						Request_Success.placeApproved();
					} else {
						Request_Success.placeComponents();
					}
				} else {
					Request_Success.placeFailed();
				}

			}
		});

		Enter.setFont(f1);
		panel.add(Enter);

		frame.add(panel);
		frame.setVisible(true);
	}
}
