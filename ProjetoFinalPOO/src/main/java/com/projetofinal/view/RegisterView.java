package com.projetofinal.view;

import com.projetofinal.controller.UsuarioController;
import com.projetofinal.entities.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegisterView extends JFrame {
	private UsuarioController usuarioController;
    private JPanel contentPane;
    private JTextField tfNome;
    private JTextField tfSobrenome;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfEmail;
    private JFormattedTextField ftfDataNascimento;
    private ButtonGroup btnGroupSexo;
    private JRadioButton rdbtnMasculino;
    private JRadioButton rdbtnFeminino;
    private JRadioButton rdbtnNaoInformar;
    private JTextField tfFoto;

    public RegisterView(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setTitle("Cadastro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 638, 397);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 11, 602, 336);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblCadastro = new JLabel("Cadastro ");
        lblCadastro.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblCadastro.setBounds(263, 0, 85, 33);
        panel.add(lblCadastro);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setBounds(10, 47, 46, 14);
        panel.add(lblNome);

        tfNome = new JTextField();
        tfNome.setBounds(83, 44, 182, 20);
        panel.add(tfNome);
        tfNome.setColumns(10);

        JLabel lblSobrenome = new JLabel("Sobrenome");
        lblSobrenome.setBounds(10, 72, 54, 14);
        panel.add(lblSobrenome);

        tfSobrenome = new JTextField();
        tfSobrenome.setBounds(83, 69, 182, 20);
        panel.add(tfSobrenome);
        tfSobrenome.setColumns(10);

        JLabel lblDaTAnASC = new JLabel("Data de Nascimento");
        lblDaTAnASC.setBounds(308, 47, 116, 14);
        panel.add(lblDaTAnASC);

        ftfDataNascimento = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        ftfDataNascimento.setBounds(434, 44, 153, 20);
        panel.add(ftfDataNascimento);

        JLabel lblUsername = new JLabel("Nome de Usuario");
        lblUsername.setBounds(308, 72, 105, 14);
        panel.add(lblUsername);

        tfUsername = new JTextField();
        tfUsername.setBounds(434, 69, 154, 20);
        panel.add(tfUsername);
        tfUsername.setColumns(10);

        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setBounds(10, 97, 46, 14);
        panel.add(lblSenha);

        tfPassword = new JTextField();
        tfPassword.setBounds(83, 94, 182, 20);
        panel.add(tfPassword);
        tfPassword.setColumns(10);

        JLabel lblSexo = new JLabel("Gênero");
        lblSexo.setBounds(10, 131, 46, 14);
        panel.add(lblSexo);

        JPanel panelSexo = new JPanel();
        panelSexo.setBounds(83, 131, 182, 87);
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

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(308, 97, 46, 14);
        panel.add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(434, 94, 153, 20);
        panel.add(tfEmail);
        tfEmail.setColumns(10);

        JLabel lblFoto = new JLabel("URL da Foto");
        lblFoto.setBounds(308, 163, 105, 14);
        panel.add(lblFoto);

        tfFoto = new JTextField();
        tfFoto.setBounds(434, 160, 153, 20);
        panel.add(tfFoto);
        tfFoto.setColumns(10);

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
        btnCadastrar.setBounds(58, 255, 94, 33);
        panel.add(btnCadastrar);

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarUsuario();
            }
        });
        btnAtualizar.setBounds(189, 255, 94, 33);
        panel.add(btnAtualizar);

        Button btnExcluir = new Button("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirUsuario();
            }
        });
        btnExcluir.setBounds(319, 255, 94, 33);
        panel.add(btnExcluir);
    }

    private void cadastrarUsuario() {
        if (validarCampos()) {
            Usuario usuario = criarUsuarioAPartirDosCampos();
            usuarioController.createUser(usuario);
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            limparCampos();
        }
    }

    private void atualizarUsuario() {
        String username = tfUsername.getText();
        Usuario usuario = usuarioController.getUserByUsername(username);
        if (usuario != null) {
            if (validarCampos()) {
                usuario = criarUsuarioAPartirDosCampos();
                usuarioController.updateUser(usuario);
                JOptionPane.showMessageDialog(this, "Atualização realizada com sucesso!");
                limparCampos();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado!");
        }
    }

    private void excluirUsuario() {
        String username = tfUsername.getText();
        Usuario usuario = usuarioController.getUserByUsername(username);
        if (usuario != null) {
            usuarioController.deleteUser(username);
            JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!");
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado!");
        }
    }
    
    private boolean validarCampos() {
        if (tfNome.getText().isEmpty() || tfSobrenome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o campo Nome e Sobrenome.");
            return false;
        }
        if (ftfDataNascimento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o campo Data de Nascimento no formato yyyy-MM-dd.");
            return false;
        }
        if (!rdbtnMasculino.isSelected() && !rdbtnFeminino.isSelected() && !rdbtnNaoInformar.isSelected()) {
            JOptionPane.showMessageDialog(this, "Selecione o Gênero.");
            return false;
        }
        if (tfEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o campo Email.");
            return false;
        }
        if (tfUsername.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o campo Nome de Usuário.");
            return false;
        }
        if (tfPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o campo Senha.");
            return false;
        }
        return true;
    }

    private Usuario criarUsuarioAPartirDosCampos() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(tfNome.getText() + " " + tfSobrenome.getText());
        try {
            usuario.setDataNascimento(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(ftfDataNascimento.getText()).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        usuario.setGenero(verificarSelecaoRadioButtonSexo());
        usuario.setFotoPessoal(tfFoto.getText());
        usuario.setEmail(tfEmail.getText());
        usuario.setNomeUsuario(tfUsername.getText());
        usuario.setSenha(tfPassword.getText());
        return usuario;
    }

    private String verificarSelecaoRadioButtonSexo() {
        if (rdbtnMasculino.isSelected()) {
            return rdbtnMasculino.getText();
        } else if (rdbtnFeminino.isSelected()) {
            return rdbtnFeminino.getText();
        } else {
            return rdbtnNaoInformar.getText();
        }
    }

    private void limparCampos() {
        tfNome.setText("");
        tfSobrenome.setText("");
        tfUsername.setText("");
        tfPassword.setText("");
        tfEmail.setText("");
        ftfDataNascimento.setText("");
        tfFoto.setText("");
        btnGroupSexo.clearSelection();
    }
}
