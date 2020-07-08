package gui.Deposite;

import javax.swing.*;

import account.Account;
import gui.Client.ClientHomePage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Deposite_Page {
	public static Account accToDeposit = null;

	public static void main(String[] args) {
		/*
		 * calling user defined method for adding components to the panel.
		 */
		placeComponents();
	}

	public static void placeComponents() {
		// Creating instance of JFrame
		JFrame frame = new JFrame("Deposit Page");
		// Setting the width and height of frame
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		Font f = new Font("Arial", Font.CENTER_BASELINE, 14);
		Font f1 = new Font("Arial", Font.CENTER_BASELINE, 19);

		JLabel withdraw_and_close_ = new JLabel("Deposite Page ", SwingConstants.CENTER);
		withdraw_and_close_.setBounds(200, 50, 250, 60);
		withdraw_and_close_.setFont(f1);
		panel.add(withdraw_and_close_);

		JButton go_back = new JButton("Home");
		go_back.setBounds(80, 50, 100, 30);
		go_back.setOpaque(true);
		go_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ClientHomePage.placeButtons(null);
			}
		});

		go_back.setFont(f);
		go_back.setBackground(Color.ORANGE);
		panel.add(go_back);

		JButton saving_account = new JButton("Saving Account");
		saving_account.setBounds(225, 120, 200, 55);
		saving_account.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saving_account.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				accToDeposit = ClientHomePage.client.getSavingsAccount();
				if (accToDeposit != null) {
					Deposit_To_Account.placeComponents();
				}
			}
		});
		saving_account.setFont(f1);
		panel.add(saving_account);

		JButton checking_account = new JButton("Checking Account");
		checking_account.setBounds(225, 190, 200, 55);
		checking_account.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saving_account.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				accToDeposit = ClientHomePage.client.getCheckingAccount();
				if (accToDeposit != null) {
					Deposit_To_Account.placeComponents();
				}
			}
		});

		checking_account.setFont(f1);
		panel.add(checking_account);

		JButton Deposit_account = new JButton("Deposit Account");
		Deposit_account.setBounds(225, 260, 200, 55);
		Deposit_account.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saving_account.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				accToDeposit = ClientHomePage.client.getDepositAccount();
				if (accToDeposit != null) {
					Deposit_To_Account.placeComponents();
				}
			}
		});

		Deposit_account.setFont(f1);
		panel.add(Deposit_account);

		// adding panel to frame
		frame.add(panel);
		// Setting the frame visibility to true
		frame.setVisible(true);
	}
}
