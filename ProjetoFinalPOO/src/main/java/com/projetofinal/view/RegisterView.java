package com.projetofinal.view;

import com.projetofinal.controller.UsuarioController;
import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.entities.Usuario;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterView extends JFrame {

	private UsuarioController usuarioController;
	private UsuarioDAO usuarioDAO;
	private Usuario usuario;

	private JTextField tfNome;
	private JTextField tfSobrenome;
	private JFormattedTextField formattedTextFieldDataNascimento;
	private JTextField tfNomeUsuario;
	private JPasswordField pfSenha;
	private JTextField tfEmail;
	private JTextField tfUrlFoto;

	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFeminino;
	private JRadioButton rdbtnNaoInformar;

	public RegisterView(UsuarioController usuarioController, UsuarioDAO usuarioDAO, String nomeUsuario) {
		
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
		
		this.usuarioController = usuarioController;
		this.usuarioDAO = usuarioDAO;

		this.usuario = usuarioDAO.getUserByUsername(nomeUsuario);
		if (this.usuario == null) {
			this.usuario = new Usuario();
		}

		initComponents();
		if (usuario.getNomeUsuario() != null) {
			carregarDadosUsuario();
		}
	}

	private void initComponents() {
		setTitle("Cadastro de Usuário");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(650, 550);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(11, 2, 5, 5)); 
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panel.add(new JLabel("Nome:"));
		tfNome = new JTextField();
		panel.add(tfNome);

		panel.add(new JLabel("Sobrenome:"));
		tfSobrenome = new JTextField();
		panel.add(tfSobrenome);

		panel.add(new JLabel("Data de Nascimento:"));
		try {
			MaskFormatter formatter = new MaskFormatter("##/##/####");
			formattedTextFieldDataNascimento = new JFormattedTextField(formatter);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		panel.add(formattedTextFieldDataNascimento);

		panel.add(new JLabel("Nome de Usuário:"));
		tfNomeUsuario = new JTextField();
		panel.add(tfNomeUsuario);

		panel.add(new JLabel("Senha:"));
		pfSenha = new JPasswordField();
		panel.add(pfSenha);

		panel.add(new JLabel("E-mail:"));
		tfEmail = new JTextField();
		panel.add(tfEmail);

		panel.add(new JLabel("URL da Foto:"));
		tfUrlFoto = new JTextField();
		panel.add(tfUrlFoto);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new GridLayout(3, 1));
		panel.add(new JLabel("Gênero:"));
		panel_1.add(rdbtnMasculino = new JRadioButton("Masculino"));
		panel_1.add(rdbtnFeminino = new JRadioButton("Feminino"));
		panel_1.add(rdbtnNaoInformar = new JRadioButton("Não informar"));
		panel.add(panel_1);
		ButtonGroup btnGroupSexo = new ButtonGroup();
		btnGroupSexo.add(rdbtnMasculino);
		btnGroupSexo.add(rdbtnFeminino);
		btnGroupSexo.add(rdbtnNaoInformar);

		Button button = new Button("Cadastrar");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvarDadosUsuario();
			}
		});
		panel.add(button);

		add(panel);
	}

	private void carregarDadosUsuario() {
		tfNome.setText(usuario.getNomeCompleto().split(" ")[0]);
		tfSobrenome.setText(usuario.getNomeCompleto().split(" ")[1]);
		LocalDate dataNascimento = usuario.getDataNascimento().toLocalDate();
		formattedTextFieldDataNascimento.setText(dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		tfNomeUsuario.setText(usuario.getNomeUsuario());
		pfSenha.setText(usuario.getSenha());
		tfEmail.setText(usuario.getEmail());
		if (usuario.getFotoPessoal() != null && usuario.getFotoPessoal().length > 0) {
	        String fotoBase64 = Base64.getEncoder().encodeToString(usuario.getFotoPessoal());
	        tfUrlFoto.setText(fotoBase64);
	    } else {
	        tfUrlFoto.setText("");
	    }
		if (usuario.getGenero().equals("Masculino")) {
			rdbtnMasculino.setSelected(true);
		} else if (usuario.getGenero().equals("Feminino")) {
			rdbtnFeminino.setSelected(true);
		} else {
			rdbtnNaoInformar.setSelected(true);
		}
	}

	private void salvarDadosUsuario() {
	    if (!validarEmail(tfEmail.getText())) {
	        JOptionPane.showMessageDialog(this, "E-mail inválido. Verifique o formato do e-mail.");
	        return;
	    }

	    String nomeUsuario = tfNomeUsuario.getText().trim();
	    if (usuarioDAO.getUserByUsername(nomeUsuario) != null && !nomeUsuario.equals(usuario.getNomeUsuario())) {
	        JOptionPane.showMessageDialog(this, "Nome de usuário já registrado. Escolha outro nome de usuário.",
	                "Cadastro", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    usuario.setNomeCompleto(tfNome.getText() + " " + tfSobrenome.getText());

	    try {
	        LocalDate dataNascimento = LocalDate.parse(formattedTextFieldDataNascimento.getText(),
	                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	        Date dataNascimentoSQL = Date.valueOf(dataNascimento);
	        usuario.setDataNascimento(dataNascimentoSQL);
	    } catch (DateTimeParseException e) {
	        JOptionPane.showMessageDialog(this, "Data de nascimento inválida. Use o formato dd/MM/yyyy.");
	        return;
	    }
	    usuario.setNomeUsuario(tfNomeUsuario.getText());
	    usuario.setSenha(new String(pfSenha.getPassword()));
	    usuario.setEmail(tfEmail.getText());

	    String fotoBase64 = tfUrlFoto.getText();
	    if (!fotoBase64.isEmpty()) {
	        byte[] fotoBytes = Base64.getDecoder().decode(fotoBase64);
	        usuario.setFotoPessoal(fotoBytes);
	    }

	    if (rdbtnMasculino.isSelected()) {
	        usuario.setGenero("Masculino");
	    } else if (rdbtnFeminino.isSelected()) {
	        usuario.setGenero("Feminino");
	    } else {
	        usuario.setGenero("Não informar");
	    }

	    if (usuario.getId() == 0) {
	        usuarioDAO.createUser(usuario);
	    } else {
	        usuarioDAO.updateUser(usuario);
	    }

	    JOptionPane.showMessageDialog(this, "Dados do usuário salvos com sucesso!");
	    dispose();
	}
	
	public class Base64Utils {

	    private Base64Utils() {
	    }

	    public String encode(byte[] data) {
	        return Base64.getEncoder().encodeToString(data);
	    }

	    public byte[] decode(String base64String) {
	        return Base64.getDecoder().decode(base64String);
	    }
	}

	private boolean validarEmail(String email) {
		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
