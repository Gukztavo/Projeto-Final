package com.projetofinal.entities;

import java.sql.Date;

public class Convite {

	private int id;
	private int idUsuarioConvidado;
	private int idCompromisso;
	private String status;
	private Date dataConvite;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUsuarioConvidado() {
		return idUsuarioConvidado;
	}

	public void setIdUsuarioConvidado(int idUsuarioConvidado) {
		this.idUsuarioConvidado = idUsuarioConvidado;
	}

	public int getIdCompromisso() {
		return idCompromisso;
	}

	public void setIdCompromisso(int idCompromisso) {
		this.idCompromisso = idCompromisso;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDataConvite() {
		return dataConvite;
	}

	public void setDataConvite(Date dataConvite) {
		this.dataConvite = dataConvite;
	}

}
