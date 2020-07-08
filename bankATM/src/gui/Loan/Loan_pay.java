package gui.Loan;

import javax.swing.*;

import account.Account;
import account.LoansAccount;
import bankATM.Currency;
import bankATM.Loan;
import bankATM.Money;
import gui.Client.ClientHomePage;
import gui.Transfer.Failed_Transfer;
import gui.Transfer.Success_Transfer;
import transaction.Transfer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class Loan_pay {
	public static void main(String[] args) {

		placeComponents();
	}

	public static void placeComponents() {

		Account loanAcc = null;
		try {
			loanAcc = ClientHomePage.client.getLoansAccount();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		float total = 0;
		if (loanAcc != null) {
			total = loanAcc.getBalance().getValue();
		}

		JFrame frame = new JFrame("Loan Pay");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();

		panel.setLayout(null);

		Font f = new Font("Arial", Font.CENTER_BASELINE, 14);
		Font f1 = new Font("Arial", Font.CENTER_BASELINE, 19);

		JLabel title = new JLabel("Loan Pay Page", SwingConstants.CENTER);
		title.setBounds(200, 50, 250, 60);
		title.setFont(f1);
		panel.add(title);

		JButton go_back = new JButton("Go back");
		go_back.setBounds(80, 50, 100, 30);
		go_back.setOpaque(true);
		go_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				go_back.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				Loan_Page.placeComponents();
			}
		});

		go_back.setFont(f);
		go_back.setBackground(Color.ORANGE);
		panel.add(go_back);

		JLabel display = new JLabel("Total loans amount to pay: -" + total + " USD", SwingConstants.CENTER);
		display.setBounds(125, 150, 275, 40);
		display.setOpaque(true);
		display.setBackground(Color.LIGHT_GRAY);
		panel.add(display);

		JLabel currency1 = new JLabel("Currency", SwingConstants.CENTER);
		currency1.setBounds(430, 150, 100, 40);
		currency1.setFont(f);
		currency1.setOpaque(true);
		currency1.setBackground(Color.LIGHT_GRAY);
		panel.add(currency1);

		JTextField money = new JTextField(20);
		money.setBounds(125, 250, 275, 40);
		JLabel enter = new JLabel("Please enter Amount of Money to Pay", SwingConstants.CENTER);
		enter.setBounds(125, 200, 400, 40);
		enter.setFont(f1);
		enter.setLabelFor(money);
		panel.add(money);
		panel.add(enter);

		JButton currency2 = new JButton("Currency");
		currency2.setBounds(430, 250, 100, 40);
		currency2.setFont(f);
		panel.add(currency2);

		JButton Enter = new JButton("Enter");
		Enter.setBounds(275, 300, 100, 40);
//		Enter.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				// if successful:
////                Loan_Left.placeComponents();
//				// else:
////                Payment_Failed.placeComponents();
//			}
//		});

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
				System.out.println("account");
				LoansAccount acc = null;
				ArrayList<Loan> loans = null;
				Money amount = new Money(moneyFloat, Currency.USD);

				try {
					acc = (LoansAccount) ClientHomePage.client.getLoansAccount();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				if (moneyFloat > 0 && acc != null) {
					
					Loan_Left.placeComponents();

				} else {
					Failed_Transfer.placeComponents();
				}

			}
		});

		Enter.setFont(f1);
		panel.add(Enter);

		frame.add(panel);
		frame.setVisible(true);
	}
}
