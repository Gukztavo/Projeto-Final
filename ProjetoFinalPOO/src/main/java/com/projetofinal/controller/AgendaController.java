package com.projetofinal.controller;

import com.projetofinal.dao.AgendaDAO;
import com.projetofinal.entities.Agenda;
import com.projetofinal.entities.Usuario;

import java.util.List;

public class AgendaController {
    private AgendaDAO agendaDAO;
    private Usuario usuario;
    private int userId;

    public AgendaController(AgendaDAO agendaDAO, int userId) {
        this.agendaDAO = agendaDAO;
        this.usuario = usuario;
        this.userId = userId;
    }

    public void createAgenda(String nome, String descricao) {
        Agenda agenda = new Agenda(0, nome, descricao, userId);
        agendaDAO.create(agenda);
    }

    public Agenda getagendaByAgenda(String nomeAgenda) {
        Agenda agenda = agendaDAO.getAgendaByAgenda(nomeAgenda);
        if (agenda == null) {
            System.out.println("Erro: nao foi encontrada agenda com esse nome");
        }
        return agenda;
    }
    public Agenda getAgenda(int agendaId) {
        return agendaDAO.read(agendaId);
    }

    public void updateAgenda(int agendaId, String nome, String descricao) {
        Agenda agenda = new Agenda(agendaId, nome, descricao, userId);
        agendaDAO.update(agenda);
    }

    public void deleteAgenda(int agendaId) {
        agendaDAO.delete(agendaId);
    }
    
    public Agenda readAgenda(int agendaId) {
        return agendaDAO.read(agendaId);
    }

    public List<Agenda> getAllAgendas(int i) {
        return agendaDAO.findByUserId(userId);
    }
}
