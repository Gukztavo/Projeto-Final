package com.projetofinal.view;

import com.projetofinal.controller.UsuarioController;
import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.entities.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private UsuarioController usuarioController;
    private UsuarioDAO usuarioDAO;

    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public LoginView(UsuarioController usuarioController, UsuarioDAO usuarioDAO) {
        this.usuarioController = usuarioController;
        this.usuarioDAO = usuarioDAO;

        initComponents();
    }

    private void initComponents() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Usuário:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        panel.add(txtSenha);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        panel.add(btnLogin);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });
        panel.add(btnRegistrar);

        add(panel);
    }

    private void realizarLogin() {
        String nomeUsuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());

        if (usuarioDAO.validarCredenciais(nomeUsuario, senha)) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            Usuario usuario = usuarioDAO.getUserByUsername(nomeUsuario);

            // Abrir a HomeView após o login bem-sucedido
            new HomeView(usuarioController, usuarioDAO, usuario).setVisible(true);
            dispose(); // Fechar a tela de login
        } else {
            JOptionPane.showMessageDialog(this, "Credenciais inválidas. Tente novamente.");
        }
    }

    private void abrirRegistro() {
        // Abrir a tela de registro (RegisterView)
        RegisterView registerView = new RegisterView(usuarioController, usuarioDAO, "");
        registerView.setVisible(true);
    }
}
