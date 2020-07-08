package gui.Transfer;

import javax.swing.*;
import account.*;
import manager.*;
import transaction.*;
import database.*;
import gui.Withdraw.Withdraw_Account;
import bankATM.*;
import bankATM.Currency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import java.awt.*;

public class Transfer_MyAccount {
	public static Account fromAccount = Transfer_Page.fromAccount;
	public static void main(String[] args) {
	}

	public static void placeComponents() {
		JFrame frame = new JFrame("Transfer Amount");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		panel.setLayout(null);

		Font f = new Font("Arial", Font.CENTER_BASELINE, 14);
		Font f1 = new Font("Arial", Font.CENTER_BASELINE, 19);

		JLabel title = new JLabel("Transfer Page ", SwingConstants.CENTER);
		title.setBounds(200, 50, 250, 60);
		title.setFont(f1);
		panel.add(title);

		JButton go_back = new JButton("Home");
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

		JLabel input = new JLabel("Choose my account transfer to", SwingConstants.CENTER);
		input.setBounds(175, 120, 300, 60);
		input.setFont(f1);
		panel.add(input);

		

		if (Transfer_Page.client.getDepositAccount() != null && !fromAccount.getType().equals(Type.SavingsAccount)) {
			JButton dep_account = new JButton("Deposit Account");
			dep_account.setBounds(225, 190, 200, 55);
			dep_account.setFont(f1);
			panel.add(dep_account);
			dep_account.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Transfer_Amount.toAccount = Transfer_Page.client.getDepositAccount();
					Transfer_Amount.placeComponents();
				}
			});

		}

		if (Transfer_Page.client.getSavingsAccount() != null && !fromAccount.getType().equals(Type.SavingsAccount)) {

			JButton saving_account = new JButton("Saving Account");
			saving_account.setBounds(225, 260, 200, 55);
			saving_account.setFont(f1);
			panel.add(saving_account);
			saving_account.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Transfer_Amount.toAccount = Transfer_Page.client.getSavingsAccount();
					Transfer_Amount.placeComponents();
				}
			});
		}

		if (Transfer_Page.client.getCheckingAccount() != null && !fromAccount.getType().equals(Type.CheckingAccount)) {

			JButton checking_account = new JButton("Checking Account");

			checking_account.setBounds(225, 330, 200, 55);
			checking_account.setFont(f1);
			panel.add(checking_account);
			checking_account.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Transfer_Amount.toAccount = Transfer_Page.client.getCheckingAccount();
					Transfer_Amount.placeComponents();
				}
			});
		}
		
		frame.add(panel);
		frame.setVisible(true);
	}
}
