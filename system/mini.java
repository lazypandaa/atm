package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class mini extends JFrame implements ActionListener {
    String pin;
    JButton button;

    mini(String pin) {
        this.pin = pin;
        getContentPane().setBackground(new Color(255, 255, 255));
        setSize(400, 600);
        setLocation(20, 20);
        setLayout(null);

        JLabel label2 = new JLabel("STATE BANK OF INDIA");
        label2.setFont(new Font("System", Font.BOLD, 15));
        label2.setBounds(150, 20, 200, 20);
        add(label2);

        JLabel label3 = new JLabel();
        label3.setBounds(20, 80, 300, 20);
        add(label3);

        // Create a JTextArea for transaction history
        JTextArea transactionArea = new JTextArea();
        transactionArea.setEditable(false);
        transactionArea.setLineWrap(true);
        transactionArea.setWrapStyleWord(true);

        // Add JTextArea to JScrollPane
        JScrollPane scrollPane = new JScrollPane(transactionArea);
        scrollPane.setBounds(20, 140, 360, 300); // Adjust the size as needed
        add(scrollPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());


        JLabel label4 = new JLabel();
        label4.setBounds(20, 450, 300, 20);
        add(label4);

        try {
            Connn c = new Connn();
            ResultSet resultSet = c.statement.executeQuery("select * from login where pin = '" + pin + "'");
            while (resultSet.next()) {
                label3.setText("Card Number:  " + resultSet.getString("card_number").substring(0, 4) + "XXXXXXXX" + resultSet.getString("card_number").substring(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int balance = 0;
            Connn c = new Connn();
            ResultSet resultSet = c.statement.executeQuery("select * from bank where pin = '" + pin + "'");
            while (resultSet.next()) {
                transactionArea.append(resultSet.getString("date") + "   " + resultSet.getString("type") + "   " + resultSet.getString("amount") + "\n");

                if (resultSet.getString("type").equals("Deposit")) {
                    balance += Integer.parseInt(resultSet.getString("amount"));
                } else {
                    balance -= Integer.parseInt(resultSet.getString("amount"));
                }
            }
            label4.setText("Your Total Balance is Rs " + balance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        button = new JButton("Exit");
        button.setBounds(20, 500, 100, 25);
        button.addActionListener(this);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new mini("");
    }
}
