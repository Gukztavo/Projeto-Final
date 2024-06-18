package com.projetofinal.view;

import com.projetofinal.controller.UsuarioController;
import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.entities.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserDetailsView extends JFrame {

    private UsuarioController usuarioController;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;

    private JTextField txtNome;
    private JTextField txtSobrenome;
    private JFormattedTextField txtDataNascimento;
    private JTextField txtNomeUsuario;
    private JPasswordField txtSenha;
    private JTextField txtEmail;
    private JRadioButton rdbtnMasculino;
    private JRadioButton rdbtnFeminino;
    private JRadioButton rdbtnNaoInformar;
    private ButtonGroup btnGroupSexo;

    public UserDetailsView(UsuarioController usuarioController, UsuarioDAO usuarioDAO, Usuario usuario) {
        this.usuarioController = usuarioController;
        this.usuarioDAO = usuarioDAO;
        this.usuario = usuario;

        initComponents();
        carregarDadosUsuario();
    }

    private void initComponents() {
        setTitle("Detalhes do Usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 650, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 11, 614, 440);
        panel.setLayout(null);
        setContentPane(panel);

        JLabel lblCadastro = new JLabel("Detalhes do Usuário");
        lblCadastro.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblCadastro.setBounds(263, 0, 200, 33);
        panel.add(lblCadastro);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setBounds(10, 47, 46, 14);
        panel.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(83, 44, 182, 20);
        panel.add(txtNome);
        txtNome.setColumns(10);

        JLabel lblSobrenome = new JLabel("Sobrenome");
        lblSobrenome.setBounds(10, 72, 54, 14);
        panel.add(lblSobrenome);

        txtSobrenome = new JTextField();
        txtSobrenome.setBounds(83, 69, 182, 20);
        panel.add(txtSobrenome);
        txtSobrenome.setColumns(10);

        JLabel lblDataNascimento = new JLabel("Data de Nascimento");
        lblDataNascimento.setBounds(308, 47, 116, 14);
        panel.add(lblDataNascimento);

        txtDataNascimento = new JFormattedTextField();
        txtDataNascimento.setBounds(434, 44, 153, 20);
        panel.add(txtDataNascimento);

        JLabel lblNomeUsuario = new JLabel("Nome de Usuario");
        lblNomeUsuario.setBounds(308, 72, 105, 14);
        panel.add(lblNomeUsuario);

        txtNomeUsuario = new JTextField();
        txtNomeUsuario.setBounds(434, 69, 154, 20);
        panel.add(txtNomeUsuario);
        txtNomeUsuario.setColumns(10);

        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setBounds(10, 97, 46, 14);
        panel.add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(83, 94, 182, 20);
        panel.add(txtSenha);
        txtSenha.setColumns(10);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(308, 97, 46, 14);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(434, 94, 153, 20);
        panel.add(txtEmail);
        txtEmail.setColumns(10);

        JPanel panelSexo = new JPanel();
        panelSexo.setBounds(83, 131, 112, 79);
        panel.add(panelSexo);
        panelSexo.setLayout(null);

        rdbtnMasculino = new JRadioButton("Masculino");
        rdbtnMasculino.setBounds(6, 7, 100, 23);
        panelSexo.add(rdbtnMasculino);

        rdbtnFeminino = new JRadioButton("Feminino");
        rdbtnFeminino.setBounds(6, 33, 109, 23);
        panelSexo.add(rdbtnFeminino);

        rdbtnNaoInformar = new JRadioButton("Não Informar");
        rdbtnNaoInformar.setBounds(6, 59, 109, 23);
        panelSexo.add(rdbtnNaoInformar);

        btnGroupSexo = new ButtonGroup();
        btnGroupSexo.add(rdbtnMasculino);
        btnGroupSexo.add(rdbtnFeminino);
        btnGroupSexo.add(rdbtnNaoInformar);

        JLabel lblGenero = new JLabel("Genero");
        lblGenero.setBounds(10, 163, 46, 14);
        panel.add(lblGenero);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarDadosUsuario();
            }
        });
        btnAtualizar.setBounds(239, 319, 100, 22);
        panel.add(btnAtualizar);
    }

    private void carregarDadosUsuario() {
        txtNome.setText(usuario.getNomeCompleto());
        String[] nomeCompleto = usuario.getNomeCompleto().split(" ", 2);
        if (nomeCompleto.length > 1) {
            txtNome.setText(nomeCompleto[0]);
            txtSobrenome.setText(nomeCompleto[1]);
        } else {
            txtNome.setText(nomeCompleto[0]);
        }
        LocalDate dataNascimento = usuario.getDataNascimento().toLocalDate();
        txtDataNascimento.setText(dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtEmail.setText(usuario.getEmail());
        txtNomeUsuario.setText(usuario.getNomeUsuario());
        txtSenha.setText(usuario.getSenha());

        String genero = usuario.getGenero();
        if (genero.equalsIgnoreCase("Masculino")) {
            rdbtnMasculino.setSelected(true);
        } else if (genero.equalsIgnoreCase("Feminino")) {
            rdbtnFeminino.setSelected(true);
        } else {
            rdbtnNaoInformar.setSelected(true);
        }
    }

    private void atualizarDadosUsuario() {
        String nomeCompleto = txtNome.getText() + " " + txtSobrenome.getText();
        String dataNascimentoStr = txtDataNascimento.getText();
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Date sqlDate = Date.valueOf(dataNascimento);
        String email = txtEmail.getText();
        String nomeUsuario = txtNomeUsuario.getText();
        String senha = new String(txtSenha.getPassword());
        String genero = "";
        if (rdbtnMasculino.isSelected()) {
            genero = "Masculino";
        } else if (rdbtnFeminino.isSelected()) {
            genero = "Feminino";
        } else if (rdbtnNaoInformar.isSelected()) {
            genero = "Não Informar";
        }

        usuario.setNomeCompleto(nomeCompleto);
        usuario.setDataNascimento(sqlDate);
        usuario.setEmail(email);
        usuario.setNomeUsuario(nomeUsuario);
        usuario.setSenha(senha);
        usuario.setGenero(genero);

        usuarioController.updateUser(usuario);
        JOptionPane.showMessageDialog(this, "Dados do usuário atualizados com sucesso!");
    }
}
