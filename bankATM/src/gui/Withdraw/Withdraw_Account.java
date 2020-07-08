package gui.Withdraw;

import account.*;
import manager.*;
import transaction.*;
import database.*;
import bankATM.*;
import bankATM.Currency;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Withdraw_Account {

	public static void placeComponents(Account account) {
		System.out.println(">>>>>account" + account);
		JFrame frame = new JFrame("Withdraw Account");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		panel.setLayout(null);

		Font f = new Font("Arial", Font.CENTER_BASELINE, 14);
		Font f1 = new Font("Arial", Font.CENTER_BASELINE, 19);

		JLabel withdraw_and_close_ = new JLabel("Withdraw From Account", SwingConstants.CENTER);
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
				// TODO Auto-generated method stub
				Withdraw_Page.placeComponents();
			}
		});

		JTextField money = new JTextField(20);
		money.setBounds(125, 200, 275, 40);
		JLabel enter = new JLabel("Please enter Amount of Money to Withdraw", SwingConstants.CENTER);
		enter.setBounds(125, 130, 400, 40);
		enter.setFont(f1);
		enter.setLabelFor(money);
		panel.add(money);
		panel.add(enter);
//
//        JButton currency = new JButton("Currency");
//        currency.setBounds(430,200,100,40);
//        currency.setFont(f);
//        panel.add(currency);

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
				Account account = Withdraw_Page.account;
				System.out.println("account" + account);
				if (moneyFloat > 0) {
					Money amt = new Money(moneyFloat, Currency.USD);
					Withdraw w = account.withdraw(amt);
					System.out.println("account" + account);
					if (w != null) {
						Success_Withdraw.placeComponents();
					} else {
						Failed_Withdraw.placeComponents();
					}
				}
				Failed_Withdraw.placeComponents();

			}
		});

		Enter.setBounds(275, 300, 100, 40);
		Enter.setFont(f1);
		panel.add(Enter);

		frame.add(panel);
		frame.setVisible(true);
	}
}
