package com.projetofinal.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.projetofinal.controller.AgendaController;
import com.projetofinal.dao.AgendaDAO;
import com.projetofinal.entities.Agenda;
import com.projetofinal.entities.Usuario;
import com.toedter.calendar.JCalendar;

public class AgendaView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome;
    private JTextField txtDescricao;
    private JTextArea textArea;
    private AgendaController agendaController;
    private JCalendar calendar;
    private Usuario usuario;

    public AgendaView(Connection connection, Usuario usuario) {
    	this.usuario = usuario;
        agendaController = new AgendaController(new AgendaDAO(connection), usuario);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 650, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);

        txtNome = new JTextField();
        panel.add(txtNome);
        txtNome.setColumns(10);

        txtDescricao = new JTextField();
        panel.add(txtDescricao);
        txtDescricao.setColumns(10);

        JButton btnAdd = new JButton("Adicionar");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String descricao = txtDescricao.getText();
                agendaController.createAgenda(nome, descricao);
                updateTextArea();
            }
        });
        panel.add(btnAdd);

        JButton btnUpdate = new JButton("Atualizar");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int agendaId = usuario.getId();
                String nome = txtNome.getText();
                String descricao = txtDescricao.getText();
                agendaController.updateAgenda(agendaId, nome, descricao);
                updateTextArea();
            }
        });
        panel.add(btnUpdate);

        JButton btnDelete = new JButton("Excluir");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int agendaId = usuario.getId();
                agendaController.deleteAgenda(agendaId);
                updateTextArea();
            }
        });
        panel.add(btnDelete);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);

        calendar = new JCalendar();
        contentPane.add(calendar, BorderLayout.SOUTH);

        updateTextArea();
    }

    private void updateTextArea() {
        List<Agenda> agendas = agendaController.getAllAgendas(usuario.getId());
        textArea.setText("");
        for (Agenda agenda : agendas) {
            textArea.append("ID: " + agenda.getId() + ", Nome: " + agenda.getNome() + ", Descrição: "
                    + agenda.getDescricao() + "\n");
        }
    }
}
