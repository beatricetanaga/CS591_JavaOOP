package gui.Deposite;

import gui.Client.ClientHomePage;
import gui.Stock.Failed_Trans;
import gui.Transfer.*;
import transaction.*;

import javax.swing.*;

import account.Account;
import bankATM.Currency;
import bankATM.*;

import java.awt.*;
import java.awt.event.*;

public class Deposit_To_Account extends JFrame {

	public static void main(String[] args) {

		placeComponents();
	}

	public static void placeComponents() {

		JFrame frame = new JFrame("Deposit");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		Font f = new Font("Arial", Font.CENTER_BASELINE, 14);
		Font f1 = new Font("Arial", Font.CENTER_BASELINE, 19);

		JLabel title = new JLabel("Make Deposit", SwingConstants.CENTER);
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
				Deposite_Page.placeComponents();
			}
		});

		go_back.setFont(f);
		go_back.setBackground(Color.ORANGE);
		panel.add(go_back);

		JTextField money = new JTextField(20);
		money.setBounds(125, 200, 275, 40);
		JLabel enter = new JLabel("Please enter Amount of Money to Deposit", SwingConstants.CENTER);
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
				String newRateStr = money.getText();
				float moneyFloat = 0;
				try {
					moneyFloat = Float.valueOf(newRateStr.trim()).floatValue();
					System.out.println("float deposit = " + moneyFloat);
				} catch (NumberFormatException nfe) {
					System.out.println("NumberFormatException: " + nfe.getMessage());
				}
				
				if (moneyFloat > 0) {
					Account acc = Deposite_Page.accToDeposit;
					System.out.println("account: " + acc);
					
					Money amount = new Money(moneyFloat, Currency.USD);
					Deposit deposit = acc.deposit(amount);
					System.out.println("deposit: " + deposit);
					if (deposit != null) {
						System.out.println("SUCCESSFULL  deposit !");
						 Success_Deposit.placeComponents();
					} else {
						Failed_Deposit.placeComponents();
					}
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
