package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import java.awt.Window.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNome;
	private JTextField tfSobrenome;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField tfEmail;
	private ButtonGroup btnGroupSexo;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFeminino;
	private JRadioButton rdbtnNaoInformar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastro frame = new TelaCadastro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	


	private String verificarSelecaoRadioButtonSexo() {
		if (this.rdbtnMasculino.isSelected()) {
			return this.rdbtnMasculino.getText();
		} else if (this.rdbtnFeminino.isSelected()) {
			return this.rdbtnFeminino.getText();
		} else {
			return this.rdbtnNaoInformar.getText();
		}
	}

	public TelaCadastro() {
		this.iniciarComponentes();
	}

	private void iniciarComponentes() {
		setTitle("Cadastro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 614, 440);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblCadastro = new JLabel("Cadastro ");
		lblCadastro.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCadastro.setBounds(263, 0, 85, 33);
		panel.add(lblCadastro);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 47, 46, 14);
		panel.add(lblNome);

		tfNome = new JTextField();
		tfNome.setBounds(83, 44, 182, 20);
		panel.add(tfNome);
		tfNome.setColumns(10);

		JLabel lblSobrenome = new JLabel("Sobrenome");
		lblSobrenome.setBounds(10, 72, 54, 14);
		panel.add(lblSobrenome);

		tfSobrenome = new JTextField();
		tfSobrenome.setBounds(83, 69, 182, 20);
		panel.add(tfSobrenome);
		tfSobrenome.setColumns(10);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(434, 44, 153, 20);
		panel.add(formattedTextField);

		JLabel lblDaTAnASC = new JLabel("Data de Nascimento");
		lblDaTAnASC.setBounds(308, 47, 116, 14);
		panel.add(lblDaTAnASC);

		JLabel lblNewLabel = new JLabel("Nome de Usuario");
		lblNewLabel.setBounds(308, 72, 105, 14);
		panel.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(434, 69, 154, 20);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(10, 97, 46, 14);
		panel.add(lblSenha);

		textField_1 = new JTextField();
		textField_1.setBounds(83, 94, 182, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(83, 131, 112, 79);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JRadioButton rdbtnMasculino = new JRadioButton("Masculino");
		rdbtnMasculino.setBounds(6, 7, 100, 23);
		panel_1.add(rdbtnMasculino);

		JRadioButton rdbtnFeminino = new JRadioButton("Feminino");
		rdbtnFeminino.setBounds(6, 33, 109, 23);
		panel_1.add(rdbtnFeminino);

		JRadioButton rdbtnNaoInformar = new JRadioButton("N\u00E3o Informar");
		rdbtnNaoInformar.setBounds(6, 59, 109, 23);
		panel_1.add(rdbtnNaoInformar);

		btnGroupSexo = new ButtonGroup();
		btnGroupSexo.add(rdbtnMasculino);
		btnGroupSexo.add(rdbtnFeminino);
		btnGroupSexo.add(rdbtnNaoInformar);
		
		JLabel lblSexo = new JLabel("Genero");
		lblSexo.setBounds(10, 163, 46, 14);
		panel.add(lblSexo);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(308, 97, 46, 14);
		panel.add(lblEmail);

		tfEmail = new JTextField();
		tfEmail.setBounds(434, 94, 153, 20);
		panel.add(tfEmail);
		tfEmail.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(434, 129, 153, 100);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Foto");
		lblNewLabel_1.setBounds(308, 163, 46, 14);
		panel.add(lblNewLabel_1);
		
		Button button = new Button("Cadastrar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBounds(58, 319, 70, 22);
		panel.add(button);
		
		Button button_1 = new Button("Atualizar");
		button_1.setBounds(239, 319, 70, 22);
		panel.add(button_1);
		
		Button button_2 = new Button("Excluir");
		button_2.setBounds(434, 332, 70, 22);
		panel.add(button_2);
	}
}
