package gui.Stock;

import javax.swing.*;

import gui.Client.ClientHomePage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Stock_Page {
    public static void main(String[] args) {

        placeComponents();
    }

    public static void placeComponents() {
        // Creating instance of JFrame
        JFrame frame = new JFrame("Stock Page");
        // Setting the width and height of frame
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        panel.setLayout(null);

        Font f = new Font("Arial", Font.CENTER_BASELINE,14);
        Font f1 = new Font("Arial", Font.CENTER_BASELINE,19);

        JLabel withdraw_and_close_ = new JLabel("Stock Page ", SwingConstants.CENTER);
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

        JButton buyStock = new JButton("Buy Stock");
        buyStock.setBounds(200,120,250,55);
        buyStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyStock.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Stock_Market.placeComponents();
            }
        });

        buyStock.setFont(f1);
        panel.add(buyStock);

        JButton sellStock = new JButton("Sell Stock");
        sellStock.setBounds(200,190,250,55);
        sellStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellStock.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                My_Stock_Page.placeComponents();
            }
        });

        sellStock.setFont(f1);
        panel.add(sellStock);


        // adding panel to frame
        frame.add(panel);
        /* calling user defined method for adding components
         * to the panel.
         */
        // Setting the frame visibility to true
        frame.setVisible(true);
    }
}
