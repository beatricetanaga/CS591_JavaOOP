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

public class Transfer_To {
	public static void main(String[] args) {
		placeComponents();
	}

	public static void placeComponents() {
		JFrame frame = new JFrame("Transfer Email");
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

		JLabel input = new JLabel("Choose account transfer to", SwingConstants.CENTER);
		input.setBounds(175, 120, 300, 60);
		input.setFont(f1);
		panel.add(input);

		JButton my_account = new JButton("Between my accounts");
		my_account.setBounds(175, 190, 300, 55);
		my_account.setFont(f1);
		panel.add(my_account);
		my_account.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Transfer_MyAccount.placeComponents();
			}
		});

		JButton other_account = new JButton("Transfer to other client");
		other_account.setBounds(175, 260, 300, 55);
		other_account.setFont(f1);
		panel.add(other_account);
		other_account.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Client_email.placeComponents();
			}
		});


		frame.add(panel);
		frame.setVisible(true);
	}
}
