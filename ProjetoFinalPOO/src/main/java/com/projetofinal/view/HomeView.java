package com.projetofinal.view;

import com.projetofinal.controller.ConviteController;
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
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeView extends JFrame {

    private UsuarioController usuarioController;
    private ConviteController conviteController;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private CompromissoDAO compromissoDAO;
    private ConviteDAO conviteDAO;

    public HomeView(UsuarioController usuarioController, UsuarioDAO usuarioDAO, Usuario usuario,
                    CompromissoDAO compromissoDAO, ConviteDAO conviteDAO) {

        try {
            // Aplicar tema Metal
            UIManager.setLookAndFeel(new NimbusLookAndFeel());

            // Customize NimbusLookAndFeel
            UIManager.put("nimbusBase", new Color(255, 255, 255)); // Set background color to white
            UIManager.put("nimbusBlueGrey", new Color(137, 177, 177)); // Set blue-grey color to dark grey
            UIManager.put("controlFont", new Font("Arial", Font.BOLD, 14)); // Set font to Arial bold 14
        } catch (Exception e) {
            System.err.println("Erro ao aplicar tema: " + e.getMessage());
        }

        this.usuarioController = usuarioController;
        this.usuarioDAO = usuarioDAO;
        this.usuario = usuario;
        this.compromissoDAO = compromissoDAO;
        this.conviteDAO = conviteDAO;
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
                new AgendaView(usuarioDAO.getConnection(), usuario).setVisible(true);
            }
        });

        btnCompromissos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CompromissoView(compromissoDAO);
            }
        });

        btnConvites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConviteController conviteController = new ConviteController(usuarioDAO.getConnection());
                new ConviteView(conviteController, usuario.getId()).setVisible(true);
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

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
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

        panel.add(new JLabel("URL da Foto:"));
        panel.add(new JLabel(usuario.getFotoPessoal()));

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
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nome Completo:"));
        JTextField txtNomeCompleto = new JTextField(usuario.getNomeCompleto());
        panel.add(txtNomeCompleto);

        panel.add(new JLabel("Data de Nascimento:"));
        JTextField txtDataNascimento = new JTextField(usuario.getDataNascimento().toString());
        panel.add(txtDataNascimento);

        panel.add(new JLabel("Gênero:"));
        JPanel panelGenero = new JPanel(new GridLayout(1, 3));
        JRadioButton rdbtnMasculino = new JRadioButton("Masculino");
        JRadioButton rdbtnFeminino = new JRadioButton("Feminino");
        JRadioButton rdbtnNaoInformar = new JRadioButton("Não informar");
        ButtonGroup btnGroupSexo = new ButtonGroup();
        btnGroupSexo.add(rdbtnMasculino);
        btnGroupSexo.add(rdbtnFeminino);
        btnGroupSexo.add(rdbtnNaoInformar);
        panelGenero.add(rdbtnMasculino);
        panelGenero.add(rdbtnFeminino);
        panelGenero.add(rdbtnNaoInformar);

        if (usuario.getGenero().equals("Masculino")) {
            rdbtnMasculino.setSelected(true);
        } else if (usuario.getGenero().equals("Feminino")) {
            rdbtnFeminino.setSelected(true);
        } else {
            rdbtnNaoInformar.setSelected(true);
        }

        panel.add(panelGenero);

        panel.add(new JLabel("E-mail:"));
        JTextField txtEmail = new JTextField(usuario.getEmail());
        panel.add(txtEmail);

        panel.add(new JLabel("Nome de Usuário:"));
        JTextField txtNomeUsuario = new JTextField(usuario.getNomeUsuario());
        panel.add(txtNomeUsuario);

        panel.add(new JLabel("Senha:"));
        JTextField txtSenha = new JTextField(usuario.getSenha());
        panel.add(txtSenha);

        panel.add(new JLabel("URL da Foto:"));
        JTextField txtUrlFoto = new JTextField(usuario.getFotoPessoal());
        panel.add(txtUrlFoto);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validarEmail(txtEmail.getText())) {
                    JOptionPane.showMessageDialog(frame, "E-mail inválido. Verifique o formato do e-mail.");
                    return;
                }

                usuario.setNomeCompleto(txtNomeCompleto.getText());
                usuario.setDataNascimento(Date.valueOf(txtDataNascimento.getText()));
                usuario.setGenero(rdbtnMasculino.isSelected() ? "Masculino"
                        : rdbtnFeminino.isSelected() ? "Feminino" : "Não informar");
                usuario.setEmail(txtEmail.getText());
                usuario.setNomeUsuario(txtNomeUsuario.getText());
                usuario.setSenha(txtSenha.getText());
                usuario.setFotoPessoal(txtUrlFoto.getText());

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
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir sua conta?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            usuarioDAO.deleteUser(usuario.getNomeUsuario());
            JOptionPane.showMessageDialog(this, "Conta excluída com sucesso.");
        }
    }

    private boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
