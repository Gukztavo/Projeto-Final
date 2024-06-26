package com.projetofinal.entities;

import java.sql.Date;

public class Usuario {
	private int id;
	private String nomeCompleto;
	private Date dataNascimento;
	private String genero;
	private byte[] fotoPessoal;
	private String email;
	private String nomeUsuario;
	private String senha;

	public Usuario(int id, String nomeCompleto, Date dataNascimento, String genero, byte[] fotoPessoal, String email,
			String nomeUsuario, String senha) {
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.dataNascimento = dataNascimento;
		this.genero = genero;
		this.fotoPessoal = fotoPessoal;
		this.email = email;
		this.nomeUsuario = nomeUsuario;
		this.senha = senha;
	}

	public Usuario() {

	}

	public int getId() {
		return id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public byte[] getFotoPessoal() {
		return fotoPessoal;
	}

	public void setFotoPessoal(byte[] string) {
		this.fotoPessoal = string;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
