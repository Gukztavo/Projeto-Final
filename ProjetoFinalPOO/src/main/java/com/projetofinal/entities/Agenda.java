package com.projetofinal.entities;

public class Agenda {
    private int id;
    private String nome;
    private String descricao;
    private int userId;

    // Construtor
    
    public Agenda(int id, String nome, String descricao, int userId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.userId = userId;
    }
    public Agenda() {
      
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
