package com.projetofinal.view;

import com.projetofinal.controller.UsuarioController;
import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.entities.Usuario;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.text.ParseException;

public class RegisterView extends JFrame {

    private UsuarioController usuarioController;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;

    private JTextField tfNome;
    private JTextField tfSobrenome;
    private JFormattedTextField formattedTextFieldDataNascimento;
    private JTextField tfNomeUsuario;
    private JPasswordField pfSenha;
    private JTextField tfEmail;
    private JRadioButton rdbtnMasculino;
    private JRadioButton rdbtnFeminino;
    private JRadioButton rdbtnNaoInformar;

    public RegisterView(UsuarioController usuarioController, UsuarioDAO usuarioDAO, String nomeUsuario) {
        this.usuarioController = usuarioController;
        this.usuarioDAO = usuarioDAO;

        this.usuario = usuarioDAO.getUserByUsername(nomeUsuario);
        if (this.usuario == null) {
            this.usuario = new Usuario();
        }

        initComponents();
        if (usuario.getNomeUsuario() != null) {
            carregarDadosUsuario();
        }
    }

    private void initComponents() {
        setTitle("Cadastro de Usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nome:"));
        tfNome = new JTextField();
        panel.add(tfNome);

        panel.add(new JLabel("Sobrenome:"));
        tfSobrenome = new JTextField();
        panel.add(tfSobrenome);

        panel.add(new JLabel("Data de Nascimento:"));
        try {
            MaskFormatter formatter = new MaskFormatter("##/##/####");
            formattedTextFieldDataNascimento = new JFormattedTextField(formatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        panel.add(formattedTextFieldDataNascimento);

        panel.add(new JLabel("Nome de Usuário:"));
        tfNomeUsuario = new JTextField();
        tfNomeUsuario.setEditable(true);
        panel.add(tfNomeUsuario);

        panel.add(new JLabel("Senha:"));
        pfSenha = new JPasswordField();
        panel.add(pfSenha);

        panel.add(new JLabel("E-mail:"));
        tfEmail = new JTextField();
        panel.add(tfEmail);

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new GridLayout(3, 1));
        panel.add(new JLabel("Gênero:"));
        panel_1.add(rdbtnMasculino = new JRadioButton("Masculino"));
        panel_1.add(rdbtnFeminino = new JRadioButton("Feminino"));
        panel_1.add(rdbtnNaoInformar = new JRadioButton("Não informar"));
        panel.add(panel_1);
        ButtonGroup btnGroupSexo = new ButtonGroup();
        btnGroupSexo.add(rdbtnMasculino);
        btnGroupSexo.add(rdbtnFeminino);
        btnGroupSexo.add(rdbtnNaoInformar);

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new GridLayout(1, 3));
        Button button = new Button("Cadastrar");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDadosUsuario();
            }
        });
        panel_2.add(button);

        Button button_1 = new Button("Atualizar");
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            }
        });
        panel_2.add(button_1);

        Button button_2 = new Button("Excluir");
        button_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            }
        });
        panel_2.add(button_2);

        panel.add(panel_2);

        add(panel);
    }

    private void carregarDadosUsuario() {
        tfNome.setText(usuario.getNomeCompleto().split(" ")[0]);
        tfSobrenome.setText(usuario.getNomeCompleto().split(" ")[1]);
        LocalDate dataNascimento = usuario.getDataNascimento().toLocalDate();
        formattedTextFieldDataNascimento.setText(dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        tfNomeUsuario.setText(usuario.getNomeUsuario());
        pfSenha.setText(usuario.getSenha());
        tfEmail.setText(usuario.getEmail());
        if (usuario.getGenero().equals("Masculino")) {
            rdbtnMasculino.setSelected(true);
        } else if (usuario.getGenero().equals("Feminino")) {
            rdbtnFeminino.setSelected(true);
        } else {
            rdbtnNaoInformar.setSelected(true);
        }
    }

    private void salvarDadosUsuario() {
        usuario.setNomeCompleto(tfNome.getText() + " " + tfSobrenome.getText());
        
        try {
            LocalDate dataNascimento = LocalDate.parse(formattedTextFieldDataNascimento.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Date dataNascimentoSQL = Date.valueOf(dataNascimento);
            usuario.setDataNascimento(dataNascimentoSQL);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida. Use o formato dd/MM/yyyy.");
            return;
        }
        
        usuario.setNomeUsuario(tfNomeUsuario.getText());
        usuario.setSenha(new String(pfSenha.getPassword()));
        usuario.setEmail(tfEmail.getText());
        if (rdbtnMasculino.isSelected()) {
            usuario.setGenero("Masculino");
        } else if (rdbtnFeminino.isSelected()) {
            usuario.setGenero("Feminino");
        } else {
            usuario.setGenero("Não informar");
        }
     
        if (usuario.getId() == 0) {
            usuarioDAO.createUser(usuario);
        } else {
            usuarioDAO.updateUser(usuario);
        }

        JOptionPane.showMessageDialog(this, "Dados do usuário atualizados com sucesso!");
        dispose();
    }
}