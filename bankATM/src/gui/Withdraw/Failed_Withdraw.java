package gui.Withdraw;
import account.*;
import manager.*;
import transaction.*;
import database.*;
import bankATM.*;
import bankATM.Currency;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Failed_Withdraw {
	public static void main(String[] args) {
		placeComponents();
	}

	public static void placeComponents() {
		JFrame frame = new JFrame("Failed Withdraw");
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

        panel.setLayout(null);

        Font f = new Font("Arial", Font.CENTER_BASELINE,14);
        Font f1 = new Font("Arial", Font.CENTER_BASELINE,19);

		JLabel failed_withdraw = new JLabel("Failed Withdraw", SwingConstants.CENTER);
		failed_withdraw.setBounds(200, 50, 250, 60);
		failed_withdraw.setFont(f1);
        panel.add(failed_withdraw);

        JButton go_back = new JButton("Go back");
        go_back.setBounds(80, 50, 100, 30);
        go_back.setOpaque(true);
        go_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Withdraw_Page.placeComponents();
			}
		});

        JLabel money = new JLabel("Failed to Withdraw!", SwingConstants.CENTER);
		money.setBounds(150, 150, 350, 100);
		money.setOpaque(true);

		money.setFont(f1);
		money.setBackground(Color.GRAY);
		panel.add(money);
		
        go_back.setFont(f);
        go_back.setBackground(Color.ORANGE);
        panel.add(go_back);
        
        frame.add(panel);
		frame.setVisible(true);
    }

}
