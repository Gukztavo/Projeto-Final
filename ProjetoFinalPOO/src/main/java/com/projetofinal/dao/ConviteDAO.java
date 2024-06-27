package com.projetofinal.dao;

import com.projetofinal.entities.Convite;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConviteDAO {
    private Connection connection;

    public ConviteDAO(Connection connection) {
        this.connection = connection;
    }

    public void enviarConvite(Convite convite) throws SQLException {
        String sql = "INSERT INTO convite (user_id, compromisso_id, status, data_convite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, convite.getIdUsuarioConvidado());
            stmt.setInt(2, convite.getIdCompromisso());
            stmt.setString(3, convite.getStatus());
            stmt.setDate(4, new java.sql.Date(convite.getDataConvite().getTime()));
            stmt.executeUpdate();
            System.out.println(stmt);
            System.out.println(sql);
        }
        
    }

    public List<Convite> listarConvites(int idUsuario) throws SQLException {
        List<Convite> convites = new ArrayList<>();
        String sql = "SELECT * FROM convite WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Convite convite = new Convite();
                convite.setId(rs.getInt("convite_id"));
                convite.setIdUsuarioConvidado(rs.getInt("user_id"));
                convite.setIdCompromisso(rs.getInt("compromisso_id"));
                convite.setStatus(rs.getString("status"));
                convite.setDataConvite(rs.getDate("data_convite"));
                convites.add(convite);
            }
        }
        return convites;
    }

    public void atualizarStatusConvite(int conviteId, String status) {
        String sql = "UPDATE convite SET status = ? WHERE convite_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, conviteId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void criarConvite(Convite convite) {
        String sql = "INSERT INTO convite (compromisso_id, user_id, data_convite, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, convite.getIdCompromisso());
            stmt.setInt(2, convite.getIdUsuarioConvidado());
            stmt.setDate(3, convite.getDataConvite());
            stmt.setString(4, convite.getStatus());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                convite.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Convite> getConvitesPorUsuario(int idUsuario) {
        List<Convite> convites = new ArrayList<>();
        String sql = "SELECT * FROM convite WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Convite convite = new Convite();
                convite.setId(rs.getInt("convite_id"));
                convite.setIdUsuarioConvidado(rs.getInt("user_id"));
                convite.setIdCompromisso(rs.getInt("compromisso_id"));
                convite.setStatus(rs.getString("status"));
                convite.setDataConvite(rs.getDate("data_convite"));
                convites.add(convite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return convites;
    }

    public void responderConvite(int id, String status) {
        String sql = "UPDATE convite SET status = ? WHERE convite_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
