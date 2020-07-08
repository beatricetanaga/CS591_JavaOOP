package gui.Transfer;

import account.*;
import manager.*;
import transaction.*;
import database.*;
import gui.Withdraw.Failed_Withdraw;
import gui.Withdraw.Success_Withdraw;
import gui.Withdraw.Withdraw_Account;
import gui.Withdraw.Withdraw_Page;
import bankATM.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import java.awt.*;
import javax.swing.*;
import java.awt.*;

public class Transfer_Amount {

	public static Account fromAccount = Transfer_Page.fromAccount;
	public static Account toAccount = null;
	public static Money amount = new Money(0, Currency.USD);

	public static void main(String[] args) {
		placeComponents();
	}

	public static void placeComponents() {

		JFrame frame = new JFrame("Transfer Amount");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		panel.setLayout(null);

		Font f = new Font("Arial", Font.CENTER_BASELINE, 14);
		Font f1 = new Font("Arial", Font.CENTER_BASELINE, 19);

		JLabel withdraw_and_close_ = new JLabel("Transfer Page", SwingConstants.CENTER);
		withdraw_and_close_.setBounds(200, 50, 250, 60);
		withdraw_and_close_.setFont(f1);
		panel.add(withdraw_and_close_);

		JButton go_back = new JButton("Go back");
		go_back.setBounds(80, 50, 100, 30);
		go_back.setOpaque(true);

		go_back.setFont(f);
		go_back.setBackground(Color.ORANGE);
		panel.add(go_back);
		go_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Transfer_Page.placeComponents();
			}
		});

		JTextField money = new JTextField(20);
		money.setBounds(125, 200, 275, 40);
		JLabel enter = new JLabel("Please enter Amount of Money to Transfer", SwingConstants.CENTER);
		enter.setBounds(125, 130, 400, 40);
		enter.setFont(f1);
		enter.setLabelFor(money);
		panel.add(money);
		panel.add(enter);

//		JButton currency = new JButton("Currency");
//		currency.setBounds(430, 200, 100, 40);
//		currency.setFont(f);
//		panel.add(currency);

		JButton Enter = new JButton("Enter");

		Enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newRateStr = money.getText();
				float moneyFloat = 0;
				try {
					moneyFloat = Float.valueOf(newRateStr.trim()).floatValue();
					System.out.println("float priceFloat = " + moneyFloat);
				} catch (NumberFormatException nfe) {
					System.out.println("NumberFormatException: " + nfe.getMessage());
				}
				System.out.println("account" + fromAccount);
				if (moneyFloat > 0) {
					amount = new Money(moneyFloat, Currency.USD);
					Transfer w = fromAccount.transfer(amount, toAccount);
					if (w != null) {
						System.out.println("SUCCESS  transfer !");
						Success_Transfer.placeComponents();
					} else {
						Failed_Transfer.placeComponents();
					}
				} else {
					Failed_Transfer.placeComponents();
				}

			}
		});

		Enter.setBounds(275, 300, 100, 40);
		Enter.setFont(f1);
		panel.add(Enter);

		frame.add(panel);
		frame.setVisible(true);
	}
}
