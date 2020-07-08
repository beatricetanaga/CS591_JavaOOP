package gui.Client;

import javax.swing.*;

import bankATM.Bank;
import bankATM.Client;
import bankATM.*;
import database.*;
import gui.Manager.*;
import manager.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginPage {
	public static Bank bank = null;

	public static void setObjects() throws SQLException {
		DBBank dbBankObj = new DBBank();
		bank = dbBankObj.retrieveById("testBank");
	}

	public static void main(String[] args) {
		try {
			setObjects();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		placeButtons();

		// Setting the frame visibility to true

	}

	public static void placeButtons() {
		JFrame frame = new JFrame("Bank Account");
		// Setting the width and height of frame
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Creating panel. This is same as a div tag in HTML We can create several
		 * panels and add them to specific positions in a JFrame. Inside panels we can
		 * add text fields, buttons and other components.
		 */
		JPanel panel = new JPanel();
		// adding panel to frame
		frame.add(panel);
		panel.setLayout(null);
		JLabel welcomeMessage = new JLabel("Welcome to the Bank!");
		welcomeMessage.setBounds(230, 40, 200, 40);
		panel.add(welcomeMessage);

		JButton clientLogin = new JButton("Client Login");
		clientLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createClientLoginPage();

			}

		});
		clientLogin.setBounds(210, 100, 150, 50);
		panel.add(clientLogin);

		JButton managerLogin = new JButton("Manager Login");
		managerLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createManagerLoginPage();

			}

		});
		managerLogin.setBounds(210, 165, 150, 50);
		panel.add(managerLogin);
		frame.setVisible(true);
	}

	private static void createClientLoginPage() {
		JFrame frame = new JFrame("Client Login Page");
		frame.setSize(650, 500);
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

		JTextField username = new JTextField(20);
		username.setBounds(210, 115, 150, 25);
		JLabel enterUserName = new JLabel("Username: ");
		enterUserName.setBounds(120, 100, 150, 50);
		enterUserName.setLabelFor(username);
		panel.add(username);
		panel.add(enterUserName);

		JPasswordField password = new JPasswordField(20);
		password.setBounds(210, 165, 150, 25);
		JLabel enterPassword = new JLabel("Password: ");
		enterPassword.setBounds(120, 150, 150, 50);
		enterPassword.setLabelFor(password);
		panel.add(password);
		panel.add(enterPassword);

		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String u = username.getText();
				String p = password.getPassword().toString();

				DBClient dbObj = new DBClient();
				Client client = null;
				try {
					client = dbObj.retrieveByEmail(u);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (client != null) {
					ClientHomePage.placeButtons(client);
				} else {
					System.out.println("No such registered email!");
				}
			}

		});
		login.setBounds(230, 250, 100, 20);
		panel.add(login);

		JButton newClient = new JButton("New Client");
		newClient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newClientRegistration();

			}

		});
		newClient.setBounds(230, 305, 100, 20);
		panel.add(newClient);

		frame.setVisible(true);
	}

	private static void newClientRegistration() {
		JFrame frame = new JFrame("New Client Registration");
		frame.setSize(650, 500);
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

		JTextField firstName = new JTextField(20);
		firstName.setBounds(210, 80, 150, 25);
		JLabel enterFirstName = new JLabel("First name: ");
		enterFirstName.setBounds(140, 65, 150, 50);
		enterFirstName.setLabelFor(firstName);
		panel.add(firstName);
		panel.add(enterFirstName);

		JTextField lastName = new JTextField(20);
		lastName.setBounds(210, 120, 150, 25);
		JLabel enterLastName = new JLabel("Last name: ");
		enterLastName.setBounds(140, 105, 150, 50);
		enterLastName.setLabelFor(lastName);
		panel.add(lastName);
		panel.add(enterLastName);

		JTextField dob = new JTextField(20);
		dob.setBounds(210, 160, 150, 25);
		JLabel enterDob = new JLabel("Date of birth: ");
		enterDob.setBounds(130, 145, 150, 50);
		enterDob.setLabelFor(dob);
		panel.add(dob);
		panel.add(enterDob);

		JTextField phone = new JTextField(20);
		phone.setBounds(210, 200, 150, 25);
		JLabel enterPhone = new JLabel("Phone number: ");
		enterPhone.setBounds(120, 185, 150, 50);
		enterPhone.setLabelFor(phone);
		panel.add(phone);
		panel.add(enterPhone);

		JTextField city = new JTextField(20);
		city.setBounds(210, 240, 150, 25);
		JLabel enterCity = new JLabel("City: ");
		enterCity.setBounds(180, 225, 150, 50);
		enterCity.setLabelFor(city);
		panel.add(city);
		panel.add(enterCity);

		JTextField country = new JTextField(20);
		country.setBounds(210, 280, 150, 25);
		JLabel enterCountry = new JLabel("Country: ");
		enterCountry.setBounds(155, 265, 150, 50);
		enterCountry.setLabelFor(country);
		panel.add(country);
		panel.add(enterCountry);

		JTextField email = new JTextField(20);
		email.setBounds(210, 320, 150, 25);
		JLabel enterEmail = new JLabel("Email: ");
		enterEmail.setBounds(170, 305, 150, 50);
		enterEmail.setLabelFor(email);
		panel.add(email);
		panel.add(enterEmail);

		JPasswordField password = new JPasswordField(20);
		password.setBounds(210, 165, 150, 25);
		JLabel enterPassword = new JLabel("Password: ");
		enterPassword.setBounds(145, 345, 150, 50);
		enterPassword.setLabelFor(password);
		panel.add(password);
		panel.add(enterPassword);
//		JTextField pw = new JTextField(20);
//		pw.setBounds(210, 360, 150, 25);
//		JLabel enterPw = new JLabel("Password: ");
//		enterPw.setBounds(145, 345, 150, 50);
//		enterPw.setLabelFor(pw);
//		panel.add(pw);
//		panel.add(enterPw);

		JButton register = new JButton("Register");
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ArrayList<String> userInfo = new ArrayList<String>();
				userInfo.add(firstName.getText());
				userInfo.add(lastName.getText());
				userInfo.add(dob.getText());
				userInfo.add(phone.getText());
				userInfo.add(city.getText());
				userInfo.add(country.getText());
				userInfo.add(email.getText());
				userInfo.add(password.getPassword().toString());
//				
				Person p = new Person("112", firstName.getText(), lastName.getText(), new Date(2000, 10, 10),
						phone.getText(), city.getText(), country.getText());
				Client client = new Client("NNNNNNNNNN", p, LoginPage.bank.getCurrentDate(), email.getText(),
						password.getPassword().toString());
				client.addToDB();
				
				// TODO Auto-generated method stub
				JFrame approved = new JFrame("Registration Successful");
				approved.setSize(650, 500);
				approved.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel panel = new JPanel();
				approved.add(panel);
				panel.setLayout(null);
				JLabel successful = new JLabel("Registration Successful");
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
		register.setBounds(210, 410, 100, 25);
		panel.add(register);

		frame.setVisible(true);
	}

	private static void createManagerLoginPage() {
		JFrame frame = new JFrame("Manager Login Page");
		frame.setSize(650, 500);
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

		JTextField username = new JTextField(20);
		username.setBounds(210, 115, 150, 25);
		JLabel enterUserName = new JLabel("Username: ");
		enterUserName.setBounds(120, 100, 150, 50);
		enterUserName.setLabelFor(username);
		panel.add(username);
		panel.add(enterUserName);

		JPasswordField password = new JPasswordField(20);
		password.setBounds(210, 165, 150, 25);
		JLabel enterPassword = new JLabel("Password: ");
		enterPassword.setBounds(120, 150, 150, 50);
		enterPassword.setLabelFor(password);
		panel.add(password);
		panel.add(enterPassword);

		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String u = username.getText();
				String p = password.getPassword().toString();

				DBManager dbObj = new DBManager();
				Manager manager = null;
				try {
					manager = dbObj.retrieveByEmail(u);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (manager != null) {
					ManagerHomePage.placeButtons(manager);
				} else {
					System.out.println("No such registered email!");
				}
			}

		});
		login.setBounds(230, 250, 100, 20);
		panel.add(login);

		frame.setVisible(true);
	}

}
