package gui.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Stock_History {
    public static void main(String[] args) {

        placeComponents();

    }

    public static void placeComponents() {
        JFrame frame = new JFrame("My Stock History");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        Font f = new Font("Arial", Font.CENTER_BASELINE,14);
        Font f1 = new Font("Arial", Font.CENTER_BASELINE,19);

        JLabel failed_deposit = new JLabel("My Stock Page", SwingConstants.CENTER);
        failed_deposit.setBounds(200, 50, 250, 60);
        failed_deposit.setFont(f1);
        panel.add(failed_deposit);

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

        JLabel money = new JLabel("Display Stock Info Here", SwingConstants.CENTER);
        money.setBounds(150, 150, 350, 250);
        money.setOpaque(true);


        money.setFont(f1);
        money.setBackground(Color.GRAY);
        panel.add(money);

        frame.add(panel);
        frame.setVisible(true);
    }
}
