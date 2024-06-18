package com.projetofinal.view;

import com.projetofinal.entities.Agenda;
import com.projetofinal.dao.AgendaDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AgendaView extends JFrame {
    private AgendaDAO agendaDAO;

    public AgendaView(AgendaDAO agendaDAO) {
        this.agendaDAO = agendaDAO;
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestão de Agendas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnCriar = new JButton("Criar Agenda");
        JButton btnEditar = new JButton("Editar Agenda");
        JButton btnExcluir = new JButton("Excluir Agenda");
        JButton btnVisualizar = new JButton("Visualizar Agendas");

        panelBotoes.add(btnCriar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnVisualizar);

        btnCriar.addActionListener(e -> criarAgenda());
        btnEditar.addActionListener(e -> editarAgenda());
        btnExcluir.addActionListener(e -> excluirAgenda());
        btnVisualizar.addActionListener(e -> visualizarAgendas());

        panel.add(panelBotoes, BorderLayout.NORTH);
        add(panel);

        setVisible(true);
    }

    private void criarAgenda() {
        JFrame frame = new JFrame("Criar Agenda");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nome:"));
        JTextField txtNome = new JTextField();
        panel.add(txtNome);

        panel.add(new JLabel("Descrição:"));
        JTextField txtDescricao = new JTextField();
        panel.add(txtDescricao);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();

            if (!nome.isEmpty()) {
                Agenda agenda = new Agenda(nome, descricao);
                agendaDAO.createAgenda(agenda);
                JOptionPane.showMessageDialog(frame, "Agenda criada com sucesso!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Nome da agenda é obrigatório.");
            }
        });

        panel.add(new JLabel());
        panel.add(btnSalvar);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void editarAgenda() {
        // Similar to criarAgenda, but with existing agenda data
    }

    private void excluirAgenda() {
        // Logic to delete an agenda
    }

    private void visualizarAgendas() {
        List<Agenda> agendas = agendaDAO.getAllAgendas();
        StringBuilder agendaList = new StringBuilder();

        for (Agenda agenda : agendas) {
            agendaList.append("Nome: ").append(agenda.getNome()).append("\n");
            agendaList.append("Descrição: ").append(agenda.getDescricao()).append("\n\n");
        }

        JTextArea textArea = new JTextArea(agendaList.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JFrame frame = new JFrame("Visualizar Agendas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(this);
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}
