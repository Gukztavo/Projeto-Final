package com.projetofinal.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

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
    private AgendaController agendaController;
    private JCalendar calendar;
    private Usuario usuario;
    private AgendaDAO agendaDAO;
    private JTextArea textArea;

    public AgendaView(Connection connection, Usuario usuario){
    	
    	try {
        	UIManager.setLookAndFeel(new NimbusLookAndFeel());

            UIManager.put("nimbusBase", new Color(255, 255, 255)); 
            UIManager.put("nimbusBlueGrey", new Color(137, 177, 177)); 
            UIManager.put("controlFont", new Font("Arial", Font.BOLD, 14)); 
        } catch (Exception e) {
            System.err.println("Erro ao aplicar tema: " + e.getMessage());
        }
        
        this.usuario = usuario;
        this.agendaDAO = new AgendaDAO(connection);
        this.agendaController = new AgendaController(agendaDAO, usuario.getId());

        initComponents();
    }
    
    private void initComponents() {
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 650, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(5, 0, 624, 33);
        contentPane.add(panel);

        txtNome = new JTextField();
        txtNome.setText("Nome");
        panel.add(txtNome);
        txtNome.setColumns(10);

        txtDescricao = new JTextField();
        txtDescricao.setText("Descricao");
        panel.add(txtDescricao);
        txtDescricao.setColumns(10);

        JButton btnAdd = new JButton("Adicionar");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirRegistro(0);
            }
        });
        panel.add(btnAdd);

        JButton btnUpdate = new JButton("Atualizar");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int agendaId = Integer.parseInt(JOptionPane.showInputDialog("ID da agenda a ser atualizada:"));
                abrirRegistro(agendaId); 
            }
        });
        panel.add(btnUpdate);

        JButton btnDelete = new JButton("Excluir");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int agendaId = Integer.parseInt(JOptionPane.showInputDialog("ID da agenda que deseja excluir:"));
                agendaController.deleteAgenda(agendaId);
                updateTextArea();
            }
        });
        panel.add(btnDelete);

        calendar = new JCalendar();
        calendar.setBounds(5, 303, 624, 153);
        contentPane.add(calendar);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 44, 609, 248);
        contentPane.add(panel_1);
        panel_1.setLayout(null);
        
        textArea = new JTextArea();
        textArea.setBounds(10, 5, 589, 219);
        panel_1.add(textArea);
        textArea.setText("");

        updateTextArea();
    }

    private void abrirRegistro(int agendaId) {
        RegisterAgendaView registerView = new RegisterAgendaView(agendaController, agendaDAO, agendaId, this);
        registerView.setVisible(true);
    }

    public void updateTextArea() {
        List<Agenda> agendas = agendaController.getAllAgendas(usuario.getId());
        textArea.setText("");
        for (Agenda agenda : agendas) {
            textArea.append("ID: " + agenda.getId() + ", Nome: " + agenda.getNome() + ", Descrição: "
                    + agenda.getDescricao() + "\n");
        }
    }
}
