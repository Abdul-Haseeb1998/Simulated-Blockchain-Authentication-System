/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

// SignupFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public SignupFrame() {
        setTitle("Signup");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JLabel titleLabel = new JLabel("Signup");
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        usernameLabel.setForeground(Color.BLACK);

        usernameField = new JTextField(20);
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setMaximumSize(new Dimension(400, 40));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 24));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 22));
        passwordLabel.setForeground(Color.BLACK);

        passwordField = new JPasswordField(20);
          passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setMaximumSize(new Dimension(400, 40));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24));

        JButton signupButton = new JButton("Signup");
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setPreferredSize(new Dimension(1000, 50));
        signupButton.setFont(new Font("Arial", Font.BOLD, 22));
        signupButton.setBackground(new Color(135, 206, 250));
        signupButton.setForeground(Color.BLACK);
       
        signupButton.setFocusPainted(false);
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(SignupFrame.this, "Please enter username and password", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String encryptedPassword = encryptPassword(password);
                    Block block = new Block(username, encryptedPassword);
                    addBlockToBlockchain(block);
                    JOptionPane.showMessageDialog(SignupFrame.this, "Signup successful! You can now login.");
                    openLoginFrame();
                    dispose();
                }
            }
        });

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(50));
        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(50));
        panel.add(signupButton);

        setContentPane(panel);
    }

    private void addBlockToBlockchain(Block block) {
        Blockchain blockchain = new Blockchain();
        blockchain.loadBlockchainData("blockchain.txt");
        blockchain.addBlock(block);
        blockchain.saveBlockchainData("blockchain.txt");
    }

    private void openLoginFrame() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }

    private String encryptPassword(String password) {
        // Simple encryption technique (replace with your own encryption logic)
        StringBuilder encryptedPassword = new StringBuilder();
        for (char c : password.toCharArray()) {
            encryptedPassword.append((char) (c + 1));
        }
        return encryptedPassword.toString();
    }
}
