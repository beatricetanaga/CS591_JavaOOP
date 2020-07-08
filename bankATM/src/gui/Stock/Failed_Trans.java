package gui.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Failed_Trans {
    public static void main(String[] args) {

        placeComponents();
    }

    public static void placeComponents() {

        JFrame frame = new JFrame("Failed Transaction");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        panel.setLayout(null);

        Font f = new Font("Arial", Font.CENTER_BASELINE,14);
        Font f1 = new Font("Arial", Font.CENTER_BASELINE,19);

        JLabel withdraw_and_close_ = new JLabel("Failed Transaction", SwingConstants.CENTER);
        withdraw_and_close_.setBounds(200, 50, 250, 60);
        withdraw_and_close_.setFont(f1);
        panel.add(withdraw_and_close_);

        JLabel message = new JLabel("Error Message", SwingConstants.CENTER);
        message.setBounds(150, 150, 350, 100);
        message.setOpaque(true);

        message.setFont(f1);
        message.setBackground(Color.GRAY);
        panel.add(message);

        JButton Enter = new JButton("Retry");
        Enter.setBounds(200,275,100,40);
        Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Enter.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Buy_Amount.placeComponents(null);
            }
        });
        Enter.setFont(f1);
        panel.add(Enter);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(350,275,100,40);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Stock_Page.placeComponents();
            }
        });
        cancel.setFont(f1);
        panel.add(cancel);

        frame.add(panel);
        frame.setVisible(true);

    }
}
