package com.projetofinal.view;

import com.projetofinal.dao.CompromissoDAO;
import com.projetofinal.dao.ConviteDAO;
import com.projetofinal.entities.Compromisso;
import com.projetofinal.entities.Convite;
import com.projetofinal.entities.Usuario;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CompromissoView extends JFrame {
	private CompromissoDAO compromissoDAO;
	private ConviteDAO conviteDAO;
	private Usuario usuario;
	private JTextArea textArea;

	public CompromissoView(Connection connection, Usuario usuario) {
		try {
			
			UIManager.setLookAndFeel(new NimbusLookAndFeel());

			
			UIManager.put("nimbusBase", new Color(255, 255, 255)); 
			UIManager.put("nimbusBlueGrey", new Color(137, 177, 177)); 
			UIManager.put("controlFont", new Font("Arial", Font.BOLD, 14)); 
		} catch (Exception e) {
			System.err.println("Erro ao aplicar tema: " + e.getMessage());
		}

		this.usuario = usuario;
		this.compromissoDAO = new CompromissoDAO(connection);
		this.conviteDAO = new ConviteDAO(connection);
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
		JButton btnEnviarConvite = new JButton("Enviar Convite");
		JButton btnExportarCSV = new JButton("Exportar para CSV");
		JButton btnImportarCSV = new JButton("Importar de CSV");

		panelBotoes.add(btnCriar);
		panelBotoes.add(btnEditar);
		panelBotoes.add(btnExcluir);
		panelBotoes.add(btnEnviarConvite);
		panelBotoes.add(btnExportarCSV);
		panelBotoes.add(btnImportarCSV);

		btnCriar.addActionListener(e -> criarCompromisso());
		btnEditar.addActionListener(e -> editarCompromisso());
		btnExcluir.addActionListener(e -> excluirCompromisso());
		btnEnviarConvite.addActionListener(e -> enviarConvite());
		btnExportarCSV.addActionListener(e -> exportarCompromissosParaCSV());
		btnImportarCSV.addActionListener(e -> importarCompromissosDeCSV());

		panel.add(panelBotoes, BorderLayout.NORTH);

		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel.add(scrollPane, BorderLayout.CENTER);

		visualizarCompromissos();

		getContentPane().add(panel);
		setVisible(true);
	}

	private void criarCompromisso() {
		JFrame frame = new JFrame("Criar Compromisso");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(571, 500);
		frame.setLocationRelativeTo(this);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(null);

		JLabel label = new JLabel("Título:");
		label.setBounds(10, 13, 253, 35);
		panel.add(label);
		JTextField txtTitulo = new JTextField();
		txtTitulo.setBounds(268, 13, 253, 35);
		panel.add(txtTitulo);

		JLabel label_1 = new JLabel("Descrição");
		label_1.setBounds(10, 53, 253, 35);
		panel.add(label_1);
		JTextField txtDescricao = new JTextField();
		txtDescricao.setBounds(268, 53, 253, 35);
		panel.add(txtDescricao);

		JLabel label_2 = new JLabel("Data e Hora de Início (dd/MM/yyyy HH:mm):");
		label_2.setBounds(10, 93, 253, 35);
		panel.add(label_2);
		JTextField txtDataHoraInicio = new JTextField();
		txtDataHoraInicio.setBounds(268, 93, 253, 35);
		panel.add(txtDataHoraInicio);

		JLabel label_3 = new JLabel("Data e Hora de Término (dd/MM/yyyy HH:mm):");
		label_3.setBounds(10, 133, 253, 35);
		panel.add(label_3);
		JTextField txtDataHoraTermino = new JTextField();
		txtDataHoraTermino.setBounds(268, 133, 253, 35);
		panel.add(txtDataHoraTermino);

		JLabel label_4 = new JLabel("Local:");
		label_4.setBounds(10, 173, 253, 35);
		panel.add(label_4);
		JTextField txtLocal = new JTextField();
		txtLocal.setBounds(268, 173, 253, 35);
		panel.add(txtLocal);

		JLabel label_5 = new JLabel("ID da Agenda:");
		label_5.setBounds(10, 213, 253, 35);
		panel.add(label_5);
		JTextField txtAgendaId = new JTextField();
		txtAgendaId.setBounds(268, 213, 253, 35);
		panel.add(txtAgendaId);

		JLabel label_6 = new JLabel("ID dos Usuários Convidados (separados por vírgula):");
		label_6.setBounds(10, 253, 253, 35);
		panel.add(label_6);
		JTextField txtUsuariosConvidados = new JTextField();
		txtUsuariosConvidados.setBounds(268, 253, 253, 35);
		panel.add(txtUsuariosConvidados);

		JLabel label_7 = new JLabel("Data e Hora da Notificação (dd/MM/yyyy HH:mm):");
		label_7.setBounds(10, 293, 253, 35);
		panel.add(label_7);
		JTextField txtDataHoraNotificacao = new JTextField();
		txtDataHoraNotificacao.setBounds(268, 293, 253, 35);
		panel.add(txtDataHoraNotificacao);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(268, 333, 253, 35);
		btnSalvar.addActionListener(e -> {
			String titulo = txtTitulo.getText();
			String descricao = txtDescricao.getText();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

			try {
				LocalDateTime dataHoraInicio = LocalDateTime.parse(txtDataHoraInicio.getText(), formatter);
				LocalDateTime dataHoraTermino = LocalDateTime.parse(txtDataHoraTermino.getText(), formatter);
				String local = txtLocal.getText();
				int agendaId = Integer.parseInt(txtAgendaId.getText());
				List<Integer> usuariosConvidados = List.of(txtUsuariosConvidados.getText().split(",")).stream()
						.map(Integer::parseInt).collect(Collectors.toList());
				LocalDateTime dataHoraNotificacao = LocalDateTime.parse(txtDataHoraNotificacao.getText(), formatter);

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
				visualizarCompromissos();
			} catch (DateTimeParseException ex) {
				JOptionPane.showMessageDialog(frame,
						"Erro ao formatar as datas. Por favor, use o formato dd/MM/yyyy HH:mm", "Erro de Formatação",
						JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Erro ao converter um n�mero. Verifique os campos numéricos.",
						"Erro de Convers�o", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Ocorreu um erro ao criar o compromisso.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});

		JLabel label_8 = new JLabel();
		label_8.setBounds(10, 333, 253, 35);
		panel.add(label_8);
		panel.add(btnSalvar);

		JButton btnImportarCSV = new JButton("Importar de CSV");
		btnImportarCSV.setBounds(268, 376, 253, 35);
		btnImportarCSV.addActionListener(e -> importarCompromissosDeCSV());
		panel.add(btnImportarCSV);

		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	private void editarCompromisso() {
		String compromissoIdStr = JOptionPane.showInputDialog(this, "Digite o ID do compromisso a ser alterado:",
				"Editar Compromisso", JOptionPane.PLAIN_MESSAGE);

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
					JTextField txtDataHoraInicio = new JTextField(
							compromisso.getDataHoraInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
					panel.add(txtDataHoraInicio);

					panel.add(new JLabel("Data e Hora de Término (dd/MM/yyyy HH:mm):"));
					JTextField txtDataHoraTermino = new JTextField(
							compromisso.getDataHoraTermino().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
					panel.add(txtDataHoraTermino);

					panel.add(new JLabel("Local:"));
					JTextField txtLocal = new JTextField(compromisso.getLocal());
					panel.add(txtLocal);

					panel.add(new JLabel("ID da Agenda:"));
					JTextField txtAgendaId = new JTextField(String.valueOf(compromisso.getAgendaId()));
					panel.add(txtAgendaId);

					panel.add(new JLabel("ID dos Usuários Convidados (separados por vírgula):"));
					JTextField txtUsuariosConvidados = new JTextField(compromisso.getUsuariosConvidados().stream()
							.map(String::valueOf).collect(Collectors.joining(",")));
					panel.add(txtUsuariosConvidados);

					panel.add(new JLabel("Data e Hora da Notifica��o (dd/MM/yyyy HH:mm):"));
					JTextField txtDataHoraNotificacao = new JTextField(compromisso.getDataHoraNotificacao()
							.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
					panel.add(txtDataHoraNotificacao);

					JButton btnSalvar = new JButton("Atualizar");
					btnSalvar.addActionListener(e -> {
						String titulo = txtTitulo.getText();
						String descricao = txtDescricao.getText();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

						try {
							LocalDateTime dataHoraInicio = LocalDateTime.parse(txtDataHoraInicio.getText(), formatter);
							LocalDateTime dataHoraTermino = LocalDateTime.parse(txtDataHoraTermino.getText(),
									formatter);
							String local = txtLocal.getText();
							int agendaId = Integer.parseInt(txtAgendaId.getText());
							List<Integer> usuariosConvidados = List.of(txtUsuariosConvidados.getText().split(","))
									.stream().map(Integer::parseInt).collect(Collectors.toList());
							LocalDateTime dataHoraNotificacao = LocalDateTime.parse(txtDataHoraNotificacao.getText(),
									formatter);

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
							JOptionPane.showMessageDialog(frame,
									"Erro ao formatar as datas. Por favor, use o formato dd/MM/yyyy HH:mm",
									"Erro de Formata��o", JOptionPane.ERROR_MESSAGE);
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(frame,
									"Erro ao converter um n�mero. Verifique os campos num�ricos.", "Erro de Convers�o",
									JOptionPane.ERROR_MESSAGE);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(frame, "Ocorreu um erro ao atualizar o compromisso.", "Erro",
									JOptionPane.ERROR_MESSAGE);
							ex.printStackTrace();
						}
					});

					panel.add(new JLabel());
					panel.add(btnSalvar);

					frame.getContentPane().add(panel);
					frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(this, "Compromisso n�o encontrado com o ID especificado.", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de ID",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void excluirCompromisso() {
		String compromissoIdStr = JOptionPane.showInputDialog(this, "Digite o ID do compromisso a ser excluído:",
				"Excluir Compromisso", JOptionPane.PLAIN_MESSAGE);

		if (compromissoIdStr != null && !compromissoIdStr.trim().isEmpty()) {
			try {
				int compromissoId = Integer.parseInt(compromissoIdStr);
				Compromisso compromisso = compromissoDAO.getCompromissoById(compromissoId);

				if (compromisso != null) {
					int confirmacao = JOptionPane.showConfirmDialog(this,
							"Tem certeza que deseja excluir o compromisso: " + compromisso.getTitulo() + "?",
							"Confirmar Exclus�o", JOptionPane.YES_NO_OPTION);

					if (confirmacao == JOptionPane.YES_OPTION) {
						compromissoDAO.deleteCompromisso(compromissoId);
						JOptionPane.showMessageDialog(this, "Compromisso exclu�do com sucesso!");
						visualizarCompromissos();
					}
				} else {
					JOptionPane.showMessageDialog(this, "Compromisso n�o encontrado com o ID especificado.", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inv�lido. Por favor, digite um n�mero.", "Erro de ID",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void enviarConvite() {
		String compromissoIdStr = JOptionPane.showInputDialog(this,
				"Digite o ID do compromisso para o qual deseja enviar um convite:", "Enviar Convite",
				JOptionPane.PLAIN_MESSAGE);

		if (compromissoIdStr != null && !compromissoIdStr.trim().isEmpty()) {
			try {
				int compromissoId = Integer.parseInt(compromissoIdStr);
				Compromisso compromisso = compromissoDAO.getCompromissoById(compromissoId);

				if (compromisso != null) {
					String destinatarioIdStr = JOptionPane.showInputDialog(this, "Digite o ID do usu�rio destinatário:",
							"Enviar Convite", JOptionPane.PLAIN_MESSAGE);

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
						JOptionPane.showMessageDialog(this, "ID do destinatário inválido.", "Erro",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Compromisso não encontrado com o ID especificado.", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inv�lido. Por favor, digite um número.", "Erro de ID",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void exportarCompromissosParaCSV() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Salvar arquivo CSV");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
		fileChooser.setFileFilter(filter);
		int userSelection = fileChooser.showSaveDialog(this);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
				fileToSave = new File(fileToSave + ".csv");
			}
			List<Compromisso> compromissos = compromissoDAO.getCompromissosByUsuarioId(usuario.getId());

			try (FileWriter writer = new FileWriter(fileToSave)) {
				writer.append(
						"ID,T�tulo,Descri��o,Data/Hora In�cio,Data/Hora Fim,Local,AgendaId,Data/hora Notificacao\n");
				for (Compromisso compromisso : compromissos) {
					writer.append(compromisso.getId() + ",");
					writer.append(compromisso.getTitulo() + ",");
					writer.append(compromisso.getDescricao() + ",");
					writer.append(
							compromisso.getDataHoraInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
									+ ",");
					writer.append(
							compromisso.getDataHoraTermino().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
									+ ",");
					writer.append(compromisso.getLocal() + ",");
					writer.append(compromisso.getAgendaId() + ",");
					writer.append(compromisso.getUsuariosConvidados() + ",");
					writer.append(compromisso.getDataHoraNotificacao() + "\n");
				}
				JOptionPane.showMessageDialog(this, "Compromissos exportados com sucesso!");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Erro ao exportar compromissos: " + e.getMessage());
			}
		}
	}

	private void importarCompromissosDeCSV() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Importar arquivo CSV");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
		fileChooser.setFileFilter(filter);
		int userSelection = fileChooser.showOpenDialog(this);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();

			try (BufferedReader reader = new BufferedReader(new FileReader(fileToOpen))) {
				String line;
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				DateTimeFormatter notificationFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

				reader.readLine();

				while ((line = reader.readLine()) != null) {
					System.out.println("Lendo linha: " + line); 
					List<String> values = parseCSVLine(line);
					System.out.println("Valores extra�dos: " + values); 
					if (values.size() != 9) {
						throw new IllegalArgumentException("O arquivo CSV n�o está no formato esperado.");
					}

					int compromissoId = Integer.parseInt(values.get(0));
					if (compromissoDAO.getCompromissoById(compromissoId) != null) {
						throw new IllegalArgumentException(
								"Compromisso com ID " + compromissoId + " já existe. Não é poss�vel criar novamente.");
					}

					Compromisso compromisso = new Compromisso();
					compromisso.setId(compromissoId);
					compromisso.setTitulo(values.get(1));
					compromisso.setDescricao(values.get(2));

					try {
						compromisso.setDataHoraInicio(LocalDateTime.parse(values.get(3), dateTimeFormatter));
					} catch (DateTimeParseException e) {
						throw new IllegalArgumentException("Erro ao formatar Data/Hora Início: " + values.get(3)
								+ ". Use o formato dd/MM/yyyy HH:mm");
					}

					try {
						compromisso.setDataHoraTermino(LocalDateTime.parse(values.get(4), dateTimeFormatter));
					} catch (DateTimeParseException e) {
						throw new IllegalArgumentException("Erro ao formatar Data/Hora Fim: " + values.get(4)
								+ ". Use o formato dd/MM/yyyy HH:mm");
					}

					compromisso.setLocal(values.get(5));
					compromisso.setAgendaId(Integer.parseInt(values.get(6)));

					try {
						compromisso.setDataHoraNotificacao(LocalDateTime.parse(values.get(8), notificationFormatter));
					} catch (DateTimeParseException e) {
						throw new IllegalArgumentException("Erro ao formatar Data/hora Notificação: " + values.get(8)
								+ ". Use o formato yyyy-MM-dd'T'HH:mm");
					}

					String convidadosString = values.get(7).replace("[", "").replace("]", "").trim();
					if (!convidadosString.isEmpty()) {
						List<Integer> usuariosConvidados = Arrays.stream(convidadosString.split(",")).map(String::trim)
								.map(Integer::parseInt).collect(Collectors.toList());
						compromisso.setUsuariosConvidados(usuariosConvidados);
					} else {
						compromisso.setUsuariosConvidados(new ArrayList<>());
					}

					compromissoDAO.createCompromisso(compromisso);
				}
				JOptionPane.showMessageDialog(this, "Compromissos importados com sucesso!");
				visualizarCompromissos();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Erro ao importar compromissos: " + e.getMessage());
			} catch (DateTimeParseException e) {
				JOptionPane.showMessageDialog(this,
						"Erro ao formatar as datas. Verifique o formato das datas no arquivo CSV.",
						"Erro de Formata��o", JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this,
						"Erro ao converter um n�mero. Verifique os campos numéricos no arquivo CSV.",
						"Erro de Convers�o", JOptionPane.ERROR_MESSAGE);
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Erro no Formato do CSV",
						JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Ocorreu um erro ao importar compromissos.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	private List<String> parseCSVLine(String line) {
		List<String> result = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		boolean inQuotes = false;

		for (char ch : line.toCharArray()) {
			switch (ch) {
			case '"':
				inQuotes = !inQuotes;
				break;
			case ',':
				if (inQuotes) {
					current.append(ch);
				} else {
					result.add(current.toString().trim());
					current.setLength(0);
				}
				break;
			default:
				current.append(ch);
				break;
			}
		}

		result.add(current.toString().trim());
		return result;
	}

	private void visualizarCompromissos() {
		List<Compromisso> compromissos = compromissoDAO.getCompromissosByUsuarioId(usuario.getId());
		StringBuilder compromissoList = new StringBuilder();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		for (Compromisso compromisso : compromissos) {
			compromissoList.append("ID: ").append(compromisso.getId()).append("\n");
			compromissoList.append("T�tulo: ").append(compromisso.getTitulo()).append("\n");
			compromissoList.append("Descrição: ").append(compromisso.getDescricao()).append("\n");
			compromissoList.append("Data e Hora de In�cio: ").append(compromisso.getDataHoraInicio().format(formatter))
					.append("\n");
			compromissoList.append("Data e Hora de Término: ")
					.append(compromisso.getDataHoraTermino().format(formatter)).append("\n");
			compromissoList.append("Local: ").append(compromisso.getLocal()).append("\n");
			compromissoList.append("ID da Agenda: ").append(compromisso.getAgendaId()).append("\n");
			compromissoList.append("Usu�rios Convidados: ").append(compromisso.getUsuariosConvidados().toString())
					.append("\n");
			compromissoList.append("Data e Hora da Notificação: ")
					.append(compromisso.getDataHoraNotificacao().format(formatter)).append("\n\n");
		}

		textArea.setText(compromissoList.toString());
	}

}
