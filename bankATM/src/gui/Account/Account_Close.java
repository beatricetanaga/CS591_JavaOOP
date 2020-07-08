package gui.Account;

import gui.Client.ClientHomePage;
import gui.Deposite.Deposite_Page;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import account.Account;
import bankATM.Money;
import database.DBAccount;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class Account_Close {

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

    	Money m = account.getBalance();
        JFrame frame = new JFrame("Account Close");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        Font f = new Font("Arial", Font.CENTER_BASELINE,14);
        Font f1 = new Font("Arial", Font.CENTER_BASELINE,19);

        JLabel withdraw_and_close_ = new JLabel("Account Close ", SwingConstants.CENTER);
        withdraw_and_close_.setBounds(200, 50, 250, 60);
        withdraw_and_close_.setFont(f1);
        panel.add(withdraw_and_close_);

        JButton go_back = new JButton("Home");
        go_back.setBounds(80, 50, 100, 30);
        go_back.setOpaque(true);
        go_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_back.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//                return to Clients Page Wait for Byran
                ClientHomePage.placeButtons(null);
            }
        });


        go_back.setFont(f);
        go_back.setBackground(Color.ORANGE);
        panel.add(go_back);

        JButton money = new JButton("Withdraw remaining "+m+" and Close");
        money.setBounds(50, 150, 260, 100);
        money.setOpaque(true);
        money.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                money.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Withdraw_Close.placeComponents();
            }
        });

        money.setBackground(Color.GRAY);
        panel.add(money);

        JButton Withdraw = new JButton("Withdraw and Close");
        Withdraw.setBounds(100,350,220,30);
        Withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Withdraw.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//                if sucess:
//                Success_Close_Account.placeComponents();
//                else:
//                Failed_Close_Account.placeComponents();
                
                try {
					account.close(ClientHomePage.client);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                Success_Close_Account.placeComponents();
            }
        });

        Withdraw.setFont(f1);
        Withdraw.setOpaque(true);
        Withdraw.setBackground(Color.green);
        panel.add(Withdraw);

        JButton money1 = new JButton("Transfer remaining "+m+" and Close");
        money1.setBounds(340, 150, 260, 100);
        money1.setOpaque(true);
        money1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                money1.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Transfer_Close.placeComponents();
            }
        });

        money1.setBackground(Color.GRAY);
        panel.add(money1);

        JButton Transfer = new JButton("Transfer and Close");
        Transfer.setBounds(375,350,200,30);
        Transfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transfer.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                try {
					account.close(ClientHomePage.client);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                Success_Close_Account.placeComponents();
            }
        });

        Transfer.setFont(f1);
        Transfer.setOpaque(true);
        Transfer.setBackground(Color.green);
        panel.add(Transfer);

        frame.add(panel);
        frame.setVisible(true);
    }
}