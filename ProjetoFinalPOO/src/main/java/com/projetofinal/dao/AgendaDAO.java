package com.projetofinal.dao;

import com.projetofinal.entities.Agenda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO {
    private Connection connection;

    public AgendaDAO(Connection connection) {
        this.connection = connection;
    }

    public void createAgenda(Agenda agenda) {
    	
    	
        String sql = "INSERT INTO agenda (nome, descricao) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agenda.getNome());
            stmt.setString(2, agenda.getDescricao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Agenda getAgendaById(int id) {
        String sql = "SELECT * FROM agenda WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Agenda(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAgenda(Agenda agenda) {
        String sql = "UPDATE agenda SET nome = ?, descricao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agenda.getNome());
            stmt.setString(2, agenda.getDescricao());
            stmt.setInt(3, agenda.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAgenda(int id) {
        String sql = "DELETE FROM agenda WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Agenda> getAllAgendas() {
        List<Agenda> agendas = new ArrayList<>();
        String sql = "SELECT * FROM agenda";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Agenda agenda = new Agenda(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao")
                );
                agendas.add(agenda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendas;
    }
}
