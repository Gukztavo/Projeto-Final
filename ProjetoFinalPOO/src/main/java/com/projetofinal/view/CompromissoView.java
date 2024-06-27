package com.projetofinal.view;

import com.projetofinal.dao.AgendaDAO;
import com.projetofinal.dao.CompromissoDAO;
import com.projetofinal.dao.ConviteDAO;
import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.entities.Compromisso;
import com.projetofinal.entities.Convite;
import com.projetofinal.entities.Usuario;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompromissoView extends JFrame {
    private CompromissoDAO compromissoDAO;
    private ConviteDAO conviteDAO;
    private AgendaDAO agendaDAO;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private JTextArea textArea;

    public CompromissoView(Connection connection, Usuario usuario) {
        try {
            // Aplicar tema Nimbus
            UIManager.setLookAndFeel(new NimbusLookAndFeel());

            // Customize NimbusLookAndFeel
            UIManager.put("nimbusBase", new Color(255, 255, 255)); // Set background color to white
            UIManager.put("nimbusBlueGrey", new Color(137, 177, 177)); // Set blue-grey color to dark grey
            UIManager.put("controlFont", new Font("Arial", Font.BOLD, 14)); // Set font to Arial bold 14
        } catch (Exception e) {
            System.err.println("Erro ao aplicar tema: " + e.getMessage());
        }

        this.usuario = usuario;
        this.compromissoDAO = new CompromissoDAO(connection);
        this.conviteDAO = new ConviteDAO(connection);
        this.agendaDAO = new AgendaDAO(connection);
        this.usuarioDAO = new UsuarioDAO(connection);
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
        JButton btnEditar = new JButton("Editar Compromisso");
        JButton btnExcluir = new JButton("Excluir Compromisso");
        JButton btnEnviarConvite = new JButton("Enviar Convite"); // Novo botão

        panelBotoes.add(btnCriar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnEnviarConvite); // Adicionar o novo botão

        btnCriar.addActionListener(e -> criarCompromisso());
        btnEditar.addActionListener(e -> editarCompromisso());
        btnExcluir.addActionListener(e -> excluirCompromisso());
        btnEnviarConvite.addActionListener(e -> enviarConvite()); // Adicionar listener para o novo botão

        panel.add(panelBotoes, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        visualizarCompromissos();

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

        panel.add(new JLabel("Data e Hora de Início (dd/MM/yyyy HH:mm):"));
        JTextField txtDataHoraInicio = new JTextField();
        panel.add(txtDataHoraInicio);

        panel.add(new JLabel("Data e Hora de Término (dd/MM/yyyy HH:mm):"));
        JTextField txtDataHoraTermino = new JTextField();
        panel.add(txtDataHoraTermino);

        panel.add(new JLabel("Local:"));
        JTextField txtLocal = new JTextField();
        panel.add(txtLocal);

        panel.add(new JLabel("Nome da Agenda:"));
        JTextField txtAgendaId = new JTextField();
        panel.add(txtAgendaId);

        panel.add(new JLabel("Nome dos Usuários Convidados (separados por vírgula):"));
        JTextField txtUsuariosConvidados = new JTextField();
        panel.add(txtUsuariosConvidados);

        panel.add(new JLabel("Data e Hora da Notificação (dd/MM/yyyy HH:mm):"));
        JTextField txtDataHoraNotificacao = new JTextField();
        panel.add(txtDataHoraNotificacao);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            String titulo = txtTitulo.getText();
            String descricao = txtDescricao.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            try {
                LocalDateTime dataHoraInicio = LocalDateTime.parse(txtDataHoraInicio.getText(), formatter);
                LocalDateTime dataHoraTermino = LocalDateTime.parse(txtDataHoraTermino.getText(), formatter);
                String local = txtLocal.getText();
                int agendaId = agendaDAO.getAgendaByAgenda(txtAgendaId.getText()).getId();
                List<String> usuariosConvidados = List.of(txtUsuariosConvidados.getText().split(","))
                        .stream()
                        .collect(Collectors.toList());
                List<Integer> usuariosConvidadosId = new ArrayList<>();
                for (String nome : usuariosConvidados) {
                	usuariosConvidadosId.add(usuarioDAO.getUserByUsername(nome).getId());
                }
                
                LocalDateTime dataHoraNotificacao = LocalDateTime.parse(txtDataHoraNotificacao.getText(), formatter);

                Compromisso compromisso = new Compromisso();
                compromisso.setTitulo(titulo);
                compromisso.setDescricao(descricao);
                compromisso.setDataHoraInicio(dataHoraInicio);
                compromisso.setDataHoraTermino(dataHoraTermino);
                compromisso.setLocal(local);
                compromisso.setAgendaId(agendaId);
                compromisso.setUsuariosConvidados(usuariosConvidadosId);
                compromisso.setDataHoraNotificacao(dataHoraNotificacao);
                
                compromissoDAO.createCompromisso(compromisso);
                JOptionPane.showMessageDialog(frame, "Compromisso criado com sucesso!");
                frame.dispose();
                visualizarCompromissos();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao formatar as datas. Por favor, use o formato dd/MM/yyyy HH:mm", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao converter um número. Verifique os campos numéricos.", "Erro de Conversão", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Ocorreu um erro ao criar o compromisso.", "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        panel.add(new JLabel());
        panel.add(btnSalvar);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void visualizarCompromissos() {
        List<Compromisso> compromissos = compromissoDAO.getCompromissosByUsuarioId(usuario.getId());
        StringBuilder compromissoList = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Compromisso compromisso : compromissos) {
            compromissoList.append("ID: ").append(compromisso.getId()).append("\n");
            compromissoList.append("Título: ").append(compromisso.getTitulo()).append("\n");
            compromissoList.append("Descrição: ").append(compromisso.getDescricao()).append("\n");
            compromissoList.append("Data e Hora de Início: ").append(compromisso.getDataHoraInicio().format(formatter)).append("\n");
            compromissoList.append("Data e Hora de Término: ").append(compromisso.getDataHoraTermino().format(formatter)).append("\n");
            compromissoList.append("Local: ").append(compromisso.getLocal()).append("\n");
            compromissoList.append("Nome da Agenda: ").append(agendaDAO.read(compromisso.getAgendaId()).getNome()).append("\n");
            
            List<Integer> usuariosConvidadosId = compromisso.getUsuariosConvidados();
            List<String> usuariosConvidados = new ArrayList<String>();
            
            for (Integer id : usuariosConvidadosId) {
            	usuariosConvidados.add(usuarioDAO.getUserById(id).getNomeUsuario());
            }
           
            compromissoList.append("Usuários Convidados: ").append(usuariosConvidados).append("\n");
            compromissoList.append("Data e Hora da Notificação: ").append(compromisso.getDataHoraNotificacao().format(formatter)).append("\n\n");
        }

        textArea.setText(compromissoList.toString());
    }

    private void editarCompromisso() {
        String compromissoIdStr = JOptionPane.showInputDialog(this, "Digite o ID do compromisso a ser alterado:", "Editar Compromisso", JOptionPane.PLAIN_MESSAGE);

        if (compromissoIdStr != null && !compromissoIdStr.trim().isEmpty()) {
            try {
                int compromissoId = Integer.parseInt(compromissoIdStr);
                Compromisso compromisso = compromissoDAO.getCompromissoById(compromissoId);

                if (compromisso != null) {
                    JFrame frame = new JFrame("Editar Compromisso");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(400, 500);
                    frame.setLocationRelativeTo(this);

                    JPanel panel = new JPanel(new GridLayout(10, 2, 5, 5));
                    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    panel.add(new JLabel("Título:"));
                    JTextField txtTitulo = new JTextField(compromisso.getTitulo());
                    panel.add(txtTitulo);

                    panel.add(new JLabel("Descrição:"));
                    JTextField txtDescricao = new JTextField(compromisso.getDescricao());
                    panel.add(txtDescricao);

                    panel.add(new JLabel("Data e Hora de Início (dd/MM/yyyy HH:mm):"));
                    JTextField txtDataHoraInicio = new JTextField(compromisso.getDataHoraInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                    panel.add(txtDataHoraInicio);

                    panel.add(new JLabel("Data e Hora de Término (dd/MM/yyyy HH:mm):"));
                    JTextField txtDataHoraTermino = new JTextField(compromisso.getDataHoraTermino().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                    panel.add(txtDataHoraTermino);

                    panel.add(new JLabel("Local:"));
                    JTextField txtLocal = new JTextField(compromisso.getLocal());
                    panel.add(txtLocal);

                    panel.add(new JLabel("ID da Agenda:"));
                    JTextField txtAgendaId = new JTextField(String.valueOf(compromisso.getAgendaId()));
                    panel.add(txtAgendaId);

                    panel.add(new JLabel("ID dos Usuários Convidados (separados por vírgula):"));
                    JTextField txtUsuariosConvidados = new JTextField(compromisso.getUsuariosConvidados().stream().map(String::valueOf).collect(Collectors.joining(",")));
                    panel.add(txtUsuariosConvidados);

                    panel.add(new JLabel("Data e Hora da Notificação (dd/MM/yyyy HH:mm):"));
                    JTextField txtDataHoraNotificacao = new JTextField(compromisso.getDataHoraNotificacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                    panel.add(txtDataHoraNotificacao);

                    JButton btnSalvar = new JButton("Atualizar");
                    btnSalvar.addActionListener(e -> {
                        String titulo = txtTitulo.getText();
                        String descricao = txtDescricao.getText();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                        try {
                            LocalDateTime dataHoraInicio = LocalDateTime.parse(txtDataHoraInicio.getText(), formatter);
                            LocalDateTime dataHoraTermino = LocalDateTime.parse(txtDataHoraTermino.getText(), formatter);
                            String local = txtLocal.getText();
                            int agendaId = Integer.parseInt(txtAgendaId.getText());
                            List<Integer> usuariosConvidados = List.of(txtUsuariosConvidados.getText().split(","))
                                    .stream()
                                    .map(Integer::parseInt)
                                    .collect(Collectors.toList());
                            LocalDateTime dataHoraNotificacao = LocalDateTime.parse(txtDataHoraNotificacao.getText(), formatter);

                            compromisso.setTitulo(titulo);
                            compromisso.setDescricao(descricao);
                            compromisso.setDataHoraInicio(dataHoraInicio);
                            compromisso.setDataHoraTermino(dataHoraTermino);
                            compromisso.setLocal(local);
                            compromisso.setAgendaId(agendaId);
                            compromisso.setUsuariosConvidados(usuariosConvidados);
                            compromisso.setDataHoraNotificacao(dataHoraNotificacao);

                            compromissoDAO.updateCompromisso(compromisso);
                            JOptionPane.showMessageDialog(frame, "Compromisso atualizado com sucesso!");
                            frame.dispose();
                            visualizarCompromissos();
                        } catch (DateTimeParseException ex) {
                            JOptionPane.showMessageDialog(frame, "Erro ao formatar as datas. Por favor, use o formato dd/MM/yyyy HH:mm", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Erro ao converter um número. Verifique os campos numéricos.", "Erro de Conversão", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Ocorreu um erro ao atualizar o compromisso.", "Erro", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    });

                    panel.add(new JLabel());
                    panel.add(btnSalvar);

                    frame.add(panel);
                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Compromisso não encontrado com o ID especificado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de ID", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void excluirCompromisso() {
        String compromissoIdStr = JOptionPane.showInputDialog(this, "Digite o ID do compromisso a ser excluído:", "Excluir Compromisso", JOptionPane.PLAIN_MESSAGE);

        if (compromissoIdStr != null && !compromissoIdStr.trim().isEmpty()) {
            try {
                int compromissoId = Integer.parseInt(compromissoIdStr);
                Compromisso compromisso = compromissoDAO.getCompromissoById(compromissoId);

                if (compromisso != null) {
                    int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o compromisso: " + compromisso.getTitulo() + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

                    if (confirmacao == JOptionPane.YES_OPTION) {
                        compromissoDAO.deleteCompromisso(compromissoId);
                        JOptionPane.showMessageDialog(this, "Compromisso excluído com sucesso!");
                        visualizarCompromissos();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Compromisso não encontrado com o ID especificado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de ID", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void enviarConvite() {
        String compromissoIdStr = JOptionPane.showInputDialog(this, "Digite o ID do compromisso para o qual deseja enviar um convite:", "Enviar Convite", JOptionPane.PLAIN_MESSAGE);

        if (compromissoIdStr != null && !compromissoIdStr.trim().isEmpty()) {
            try {
                int compromissoId = Integer.parseInt(compromissoIdStr);
                Compromisso compromisso = compromissoDAO.getCompromissoById(compromissoId);

                if (compromisso != null) {
                    String destinatarioIdStr = JOptionPane.showInputDialog(this, "Digite o ID do usuário destinatário:", "Enviar Convite", JOptionPane.PLAIN_MESSAGE);

                    if (destinatarioIdStr != null && !destinatarioIdStr.trim().isEmpty()) {
                        int destinatarioId = Integer.parseInt(destinatarioIdStr);

                        Convite convite = new Convite();
                        convite.setIdUsuarioConvidado(destinatarioId);
                        convite.setIdCompromisso(compromissoId);
                        convite.setStatus("PENDENTE");
                        convite.setDataConvite(new java.sql.Date(System.currentTimeMillis()));

                        conviteDAO.criarConvite(convite);

                        JOptionPane.showMessageDialog(this, "Convite enviado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(this, "ID do destinatário inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Compromisso não encontrado com o ID especificado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de ID", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
