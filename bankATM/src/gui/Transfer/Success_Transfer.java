package gui.Transfer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Success_Transfer {
    public static void main(String[] args) {
        placeComponents();
    }

    public static void placeComponents() {
    	JFrame frame = new JFrame("Successful Transfer");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        
        panel.setLayout(null);

        Font f = new Font("Arial", Font.CENTER_BASELINE,14);
        Font f1 = new Font("Arial", Font.CENTER_BASELINE,19);

        JLabel title = new JLabel("Successful Transfer", SwingConstants.CENTER);
        title.setBounds(200, 50, 250, 60);
        title.setFont(f1);
        panel.add(title);

        JButton go_back = new JButton("Go back");
        go_back.setBounds(80, 50, 100, 30);
        go_back.setOpaque(true);

        go_back.setFont(f);
        go_back.setBackground(Color.ORANGE);
        panel.add(go_back);
        go_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Transfer_Page.placeComponents();
			}
		});

        
        frame.add(panel);
        frame.setVisible(true);
    }
}
