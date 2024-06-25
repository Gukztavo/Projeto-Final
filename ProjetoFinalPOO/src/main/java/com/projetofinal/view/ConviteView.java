package com.projetofinal.view;

import com.projetofinal.controller.ConviteController;
import com.projetofinal.entities.Convite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ConviteView extends JFrame {
    private ConviteController conviteController;
    private int idUsuarioAtual;

    private JList<Convite> listaConvites;
    private JButton btnAceitar;
    private JButton btnRecusar;
    private JButton btnEnviarConvite;

    public ConviteView(ConviteController conviteController, int idUsuario) {
        this.conviteController = conviteController;
        this.idUsuarioAtual = idUsuario;

        initComponents();
    }
    
    private void initComponents() {
        setTitle("Convites");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        listaConvites = new JList<>();
        btnAceitar = new JButton("Aceitar");
        btnRecusar = new JButton("Recusar");
        btnEnviarConvite = new JButton("Enviar Convite");

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnAceitar);
        panelBotoes.add(btnRecusar);
        panelBotoes.add(btnEnviarConvite);

        add(new JScrollPane(listaConvites), BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);

        carregarConvites();

        btnAceitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Convite conviteSelecionado = listaConvites.getSelectedValue();
                if (conviteSelecionado!= null) {
                    try {
                        conviteController.responderConvite(conviteSelecionado.getId(), "Aceito");
                        carregarConvites();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnRecusar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Convite conviteSelecionado = listaConvites.getSelectedValue();
                if (conviteSelecionado!= null) {
                    try {
                        conviteController.responderConvite(conviteSelecionado.getId(), "Recusado");
                        carregarConvites();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnEnviarConvite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeUsuarioConvidado = JOptionPane.showInputDialog("Digite o nome do usuário a ser convidado:");
                if (nomeUsuarioConvidado!= null &&!nomeUsuarioConvidado.isEmpty()) {
                    try {
                        conviteController.enviarConvite(nomeUsuarioConvidado, 1, idUsuarioAtual);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void carregarConvites() {
        try {
            List<Convite> convites = conviteController.listarConvites(idUsuarioAtual);
            DefaultListModel<Convite> model = new DefaultListModel<>();
            for (Convite convite : convites) {
                model.addElement(convite);
            }
            listaConvites.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}