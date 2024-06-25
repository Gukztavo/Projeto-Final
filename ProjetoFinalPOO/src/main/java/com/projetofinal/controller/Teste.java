package com.projetofinal.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.projetofinal.dao.CompromissoDAO;
import com.projetofinal.entities.Compromisso;

public class Teste {

	public static void main(String[] args) {
        // Setup database connection
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetofinal", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        CompromissoDAO compromissoDAO = new CompromissoDAO(connection);
        CompromissoController compromissoController = new CompromissoController(compromissoDAO, 1);

        // Use the existing agenda_id = 7
        int agendaId = 7;
        LocalDateTime now = LocalDateTime.now();

        // Test createCompromisso
//        compromissoController.createCompromisso(
//                "Reunião de Teste",
//                "Discussão sobre o projeto final",
//                now,
//                now.plusHours(1),
//                "Sala de Reuniões",
//                agendaId,
//                Arrays.asList(1, 2),
//                now.minusHours(1)
//        );

        // Test getCompromissoById
        Compromisso compromisso = compromissoController.getCompromissoById(3);
//        if (compromisso != null) {
//            System.out.println("Compromisso obtido: " + compromisso.getTitulo());
//        } else {
//            System.out.println("Erro: Compromisso não encontrado.");
//        }

        // Test updateCompromisso
//        compromissoController.updateCompromisso(
//                3,
//                "Reunião de Teste Atualizada",
//                "Discussão atualizada sobre o projeto final",
//                now,
//                now.plusHours(2),
//                "Sala de Reuniões Atualizada",
//                agendaId,
//                Arrays.asList(1, 5, 6),
//                now.minusHours(2)
//        );

        // Test getCompromissoById again
//        compromisso = compromissoController.getCompromissoById(3);
//        if (compromisso != null) {
//            System.out.println("Compromisso atualizado: " + compromisso.getTitulo());
//        } else {
//            System.out.println("Erro: Compromisso não encontrado após atualização.");
//        }

        // Test getAllCompromissos
//        List<Compromisso> compromissos = compromissoController.getAllCompromissos();
//        System.out.println("Todos os compromissos:");
//        for (Compromisso c : compromissos) {
//            System.out.println(c.getTitulo());
//        }

        // Test deleteCompromisso
//        compromissoController.deleteCompromisso(3);
//        compromisso = compromissoController.getCompromissoById(1);
//        if (compromisso == null) {
//            System.out.println("Compromisso deletado com sucesso.");
//        } else {
//            System.out.println("Erro ao deletar compromisso.");
//        }

        // Close database connection
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
