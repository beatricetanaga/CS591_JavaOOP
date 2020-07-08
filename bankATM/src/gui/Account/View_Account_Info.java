package gui.Account;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import account.Account;
import bankATM.Type;
import database.DBAccount;
import gui.Client.ClientAccountInfo;
import gui.Client.ClientHomePage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class View_Account_Info {

	public static void main(String[] args) {
		DBAccount b = new DBAccount();
		Account a = null;
		try {
			a = b.retrieveById("6bf61a1e-0697-4b08-a0ff-86d6cb3d70bh");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		placeComponents(a);
	}

	public static void placeComponents(Account account) {

		JFrame frame = new JFrame("View Account Information");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		Font f = new Font("Arial", Font.CENTER_BASELINE, 14);
		Font f1 = new Font("Arial", Font.CENTER_BASELINE, 19);

		JLabel failed_to_close_account = new JLabel("View Account Information", SwingConstants.CENTER);
		failed_to_close_account.setBounds(200, 50, 260, 60);
		failed_to_close_account.setFont(f1);
		panel.add(failed_to_close_account);

		JButton go_back = new JButton("Go back");
		go_back.setBounds(80, 50, 100, 30);
		go_back.setOpaque(true);
		go_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				go_back.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//                return to Clients Page Wait for Byran
				ClientAccountInfo.displayAccounts();
			}
		});
		go_back.setFont(f);
		go_back.setBackground(Color.ORANGE);
		panel.add(go_back);
		
		
		if(!account.getType().equals(Type.DepositAccount) && account.getBalance().getValue() > 0) {
			JButton closeAcc = new JButton("Close account");
			closeAcc.setBounds(180, 50, 130, 30);
			closeAcc.setOpaque(true);
			closeAcc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					closeAcc.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//	                return to Clients Page Wait for Byran
					Account_Close.placeComponents(account);
				}
			});
			closeAcc.setFont(f);
			closeAcc.setBackground(Color.ORANGE);
			panel.add(closeAcc);
		}
		

		String[] info = { "Type: " + account.getType().str, "id: " + account.getId(),
				"Balance: " + account.getBalance().toString(), "Date created: " + account.getCreated(), "Status: " + account.getStatus().str};
		JList<String> list = new JList<String>(info);

		JScrollPane scrollArea = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollArea.setBounds(150, 150, 350, 300);
		panel.add(scrollArea);

		frame.add(panel);
		frame.setVisible(true);
	}
}