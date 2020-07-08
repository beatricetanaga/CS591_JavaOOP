package gui.MyTrans;

import javax.swing.*;

import account.Account;
import bankATM.Client;
import database.DBClient;
import database.DBTransaction;
import gui.Client.ClientHomePage;
import transaction.Transaction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Mytrans {
//	public static void setup() {
//		DBClient dbobj = new DBClient();
//		try {
////			client = dbobj.retrieveById("c7577a78-ec82-4e04-85c6-468f029617e6");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public static void main(String[] args) {
		placeComponents();
	}

	public static void placeComponents() {
//		setup();
		JFrame frame = new JFrame("My Transaction Page");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		Font f = new Font("Arial", Font.CENTER_BASELINE, 14);
		Font f1 = new Font("Arial", Font.CENTER_BASELINE, 19);

		JLabel title = new JLabel("My Transaction Page", SwingConstants.CENTER);
		title.setBounds(200, 50, 250, 60);
		title.setFont(f1);
		panel.add(title);

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

		JTextField time = new JTextField(20);
		time.setBounds(190, 200, 270, 40);

		JLabel enter = new JLabel("Please enter year-month (YYYY-MM)", SwingConstants.CENTER);
		enter.setBounds(125, 130, 400, 40);
		enter.setFont(f1);
		enter.setLabelFor(time);
		panel.add(time);
		panel.add(enter);

		JButton Enter = new JButton("Enter");
		Enter.setBounds(275, 300, 100, 40);
		Enter.setFont(f1);
		Enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = time.getText() + "-30";
				String[] ss = s.split("-");
				if (ss.length == 2) {
					System.out.println(ss[0] + " " + ss[1] + " " + ss[2]);
					Date d = Date.valueOf(s);
					ALL_Trans.placeComponents(d);
				}
				ALL_Trans.placeComponents(new Date(0));

			}
		});
		panel.add(Enter);

		frame.add(panel);
		frame.setVisible(true);
	}

}
