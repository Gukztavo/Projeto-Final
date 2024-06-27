package com.projetofinal.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Compromisso {

	private int id;
	private String titulo;
	private String descricao;
	private LocalDateTime dataHoraInicio;
	private LocalDateTime dataHoraTermino;
	private String local;
	private int agendaId;
	private List<Integer> usuariosConvidados;
	private LocalDateTime dataHoraNotificacao;

	public Compromisso(int id, String titulo, String descricao, LocalDateTime dataHoraInicio,
			LocalDateTime dataHoraTermino, String local, int agendaId, List<Integer> usuariosConvidados,
			LocalDateTime dataHoraNotificacao) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraTermino = dataHoraTermino;
		this.local = local;
		this.agendaId = agendaId;
		this.usuariosConvidados = usuariosConvidados;
		this.dataHoraNotificacao = dataHoraNotificacao;
	}
	
	public Compromisso(){}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public LocalDateTime getDataHoraTermino() {
		return dataHoraTermino;
	}

	public void setDataHoraTermino(LocalDateTime dataHoraTermino) {
		this.dataHoraTermino = dataHoraTermino;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public int getAgendaId() {
		return agendaId;
	}

	public void setAgendaId(int agendaId) {
		this.agendaId = agendaId;
	}

	public List<Integer> getUsuariosConvidados() {
		return usuariosConvidados;
	}

	public void setUsuariosConvidados(List<Integer> usuariosConvidados) {
		this.usuariosConvidados = usuariosConvidados;
	}

	public LocalDateTime getDataHoraNotificacao() {
		return dataHoraNotificacao;
	}

	public void setDataHoraNotificacao(LocalDateTime dataHoraNotificacao) {
		this.dataHoraNotificacao = dataHoraNotificacao;
	}

	@Override
	public String toString() {
		return "Compromisso [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", dataHoraInicio="
				+ dataHoraInicio + ", dataHoraTermino=" + dataHoraTermino + ", local=" + local + ", agendaId="
				+ agendaId + ", usuariosConvidados=" + usuariosConvidados + ", dataHoraNotificacao="
				+ dataHoraNotificacao + "]";
	}
	
	
}
