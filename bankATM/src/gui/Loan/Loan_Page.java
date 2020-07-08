package gui.Loan;

import javax.swing.*;

import gui.Client.ClientHomePage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Loan_Page {
    public static void main(String[] args) {
        placeComponents();
    }

    public static void placeComponents() {
        // Creating instance of JFrame
        JFrame frame = new JFrame("Loan Page");
        // Setting the width and height of frame
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        Font f = new Font("Arial", Font.CENTER_BASELINE,14);
        Font f1 = new Font("Arial", Font.CENTER_BASELINE,19);

        JLabel title = new JLabel("Loan Page ", SwingConstants.CENTER);
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

        JButton request_a_loan = new JButton("Request a Loan");
        request_a_loan.setBounds(175,150,300,60);
        request_a_loan.setFont(f1);
        request_a_loan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                request_a_loan.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Loan_Request.placeComponents();
            }
        });

        panel.add(request_a_loan);

        JButton pay_a_loan = new JButton("Pay a Loan");
        pay_a_loan.setBounds(175,240,300,60);
        pay_a_loan.setFont(f1);
        pay_a_loan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pay_a_loan.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Loan_pay.placeComponents();
            }
        });

        panel.add(pay_a_loan);

        // adding panel to frame
        frame.add(panel);
        /* calling user defined method for adding components
         * to the panel.
         */
        // Setting the frame visibility to true
        frame.setVisible(true);

    }
}
