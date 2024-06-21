package com.projetofinal.dao;

import com.projetofinal.entities.Agenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO {

    private Connection connection;
    

    public AgendaDAO(Connection connection) {
        this.connection = connection;
    }

    
    public void create(Agenda agenda) {
        String sql = "INSERT INTO Agenda (nome, descricao, user_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agenda.getNome());
            stmt.setString(2, agenda.getDescricao());
            stmt.setInt(3, agenda.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public Agenda read(int agendaId) {
        String sql = "SELECT * FROM Agenda WHERE agenda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, agendaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Agenda(
                    rs.getInt("agenda_id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getInt("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public void update(Agenda agenda) {
        String sql = "UPDATE Agenda SET nome = ?, descricao = ? WHERE agenda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agenda.getNome());
            stmt.setString(2, agenda.getDescricao());
            stmt.setInt(3, agenda.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int agendaId) {
        String sql = "DELETE FROM Agenda WHERE agenda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, agendaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Agenda> findByUserId(int userId) {
        List<Agenda> agendas = new ArrayList<>();
        String sql = "SELECT * FROM Agenda WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                agendas.add(new Agenda(
                    rs.getInt("agenda_id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getInt("user_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendas;
    }
}
