package gui.Manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

import bankATM.Bank;
import bankATM.Currency;
import bankATM.Money;
import gui.Client.LoginPage;
import manager.Manager;

public class ManageServiceFees {

	public static void updateFee(String feeType) {
		JFrame frame = new JFrame("Update Fee: " + feeType);
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
		if (feeType.equals("withdraw")) {
			currentRate = LoginPage.bank.getWithdrawFee().getValue();
		} else if (feeType.equals("withdraw")) {
			currentRate = LoginPage.bank.getWithdrawFee().getValue();
		} else if (feeType.equals("transfer")) {
			currentRate = LoginPage.bank.getTransferFee().getValue();
		} else if (feeType.equals("deposit")) {
			currentRate = LoginPage.bank.getDepositFee().getValue();
		} else if (feeType.equals("account")) {
			currentRate = LoginPage.bank.getCloseAccountFee().getValue();
		} else if (feeType.equals("loan")) {
			currentRate = LoginPage.bank.getLoansInterest();
		}

		JLabel displayCurrent = new JLabel("Display Current Fee: "+currentRate);
		displayCurrent.setBounds(200, 70, 150, 40);
		panel.add(displayCurrent);

		JTextField newFee = new JTextField(20);
		newFee.setBounds(290, 130, 150, 30);
		JLabel enterNewFee = new JLabel("Enter new fee: ");
		enterNewFee.setBounds(200, 130, 150, 40);
		enterNewFee.setLabelFor(newFee);
		panel.add(newFee);
		panel.add(enterNewFee);

		JButton update = new JButton("Update Fee");
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String newFeeStr = newFee.getText();
				float feeFloat = 0;
				try {
					feeFloat = Float.valueOf(newFeeStr.trim()).floatValue();
					System.out.println("float priceFloat = " + newFeeStr);
				} catch (NumberFormatException nfe) {
					System.out.println("NumberFormatException: " + nfe.getMessage());
					feeFloat = 0;
				}

				String message = "Fee Updated successfully";
				
				if (feeFloat <= 0 ) {
					message = "Invalid inputs! Fee is not updated.";
					System.out.println(message);
				} else {
					Money newFeeMoney  =new Money(feeFloat, Currency.USD); 
					if (feeType.equals("checking")) {
						LoginPage.bank.setCheckingAccountFee(newFeeMoney);
					} else if (feeType.equals("withdraw")) {
						LoginPage.bank.setWithdrawFee(newFeeMoney);
					} else if (feeType.equals("transfer")) {
						LoginPage.bank.setTransferFee(newFeeMoney);
					} else if (feeType.equals("deposit")) {
						LoginPage.bank.setDepositFee(newFeeMoney);
					} else if (feeType.equals("account")) {
						LoginPage.bank.setCloseAccountFee(newFeeMoney);
					} 
					LoginPage.bank.updateDB();
					message = "Updated Fee Successfully!";
					System.out.println("Fee is updated");
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
