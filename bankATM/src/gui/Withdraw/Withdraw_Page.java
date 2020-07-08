package gui.Withdraw;

import javax.swing.*;

import account.*;
import manager.*;
import transaction.*;
import database.*;
import gui.Client.ClientHomePage;
import bankATM.*;
import bankATM.Currency;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Withdraw_Page {

	static Client client = null;
	public static Account account = null;

	public static void main(String[] args) {
		placeComponents();
	}

	public static void setup() {
		DBClient dbobj = new DBClient();
		try {
			client = dbobj.retrieveById("c7577a78-ec82-4e04-85c6-468f029617e6");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void placeComponents() {

		setup();

		JFrame frame = new JFrame("Withdraw Page");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		Font f = new Font("Arial", Font.CENTER_BASELINE, 14);
		Font f1 = new Font("Arial", Font.CENTER_BASELINE, 19);

		JLabel withdraw_and_close_ = new JLabel("Withdraw Page ", SwingConstants.CENTER);
		withdraw_and_close_.setBounds(200, 50, 250, 60);
		withdraw_and_close_.setFont(f1);
		panel.add(withdraw_and_close_);

		JButton go_back = new JButton("Home");
		go_back.setBounds(80, 50, 100, 30);
		go_back.setOpaque(true);

		go_back.setFont(f);
		go_back.setBackground(Color.ORANGE);
		panel.add(go_back);
		go_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ClientHomePage.placeButtons(null);
			}
		});

		JLabel input = new JLabel("Choose account withdraw from", SwingConstants.CENTER);
		input.setBounds(175, 120, 300, 60);
		input.setFont(f1);
		panel.add(input);

		JButton dep_account = new JButton("Deposit Account");
		dep_account.setBounds(225, 190, 200, 55);
		
		dep_account.setFont(f1);
		panel.add(dep_account);
		dep_account.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				account = client.getDepositAccount();
				Withdraw_Account.placeComponents(account);
			}
		});
		
		if (client.getSavingsAccount() != null) {
			
			JButton saving_account = new JButton("Saving Account");
			saving_account.setBounds(225, 260, 200, 55);
			saving_account.setFont(f1);
			panel.add(saving_account);
			saving_account.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					account = client.getSavingsAccount();
					Withdraw_Account.placeComponents(account);
				}
			});
		}

		if (client.getCheckingAccount() != null) {
			
			JButton checking_account = new JButton("Checking Account");
			
			checking_account.setBounds(225, 330, 200, 55);
			checking_account.setFont(f1);
			panel.add(checking_account);
			checking_account.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					account = client.getCheckingAccount();
					Withdraw_Account.placeComponents(account);
				}
			});
		}


		frame.add(panel);
		frame.setVisible(true);
	}
}
