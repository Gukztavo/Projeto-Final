package com.projetofinal.entities;

public class Convite {

	private int id;
	private int compromissoId;
	private int usuarioId;
	private boolean aceito;

	public Convite(int id, int compromissoId, int usuarioId, boolean aceito) {
        this.id = id;
        this.compromissoId = compromissoId;
        this.usuarioId = usuarioId;
        this.aceito = aceito;
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompromissoId() {
		return compromissoId;
	}

	public void setCompromissoId(int compromissoId) {
		this.compromissoId = compromissoId;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public boolean isAceito() {
		return aceito;
	}

	public void setAceito(boolean aceito) {
		this.aceito = aceito;
	}
}
