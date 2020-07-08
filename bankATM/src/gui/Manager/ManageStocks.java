package gui.Manager;

import javax.swing.*;

import bankATM.Currency;
import bankATM.*;
import manager.*;
import transaction.Transaction;
import database.*;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.*;

public class ManageStocks {


	public static void updateStock() {
		JFrame frame = new JFrame("Update Stocks");
		frame.setSize(650, 500);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

			}

		});
		back.setBounds(50, 20, 75, 50);
		panel.add(back);

		JLabel displayCurrent = new JLabel("Display All Stocks: "); // empty for now, fetch all stocks from backend
		displayCurrent.setBounds(200, 70, 150, 40);
		panel.add(displayCurrent);

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
						updateStockPage(stock);
					}
				});
				panel.add(chooseStockButton);
			}
		}
		JScrollPane scroll = new JScrollPane(panel);
//		scroll.setBounds(10, 11, 455, 249);

		frame.getContentPane().add(scroll);

		frame.setVisible(true);
		;
	}

	private static void updateStockPage(Stock stock) {
		JFrame frame = new JFrame("Update Stock");
		frame.setSize(650, 500);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

			}

		});
		back.setBounds(50, 20, 75, 50);
		panel.add(back);

		JLabel stockName = new JLabel("Update " + stock.getName() + "\n");
		stockName.setBounds(200, 70, 150, 30);
		panel.add(stockName);

		JTextField name = new JTextField(20);
		name.setBounds(250, 130, 150, 30);
		JLabel enterName = new JLabel("Name: ");
		enterName.setBounds(200, 130, 150, 40);
		enterName.setLabelFor(name);
		panel.add(name);
		panel.add(enterName);

		JTextField price = new JTextField(20);
		price.setBounds(250, 180, 150, 30);
		JLabel enterPrice = new JLabel("Price: ");
		enterPrice.setBounds(200, 180, 150, 30);
		enterPrice.setLabelFor(price);
		panel.add(price);
		panel.add(enterPrice);

		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nameStr = name.getText();
				System.out.println("nameStr f = " + nameStr);
				String priceStr = price.getText();

				float priceFloat = 0;
				try {
					priceFloat = Float.valueOf(priceStr.trim()).floatValue();
					System.out.println("float f = " + priceFloat);
				} catch (NumberFormatException nfe) {
					System.out.println("NumberFormatException: " + nfe.getMessage());
					priceFloat = 0;
				}
				JFrame result = null;
				JLabel message = new JLabel("Successfully Updated stock!");
				if (nameStr.isBlank() || priceFloat <= 0) {
					result = new JFrame("Stock Not Updated. Invalid Inputs.");
					message = new JLabel("Stock Not Updated. Invalid Inputs.");
				} else {
					if (!nameStr.isBlank()) {
						stock.setName(nameStr);
						stock.updateDB();
						// TODO Auto-generated method stub
						result = new JFrame("Stock Successfully Updated");
					}
					if (priceFloat > 0) {
						stock.setPrice(new Money(priceFloat, Currency.USD));
						stock.updateDB();
						result = new JFrame("Stock Successfully Updated");
					}
				}

				final JFrame approved = result;
				// TODO Auto-generated method stub
				approved.setSize(650, 500);
				approved.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel panel = new JPanel();
				approved.add(panel);
				panel.setLayout(null);
				JLabel successful = message;
				successful.setBounds(200, 150, 300, 100);
				panel.add(successful);
				JButton back = new JButton("Back");
				back.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						approved.dispatchEvent(new WindowEvent(approved, WindowEvent.WINDOW_CLOSING));

					}

				});
				back.setBounds(50, 20, 75, 50);
				panel.add(back);
				approved.setVisible(true);

			}

		});
		update.setBounds(200, 330, 150, 30);
		panel.add(update);

		frame.setVisible(true);
	}

	public static void addNewStock() {
		JFrame frame = new JFrame("Add New Stock");
		frame.setSize(650, 500);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

			}

		});
		back.setBounds(50, 20, 75, 50);
		panel.add(back);

		JTextField name = new JTextField(20);
		name.setBounds(250, 130, 150, 30);
		JLabel enterName = new JLabel("Name: ");
		enterName.setBounds(200, 130, 150, 40);
		enterName.setLabelFor(name);
		panel.add(name);
		panel.add(enterName);

		JTextField price = new JTextField(20);
		price.setBounds(250, 180, 150, 30);
		JLabel enterPrice = new JLabel("Price: ");
		enterPrice.setBounds(200, 180, 150, 30);
		enterPrice.setLabelFor(price);
		panel.add(price);
		panel.add(enterPrice);

		JTextField quantity = new JTextField(20);
		quantity.setBounds(260, 230, 150, 30);
		JLabel enterQuantity = new JLabel("Quantity: ");
		enterQuantity.setBounds(200, 230, 150, 30);
		enterQuantity.setLabelFor(quantity);
		panel.add(quantity);
		panel.add(enterQuantity);

		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nameStr = name.getText();
				String priceStr = price.getText();
				String quantityStr = quantity.getText();
				float priceFloat = 0;
				try {
					priceFloat = Float.valueOf(priceStr.trim()).floatValue();
					System.out.println("float priceFloat = " + priceFloat);
				} catch (NumberFormatException nfe) {
					System.out.println("NumberFormatException: " + nfe.getMessage());
					priceFloat = 0;
				}
				int quantityInt = 0;
				try {
					quantityInt = Integer.valueOf(quantityStr.trim()).intValue();
					System.out.println("int quantityInt = " + quantityInt);
				} catch (NumberFormatException nfe) {
					System.out.println("NumberFormatException: " + nfe.getMessage());
					priceFloat = 0;
				}

				String message = "Stock Successfully Added";
				if (nameStr.isBlank() || priceFloat <= 0 || quantityInt < 1) {
					message = "Invalid inputs! Stock is not added.";
					System.out.println(message);
				} else {
					Stock stock = new Stock(nameStr, new Money(priceFloat, Currency.USD), quantityInt);
					System.out.println("STock" + stock);
				}

				// TODO Auto-generated method stub
				JFrame approved = new JFrame(message);
				approved.setSize(650, 500);
				approved.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel panel = new JPanel();
				approved.add(panel);
				panel.setLayout(null);
				JLabel successful = new JLabel(message);
				successful.setBounds(200, 150, 300, 100);
				panel.add(successful);
				JButton back = new JButton("Back");
				back.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						approved.dispatchEvent(new WindowEvent(approved, WindowEvent.WINDOW_CLOSING));

					}

				});
				back.setBounds(50, 20, 75, 50);
				panel.add(back);
				approved.setVisible(true);

			}

		});
		create.setBounds(200, 330, 150, 30);
		panel.add(create);

		frame.setVisible(true);
	}

}
