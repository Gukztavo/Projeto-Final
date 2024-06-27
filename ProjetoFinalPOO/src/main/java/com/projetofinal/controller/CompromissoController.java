package com.projetofinal.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.projetofinal.dao.CompromissoDAO;
import com.projetofinal.entities.Compromisso;

public class CompromissoController {

    private CompromissoDAO compromissoDAO;
    private int userId;

    public CompromissoController(CompromissoDAO compromissoDAO, int userId) {
        this.compromissoDAO = compromissoDAO;
        this.userId = userId;
    }

    public void createCompromisso(String titulo, String descricao, LocalDateTime dataHoraInicio,
                                  LocalDateTime dataHoraTermino, String local, int agendaId,
                                  List<Integer> usuariosConvidados, LocalDateTime dataHoraNotificacao) {
        Compromisso compromisso = new Compromisso(0, titulo, descricao, dataHoraInicio, dataHoraTermino, local, agendaId, usuariosConvidados, dataHoraNotificacao);
        compromissoDAO.createCompromisso(compromisso);
    }

    public Compromisso getCompromissoById(int compromissoId) {
        Compromisso compromisso = compromissoDAO.getCompromissoById(compromissoId);
        if (compromisso == null) {
            System.out.println("Erro: não foi encontrado compromisso com esse ID");
        }
        return compromisso;
    }

    public void updateCompromisso(int compromissoId, String titulo, String descricao, LocalDateTime dataHoraInicio,
                                  LocalDateTime dataHoraTermino, String local, int agendaId,
                                  List<Integer> usuariosConvidados, LocalDateTime dataHoraNotificacao) {
        Compromisso compromisso = new Compromisso(compromissoId, titulo, descricao, dataHoraInicio, dataHoraTermino, local, agendaId, usuariosConvidados, dataHoraNotificacao);
        compromissoDAO.updateCompromisso(compromisso);
    }

    public void deleteCompromisso(int compromissoId) {
        compromissoDAO.deleteCompromisso(compromissoId);
    }

    public List<Compromisso> getAllCompromissos() {
        return compromissoDAO.getAllCompromissos();
    }
}