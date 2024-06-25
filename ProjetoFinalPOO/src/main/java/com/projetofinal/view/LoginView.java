package com.projetofinal.view;

import com.projetofinal.controller.UsuarioController;
import com.projetofinal.dao.CompromissoDAO;
import com.projetofinal.dao.ConviteDAO;
import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.entities.Usuario;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private UsuarioController usuarioController;
    private UsuarioDAO usuarioDAO;
    private CompromissoDAO compromissoDAO;
    private ConviteDAO conviteDAO;

    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public LoginView(UsuarioController usuarioController, UsuarioDAO usuarioDAO) {
    	
    	try {
            // Aplicar tema Metal
        	UIManager.setLookAndFeel(new NimbusLookAndFeel());

            // Customize NimbusLookAndFeel
            UIManager.put("nimbusBase", new Color(255, 255, 255)); // Set background color to white
            UIManager.put("nimbusBlueGrey", new Color(137, 177, 177)); // Set blue-grey color to dark grey
            UIManager.put("controlFont", new Font("Arial", Font.BOLD, 14)); // Set font to Arial bold 14
            // UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (Exception e) {
            System.err.println("Erro ao aplicar tema: " + e.getMessage());
        }
    	
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
            Usuario usuario = usuarioDAO.getUserByUsername(nomeUsuario);

            EventQueue.invokeLater(() -> {
                new HomeView(usuarioController, usuarioDAO, usuario, compromissoDAO, conviteDAO).setVisible(true);
            });
            
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciais inválidas. Tente novamente.");
        }
    }

    private void abrirRegistro() {
        RegisterView registerView = new RegisterView(usuarioController, usuarioDAO, "");
        registerView.setVisible(true);
    }
}
