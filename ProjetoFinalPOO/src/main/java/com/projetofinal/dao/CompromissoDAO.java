package com.projetofinal.dao;

import com.projetofinal.entities.Compromisso;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CompromissoDAO {
    private Connection connection;

    public CompromissoDAO(Connection connection) {
        this.connection = connection;
    }

    public void createCompromisso(Compromisso compromisso) {
    	
    	 if (existsCompromissoById(compromisso.getId())) {
             System.out.println("Compromisso com ID " + compromisso.getId() + " já existe. Não é possível criar novamente.");
             return;
         }
    	
        String sql = "INSERT INTO compromisso (titulo, descricao, data_hora_inicio, data_hora_termino, local, agenda_id, usuarios_convidados, data_hora_notificacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, compromisso.getTitulo());
            stmt.setString(2, compromisso.getDescricao());
            stmt.setTimestamp(3, Timestamp.valueOf(compromisso.getDataHoraInicio()));
            stmt.setTimestamp(4, Timestamp.valueOf(compromisso.getDataHoraTermino()));
            stmt.setString(5, compromisso.getLocal());
            stmt.setInt(6, compromisso.getAgendaId());
            stmt.setString(7, compromisso.getUsuariosConvidados().stream().map(Object::toString).collect(Collectors.joining(",")));
            stmt.setTimestamp(8, Timestamp.valueOf(compromisso.getDataHoraNotificacao()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean existsCompromissoById(int id) {
        String sql = "SELECT COUNT(*) FROM compromisso WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Compromisso getCompromissoById(int id) {
        String sql = "SELECT * FROM compromisso WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                List<Integer> usuariosConvidados = Arrays.stream(rs.getString("usuarios_convidados").split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                
                return new Compromisso(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getTimestamp("data_hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("data_hora_termino").toLocalDateTime(),
                    rs.getString("local"),
                    rs.getInt("agenda_id"),
                    usuariosConvidados,
                    rs.getTimestamp("data_hora_notificacao").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCompromisso(Compromisso compromisso) {
        String sql = "UPDATE compromisso SET titulo = ?, descricao = ?, data_hora_inicio = ?, data_hora_termino = ?, local = ?, agenda_id = ?, usuarios_convidados = ?, data_hora_notificacao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, compromisso.getTitulo());
            stmt.setString(2, compromisso.getDescricao());
            stmt.setTimestamp(3, Timestamp.valueOf(compromisso.getDataHoraInicio()));
            stmt.setTimestamp(4, Timestamp.valueOf(compromisso.getDataHoraTermino()));
            stmt.setString(5, compromisso.getLocal());
            stmt.setInt(6, compromisso.getAgendaId());
            stmt.setString(7, compromisso.getUsuariosConvidados().stream().map(Object::toString).collect(Collectors.joining(",")));
            stmt.setTimestamp(8, Timestamp.valueOf(compromisso.getDataHoraNotificacao()));
            stmt.setInt(9, compromisso.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCompromisso(int id) {
        String sql = "DELETE FROM compromisso WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Compromisso> getAllCompromissos() {
        List<Compromisso> compromissos = new ArrayList<>();
        String sql = "SELECT * FROM compromisso";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                List<Integer> usuariosConvidados = Arrays.stream(rs.getString("usuarios_convidados").split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                
                Compromisso compromisso = new Compromisso(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getTimestamp("data_hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("data_hora_termino").toLocalDateTime(),
                    rs.getString("local"),
                    rs.getInt("agenda_id"),
                    usuariosConvidados,
                    rs.getTimestamp("data_hora_notificacao").toLocalDateTime()
                );
                compromissos.add(compromisso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compromissos;
    }
}
