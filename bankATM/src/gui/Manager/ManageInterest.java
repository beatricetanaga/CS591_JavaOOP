package gui.Manager;

import javax.swing.*;

import bankATM.*;
import gui.Client.LoginPage;
import manager.*;

import java.awt.event.*;

public class ManageInterest {

	public static void updateInterest(String interestType) {
		JFrame frame = new JFrame(interestType + " Interest");
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
		
		float currentRate = 0;
		if (interestType.equals("savings") ) {
			currentRate = LoginPage.bank.getSavingsInterest();
		} else { // loans 
			currentRate = LoginPage.bank.getLoansInterest();
		}
		
		JLabel displayCurrent = new JLabel("Display Current Rate: " + currentRate);
		displayCurrent.setBounds(200, 70, 150, 40);
		panel.add(displayCurrent);

		JTextField newRate = new JTextField(20);
		newRate.setBounds(300, 130, 150, 30);
		JLabel enterNewFee = new JLabel("New " + interestType + " Interest: ");
		enterNewFee.setBounds(150, 130, 150, 40);
		enterNewFee.setLabelFor(newRate);
		panel.add(newRate);
		panel.add(enterNewFee);

		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String newRateStr = newRate.getText();
				float rateFloat = 0;
				try {
					rateFloat = Float.valueOf(newRateStr.trim()).floatValue();
					System.out.println("float priceFloat = " + rateFloat);
				} catch (NumberFormatException nfe) {
					System.out.println("NumberFormatException: " + nfe.getMessage());
					rateFloat = 0;
				}

				String message = "Rate Updated successfully";
				if (rateFloat <= 0 || rateFloat > 1 ) {
					message = "Invalid inputs! Rate is not updated.";
					System.out.println(message);
				} else {
					if (interestType.equals("savings") ) {
						LoginPage.bank.setSavingsInterest(rateFloat);
					} else { // loans 
						LoginPage.bank.setLoansInterest(rateFloat);
					}
					LoginPage.bank.updateDB();
					System.out.println("Rate is updated" );
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
		update.setBounds(200, 350, 100, 30);
		panel.add(update);

		frame.setVisible(true);
	}

}
