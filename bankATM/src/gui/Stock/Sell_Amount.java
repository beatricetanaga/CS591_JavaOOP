package gui.Stock;

import javax.swing.*;

import account.Account;
import bankATM.PurchasedStock;
import bankATM.Stock;
import gui.Client.ClientHomePage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Sell_Amount {
    public static void main(String[] args) {

        placeComponents(null);
    }
    
    public static void placeComponents(PurchasedStock stock) {

        JFrame frame = new JFrame("Buy Amount");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        panel.setLayout(null);

        Font f = new Font("Arial", Font.CENTER_BASELINE,14);
        Font f1 = new Font("Arial", Font.CENTER_BASELINE,19);

        JLabel withdraw_and_close_ = new JLabel("Sell Stock", SwingConstants.CENTER);
        withdraw_and_close_.setBounds(200, 50, 250, 60);
        withdraw_and_close_.setFont(f1);
        panel.add(withdraw_and_close_);

        JButton go_back = new JButton("Go back");
        go_back.setBounds(80, 50, 100, 30);
        go_back.setOpaque(true);
        go_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_back.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Stock_Page.placeComponents();
            }
        });

        go_back.setFont(f);
        go_back.setBackground(Color.ORANGE);
        panel.add(go_back);

        JTextField money = new JTextField(20);
        money.setBounds(200,200,250,40);
        JLabel enter = new JLabel("Please Enter Amount of Stock to Sell", SwingConstants.CENTER);
        enter.setBounds(125,130,400,50);
        enter.setFont(f1);
        enter.setLabelFor(money);
        panel.add(money);
        panel.add(enter);

        JButton Enter = new JButton("Enter");
        Enter.setBounds(275,275,100,40);
        Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String newRateStr = money.getText();
				int q = 0;
				try {
					q = Integer.valueOf(newRateStr.trim()).intValue();
					System.out.println("int qantity = " + q);
				} catch (NumberFormatException nfe) {
					System.out.println("NumberFormatException: " + nfe.getMessage());
				}
				if (q > 0 && q <= stock.getQuantity()) {
					Account acc =ClientHomePage.client.getSecurityAccount();
					
					if (acc != null) {
						System.out.println("SUCCESS  transfer !");
						Success_Trans.placeComponents();
					} else {
						Failed_Trans.placeComponents();
					}
				} else {
					Failed_Trans.placeComponents();
				}
				
				
                Enter.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

            }
        });
        Enter.setFont(f1);
        panel.add(Enter);


        frame.add(panel);
        frame.setVisible(true);
    }

}
