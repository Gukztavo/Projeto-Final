package com.projetofinal.view;

import com.projetofinal.controller.UsuarioController;
import com.projetofinal.entities.Usuario;
import com.projetofinal.dao.UsuarioDAO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField textField;
    private JPasswordField passwordField;
    private UsuarioController userController;
    private UsuarioDAO usuarioDAO;

    public LoginView(UsuarioController userController) {
        this.userController = userController;

        setTitle("Login");
        setSize(443, 318);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setBounds(21, 35, 68, 22);
        getContentPane().add(userLabel);

        textField = new JTextField();
        textField.setBounds(99, 31, 213, 31);
        getContentPane().add(textField);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(21, 81, 68, 22);
        getContentPane().add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(99, 77, 213, 31);
        getContentPane().add(passwordField);

        JButton registerButton = new JButton("Registrar");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterView(userController).setVisible(true);
                dispose();
            }
        });
        registerButton.setBounds(21, 143, 121, 31);
        getContentPane().add(registerButton);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textField.getText().trim();
                String password = new String(passwordField.getPassword());

                boolean loggedIn = usuarioDAO.validarCredenciais(username, password);
                if (loggedIn) {
                    JOptionPane.showMessageDialog(LoginView.this, "Login bem-sucedido!");
                    // new TelaPrincipalView(userController).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginView.this, "Credenciais inválidas. Verifique o usuário e senha.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        loginButton.setBounds(191, 143, 121, 31);
        getContentPane().add(loginButton);
    }
}
