/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

// LoginFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 22));
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

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setPreferredSize(new Dimension(1000, 50));
        loginButton.setFont(new Font("Arial", Font.BOLD, 22));
        loginButton.setBackground(new Color(135, 206, 250));
        loginButton.setForeground(Color.BLACK);
        
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Please enter username and password", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (validateCredentials(username, password)) {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Login successful! Welcome, " + username + "!");
                        displayUserInfo(username, password);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
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
        panel.add(loginButton);

        setContentPane(panel);
    }

    private boolean validateCredentials(String username, String password) {
        Blockchain blockchain = new Blockchain();
        blockchain.loadBlockchainData("blockchain.txt");
        for (int i = 0; i < blockchain.getSize(); i++) {
            Block block = blockchain.getBlock(i);
            if (block.getUsername().equals(username)) {
                String decryptedPassword = decryptPassword(block.getEncryptedPassword());
                return password.equals(decryptedPassword);
            }
        }
        return false;
    }

    private void displayUserInfo(String username, String password) {
        // Display user information or perform any other actions
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    private String decryptPassword(String encryptedPassword) {
        // Simple decryption technique (replace with your own decryption logic)
        StringBuilder decryptedPassword = new StringBuilder();
        for (char c : encryptedPassword.toCharArray()) {
            decryptedPassword.append((char) (c - 1));
        }
        return decryptedPassword.toString();
    }
}
