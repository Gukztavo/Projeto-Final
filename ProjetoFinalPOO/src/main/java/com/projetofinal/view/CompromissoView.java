package com.projetofinal.view;

import com.projetofinal.dao.CompromissoDAO;
import com.projetofinal.entities.Compromisso;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CompromissoView extends JFrame {
    private CompromissoDAO compromissoDAO;

    public CompromissoView(CompromissoDAO compromissoDAO){
    	
    	try {
            // Aplicar tema Metal
        	UIManager.setLookAndFeel(new NimbusLookAndFeel());

            // Customize NimbusLookAndFeel
            UIManager.put("nimbusBase", new Color(255, 255, 255)); // Set background color to white
            UIManager.put("nimbusBlueGrey", new Color(137, 177, 177)); // Set blue-grey color to dark grey
            UIManager.put("controlFont", new Font("Arial", Font.BOLD, 14)); // Set font to Arial bold 14
            // UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (Exception e) {
            System.err.println("Erro ao aplicar tema: " + e.getMessage());
        }
        
        this.compromissoDAO = compromissoDAO;
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestão de Compromissos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnCriar = new JButton("Criar Compromisso");
        JButton btnVisualizar = new JButton("Visualizar Compromissos");
        JButton btnEditar = new JButton("Editar Compromisso");
        JButton btnExcluir = new JButton("Excluir Compromisso");

        panelBotoes.add(btnCriar);
        panelBotoes.add(btnVisualizar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnExcluir);

        btnCriar.addActionListener(e -> criarCompromisso());
        btnVisualizar.addActionListener(e -> visualizarCompromissos());
        btnEditar.addActionListener(e -> editarCompromisso());
        btnExcluir.addActionListener(e -> excluirCompromisso());

        panel.add(panelBotoes, BorderLayout.NORTH);
        add(panel);

        setVisible(true);
    }

    private void criarCompromisso() {
        JFrame frame = new JFrame("Criar Compromisso");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(10, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Título:"));
        JTextField txtTitulo = new JTextField();
        panel.add(txtTitulo);

        panel.add(new JLabel("Descrição:"));
        JTextField txtDescricao = new JTextField();
        panel.add(txtDescricao);

        panel.add(new JLabel("Data e Hora de Início:"));
        JTextField txtDataHoraInicio = new JTextField();
        panel.add(txtDataHoraInicio);

        panel.add(new JLabel("Data e Hora de Término:"));
        JTextField txtDataHoraTermino = new JTextField();
        panel.add(txtDataHoraTermino);

        panel.add(new JLabel("Local:"));
        JTextField txtLocal = new JTextField();
        panel.add(txtLocal);

        panel.add(new JLabel("ID da Agenda:"));
        JTextField txtAgendaId = new JTextField();
        panel.add(txtAgendaId);

        panel.add(new JLabel("ID dos Usuários Convidados (separados por vírgula):"));
        JTextField txtUsuariosConvidados = new JTextField();
        panel.add(txtUsuariosConvidados);

        panel.add(new JLabel("Data e Hora da Notificação:"));
        JTextField txtDataHoraNotificacao = new JTextField();
        panel.add(txtDataHoraNotificacao);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            String titulo = txtTitulo.getText();
            String descricao = txtDescricao.getText();
            LocalDateTime dataHoraInicio = LocalDateTime.parse(txtDataHoraInicio.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime dataHoraTermino = LocalDateTime.parse(txtDataHoraTermino.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String local = txtLocal.getText();
            int agendaId = Integer.parseInt(txtAgendaId.getText());
            List<Integer> usuariosConvidados = List.of(txtUsuariosConvidados.getText().split(",")).stream().map(Integer::parseInt).toList();
            LocalDateTime dataHoraNotificacao = LocalDateTime.parse(txtDataHoraNotificacao.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            Compromisso compromisso = new Compromisso();
            compromisso.setTitulo(titulo);
            compromisso.setDescricao(descricao);
            compromisso.setDataHoraInicio(dataHoraInicio);
            compromisso.setDataHoraTermino(dataHoraTermino);
            compromisso.setLocal(local);
            compromisso.setAgendaId(agendaId);
            compromisso.setUsuariosConvidados(usuariosConvidados);
            compromisso.setDataHoraNotificacao(dataHoraNotificacao);

            compromissoDAO.createCompromisso(compromisso);
            JOptionPane.showMessageDialog(frame, "Compromisso criado com sucesso!");
            frame.dispose();
        });

        panel.add(new JLabel());
        panel.add(btnSalvar);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void visualizarCompromissos() {
        List<Compromisso> compromissos = compromissoDAO.getAllCompromissos();
        StringBuilder compromissoList = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Compromisso compromisso : compromissos) {
            compromissoList.append("ID: ").append(compromisso.getId()).append("\n");
            compromissoList.append("Título: ").append(compromisso.getTitulo()).append("\n");
            compromissoList.append("Descrição: ").append(compromisso.getDescricao()).append("\n");
            compromissoList.append("Data e Hora de Início: ").append(compromisso.getDataHoraInicio().format(formatter)).append("\n");
            compromissoList.append("Data e Hora de Término: ").append(compromisso.getDataHoraTermino().format(formatter)).append("\n");
            compromissoList.append("Local: ").append(compromisso.getLocal()).append("\n");
            compromissoList.append("ID da Agenda: ").append(compromisso.getAgendaId()).append("\n");
            compromissoList.append("Usuários Convidados: ").append(compromisso.getUsuariosConvidados().toString()).append("\n");
            compromissoList.append("Data e Hora da Notificação: ").append(compromisso.getDataHoraNotificacao().format(formatter)).append("\n\n");
        }

        JTextArea textArea = new JTextArea(compromissoList.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JFrame frame = new JFrame("Visualizar Compromissos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(this);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void editarCompromisso() {
        // Implement the edit functionality
    }

    private void excluirCompromisso() {
        // Implement the delete functionality
    }
}
