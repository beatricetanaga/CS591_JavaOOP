package gui.Transfer;

import javax.swing.*;

import account.Account;
import bankATM.Client;
import bankATM.Currency;
import bankATM.Money;
import database.DBClient;
import gui.Manager.ManageLoans;
import gui.Withdraw.Failed_Withdraw;
import gui.Withdraw.Withdraw_Page;
import transaction.Transfer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;

public class Client_email {
	public NumberFormat amount;
	public static Account toAccount = null;
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

		JLabel withdraw_and_close_ = new JLabel("Transfer Page", SwingConstants.CENTER);
		withdraw_and_close_.setBounds(200, 50, 250, 60);
		withdraw_and_close_.setFont(f1);
		panel.add(withdraw_and_close_);

		JButton go_back = new JButton("Go back");
		go_back.setBounds(80, 50, 100, 30);
		go_back.setOpaque(true);
		go_back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Transfer_Page.placeComponents();
			}

		});

		go_back.setFont(f);
		go_back.setBackground(Color.ORANGE);
		panel.add(go_back);

		JTextField email = new JTextField(20);
		email.setBounds(150, 200, 350, 40);
		JLabel enter = new JLabel("Please enter client's email", SwingConstants.CENTER);
		enter.setBounds(175, 130, 300, 40);
		enter.setFont(f1);
		enter.setLabelFor(email);
		panel.add(email);
		panel.add(enter);

		JButton Enter = new JButton("Enter");
		Enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newRateStr = email.getText();
				Client client = null;
				
				DBClient dbobj = new DBClient();
				try {
					client = dbobj.retrieveByEmail(newRateStr);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(client == null) {
					Incorrect_Account.placeComponents();
				} else {
					Transfer_Amount.toAccount  = client.getDepositAccount();
					Transfer_Amount.placeComponents();
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
