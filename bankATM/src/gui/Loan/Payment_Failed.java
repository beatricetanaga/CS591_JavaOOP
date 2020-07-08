package gui.Loan;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Payment_Failed {
    public static void main(String[] args) {

        placeComponents();
    }

    public static void placeComponents() {

        JFrame frame = new JFrame("Payment Failed");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        Font f = new Font("Arial", Font.CENTER_BASELINE,14);
        Font f1 = new Font("Arial", Font.CENTER_BASELINE,19);

        JLabel title = new JLabel("Payment Failed", SwingConstants.CENTER);
        title.setBounds(200, 50, 250, 60);
        title.setFont(f1);
        panel.add(title);

        JLabel message = new JLabel("Invaild Amount Entered", SwingConstants.CENTER);
        message.setBounds(125, 190, 400, 55);
        message.setFont(f1);
        panel.add(message);

        JButton go_back = new JButton("Go back");
        go_back.setBounds(80, 50, 100, 30);
        go_back.setOpaque(true);
        go_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_back.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Loan_Page.placeComponents();
            }
        });

        go_back.setFont(f);
        go_back.setBackground(Color.ORANGE);
        panel.add(go_back);

        frame.add(panel);
        frame.setVisible(true);
    }
}
