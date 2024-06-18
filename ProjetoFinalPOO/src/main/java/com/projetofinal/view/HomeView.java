package com.projetofinal.view;

import com.projetofinal.controller.UsuarioController;
import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.entities.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class HomeView extends JFrame {

    private UsuarioController usuarioController;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;

    public HomeView(UsuarioController usuarioController, UsuarioDAO usuarioDAO, Usuario usuario) {
        this.usuarioController = usuarioController;
        this.usuarioDAO = usuarioDAO;
        this.usuario = usuario;
        initComponents();
    }

    private void initComponents() {
        setTitle("Agenda e Calendário - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new GridLayout(4, 1, 0, 20)); 
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnAgendas = new JButton("Gerenciar Agendas");
        JButton btnCompromissos = new JButton("Gerenciar Compromissos");
        JButton btnConvites = new JButton("Gerenciar Convites");
        JButton btnDadosConta = new JButton("Dados da Conta");

        panelBotoes.add(btnAgendas);
        panelBotoes.add(btnCompromissos);
        panelBotoes.add(btnConvites);
        panelBotoes.add(btnDadosConta);

        btnAgendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeView.this, "Abrir tela de gerenciamento de agendas");
            }
        });

        btnCompromissos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeView.this, "Abrir tela de gerenciamento de compromissos");
            }
        });

        btnConvites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeView.this, "Abrir tela de gerenciamento de convites");
            }
        });

        btnDadosConta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirDadosUsuario();
            }
        });

        contentPane.add(panelBotoes, BorderLayout.CENTER);

        setVisible(true);
    }

    private void exibirDadosUsuario() {
        JFrame frame = new JFrame("Dados do Usuário");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nome Completo:"));
        panel.add(new JLabel(usuario.getNomeCompleto()));

        panel.add(new JLabel("Data de Nascimento:"));
        panel.add(new JLabel(usuario.getDataNascimento().toString()));

        panel.add(new JLabel("Gênero:"));
        panel.add(new JLabel(usuario.getGenero()));

        panel.add(new JLabel("E-mail:"));
        panel.add(new JLabel(usuario.getEmail()));

        panel.add(new JLabel("Nome de Usuário:"));
        panel.add(new JLabel(usuario.getNomeUsuario()));

        panel.add(new JLabel("Senha:"));
        JTextField tfSenha = new JTextField(usuario.getSenha());
        panel.add(new JLabel(usuario.getSenha()));

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarUsuario(usuario);
                frame.dispose(); 
            }
        });

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirUsuario(usuario);
                frame.dispose(); 
                dispose(); 
            }
        });

        panel.add(btnAtualizar);
        panel.add(btnExcluir);

        frame.add(panel);
        frame.setVisible(true);
    }


    private void atualizarUsuario(Usuario usuario) {
        JFrame frame = new JFrame("Atualizar Dados do Usuário");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nome Completo:"));
        JTextField txtNomeCompleto = new JTextField(usuario.getNomeCompleto());
        panel.add(txtNomeCompleto);

        panel.add(new JLabel("Data de Nascimento:"));
        JTextField txtDataNascimento = new JTextField(usuario.getDataNascimento().toString());
        panel.add(txtDataNascimento);

        panel.add(new JLabel("Gênero:"));
        JTextField txtGenero = new JTextField(usuario.getGenero());
        panel.add(txtGenero);

        panel.add(new JLabel("E-mail:"));
        JTextField txtEmail = new JTextField(usuario.getEmail());
        panel.add(txtEmail);

        panel.add(new JLabel("Nome de Usuário:"));
        JTextField txtNomeUsuario = new JTextField(usuario.getNomeUsuario());
        panel.add(txtNomeUsuario);

        panel.add(new JLabel("Senha:"));
        JTextField txtSenha = new JTextField(usuario.getSenha());
        panel.add(txtSenha); 

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuario.setNomeCompleto(txtNomeCompleto.getText());
                usuario.setDataNascimento(Date.valueOf(txtDataNascimento.getText()));
                usuario.setGenero(txtGenero.getText());
                usuario.setEmail(txtEmail.getText());
                usuario.setNomeUsuario(txtNomeUsuario.getText());
                usuario.setSenha(txtSenha.getText());

                usuarioDAO.updateUser(usuario);
                JOptionPane.showMessageDialog(frame, "Dados atualizados com sucesso!");
                frame.dispose(); 
            }
        });

        panel.add(btnSalvar);

        frame.add(panel);
        frame.setVisible(true);
    }


    private void excluirUsuario(Usuario usuario) {
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir sua conta?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            usuarioDAO.deleteUser(usuario.getNomeUsuario());
            JOptionPane.showMessageDialog(this, "Conta excluída com sucesso.");
        }
    }
}
