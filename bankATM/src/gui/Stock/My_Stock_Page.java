package gui.Stock;

import javax.swing.*;

import bankATM.PurchasedStock;
import bankATM.Stock;
import database.DBAccount;
import database.DBPurchasedStocks;
import database.DBStocks;
import gui.Client.ClientHomePage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class My_Stock_Page {
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

        JLabel failed_to_close_account = new JLabel("My Stock Page", SwingConstants.CENTER);
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

		panel.setSize(650, 500);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		DBPurchasedStocks tObj = new DBPurchasedStocks();
		ArrayList<PurchasedStock> stocks = null;
		
		try {
			stocks = tObj.retrieveAccountStocks("89121250-47d9-4ff6-bf59-4589e5c5030k");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		panel.setSize(650, 500);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		
		if (stocks != null) {
			int i = 0;
			for (PurchasedStock stock : stocks) {
				i++;
				JButton chooseStockButton = new JButton("\nStock " + i + ": " + stock.getStock().getName() + "\nPrice: "
						+ stock.getPurchasedPrice() + ", Quantity: " + stock.getQuantity() + "\n");

				// this jbutton will lead to the update stock page
				chooseStockButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						Sell_Amount.placeComponents(stock);
					}
				});
				panel.add(chooseStockButton);
			}
		}
		JScrollPane scroll = new JScrollPane(panel);

		frame.getContentPane().add(scroll);  
        frame.add(panel);
        frame.setVisible(true);
    }
}
