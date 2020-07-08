package gui.Stock;

import javax.swing.*;

import bankATM.Stock;
import database.DBStocks;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class Stock_Market {
    public static void main(String[] args) {

        placeComponents();
    }

    public static void placeComponents() {
        JFrame frame = new JFrame("Stock Market Page");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        Font f = new Font("Arial", Font.CENTER_BASELINE,14);
        Font f1 = new Font("Arial", Font.CENTER_BASELINE,19);

        JLabel failed_to_close_account = new JLabel("Stock Market Page", SwingConstants.CENTER);
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
                Stock_Page.placeComponents();
            }
        });

        go_back.setFont(f);
        go_back.setBackground(Color.ORANGE);
        panel.add(go_back);

        JLabel displayCurrent = new JLabel("Display All Stocks: ");
        displayCurrent.setBounds(150,110,150,40);
        panel.add(displayCurrent);
        
//        JLabel displayCurrent = new JLabel("Display All Stocks: "); // empty for now, fetch all stocks from backend
//		displayCurrent.setBounds(200, 70, 150, 40);
//		panel.add(displayCurrent);

		panel.setSize(650, 500);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		DBStocks tObj = new DBStocks();
		ArrayList<Stock> stocks = null;
		try {
			stocks = tObj.retrieveTransactions();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (stocks != null) {
			int i = 0;
			for (Stock stock : stocks) {
				i++;
				JButton chooseStockButton = new JButton("\nStock " + i + ": " + stock.getName() + "\nPrice: "
						+ stock.getPrice() + ", Quantity: " + stock.getQuantity() + "\n");

				// this jbutton will lead to the update stock page
				chooseStockButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						Buy_Amount.placeComponents(stock);
					}
				});
				panel.add(chooseStockButton);
			}
		}
		JScrollPane scroll = new JScrollPane(panel);
//		scroll.setBounds(10, 11, 455, 249);

		frame.getContentPane().add(scroll);        

//        Box box = Box.createVerticalBox();
//        JScrollPane scrollArea = new JScrollPane(box,
//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollArea.setBounds(150, 150, 350, 230);
//        panel.add(scrollArea);
//
//        JButton Select = new JButton("Select");
//        Select.setBounds(275, 400, 100, 30);
//        Select.setOpaque(true);
//        Select.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Select.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//                Stock_Buy.placeComponents();
//            }
//        });
//        panel.add(Select);

        frame.add(panel);
        frame.setVisible(true);
    }
}
