package gui.Loan;
import javax.swing.*;

import account.*;
import bankATM.Money;
import gui.Client.ClientHomePage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class Loan_Left {
    public static void main(String[] args) {

        placeComponents();
    }

    public static void placeComponents() {

        JFrame frame = new JFrame("Loan Request");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        Font f = new Font("Arial", Font.CENTER_BASELINE, 14);
        Font f1 = new Font("Arial", Font.CENTER_BASELINE, 19);

        JLabel title = new JLabel("Your Payment Was Successful!", SwingConstants.CENTER);
        title.setBounds(175, 50, 300, 60);
        title.setFont(f1);
        panel.add(title);

        JButton go_back = new JButton("Go back");
        go_back.setBounds(80, 50, 100, 30);
        go_back.setOpaque(true);
        go_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_back.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Loan_Page.placeComponents();
            }
        });

        go_back.setFont(f);
        go_back.setBackground(Color.ORANGE);
        panel.add(go_back);

        Money amount = null;
		try {
			amount = ClientHomePage.client.getLoansAccount().getBalance();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        JLabel display = new JLabel("Loans left to pay: " + amount, SwingConstants.CENTER);
        display.setBounds(125, 150, 275, 40);
        display.setOpaque(true);
        display.setBackground(Color.LIGHT_GRAY);
        panel.add(display);

//        JLabel currency1 = new JLabel("Currency", SwingConstants.CENTER);
//        currency1.setBounds(430, 150, 100, 40);
//        currency1.setOpaque(true);
//        currency1.setBackground(Color.LIGHT_GRAY);
//        panel.add(currency1);

        frame.add(panel);
        frame.setVisible(true);
    }
}
