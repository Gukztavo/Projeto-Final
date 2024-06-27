package com.projetofinal.view;

import com.projetofinal.controller.AgendaController;
import com.projetofinal.dao.AgendaDAO;
import com.projetofinal.entities.Agenda;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterAgendaView extends JFrame {

    private AgendaController agendaController;
    private AgendaDAO agendaDAO;
    private Agenda agenda;
    private AgendaView agendaView; 

    private JTextField tfNome;
    private JTextArea taDescricao;

    public RegisterAgendaView(AgendaController agendaController, AgendaDAO agendaDAO, int agendaId, AgendaView agendaView) {
    	
    	try {
        	UIManager.setLookAndFeel(new NimbusLookAndFeel());

            UIManager.put("nimbusBase", new Color(255, 255, 255)); 
            UIManager.put("nimbusBlueGrey", new Color(137, 177, 177)); 
            UIManager.put("controlFont", new Font("Arial", Font.BOLD, 14)); 
        } catch (Exception e) {
            System.err.println("Erro ao aplicar tema: " + e.getMessage());
        }
    	
        this.agendaController = agendaController;
        this.agendaDAO = agendaDAO;
        this.agendaView = agendaView; 

        this.agenda = agendaDAO.read(agendaId);
        if (this.agenda == null) {
            this.agenda = new Agenda();
        }

        initComponents();
        if (agenda.getId() != 0) {
            carregarDadosAgenda();
        }
    }

    private void initComponents() {
        setTitle("Cadastro de Agenda");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nome da Agenda:"));
        tfNome = new JTextField();
        panel.add(tfNome);

        panel.add(new JLabel("Descri��o:"));
        taDescricao = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(taDescricao);
        panel.add(scrollPane);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDadosAgenda();
            }
        });
        panel.add(btnSalvar);

        add(panel);
    }

    private void carregarDadosAgenda() {
        tfNome.setText(agenda.getNome());
        taDescricao.setText(agenda.getDescricao());
    }

    private void salvarDadosAgenda() {
        String nome = tfNome.getText().trim();
        String descricao = taDescricao.getText().trim();

        if (nome.isEmpty() || descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e descri��o s�o obrigat�rios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        agenda.setNome(nome);
        agenda.setDescricao(descricao);

        if (agenda.getId() == 0) {
            agendaController.createAgenda(nome, descricao);
        } else {
            agendaController.updateAgenda(agenda.getId(), nome, descricao);
        }

        JOptionPane.showMessageDialog(this, "Dados da agenda salvos com sucesso!");
        agendaView.updateTextArea();
        dispose();
    }
}
