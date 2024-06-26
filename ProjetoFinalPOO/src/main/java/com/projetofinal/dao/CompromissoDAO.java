package com.projetofinal.dao;

import com.projetofinal.entities.Compromisso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompromissoDAO {
    private Connection connection;

    public CompromissoDAO(Connection connection) {
        this.connection = connection;
    }
    
    public CompromissoDAO() {
        
    }

    public Connection getConnection() {
        return connection;
    }

    public void createCompromisso(Compromisso compromisso) {
        if (existsCompromissoById(compromisso.getId())) {
            System.out.println("Compromisso com ID " + compromisso.getId() + " já existe. Não é possível criar novamente.");
            return;
        }

        String sql = "INSERT INTO compromisso (titulo, descricao, data_hora_inicio, data_hora_fim, local, agenda_id, data_hora_notificacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, compromisso.getTitulo());
            stmt.setString(2, compromisso.getDescricao());
            stmt.setTimestamp(3, Timestamp.valueOf(compromisso.getDataHoraInicio()));
            stmt.setTimestamp(4, Timestamp.valueOf(compromisso.getDataHoraTermino()));
            stmt.setString(5, compromisso.getLocal());
            stmt.setInt(6, compromisso.getAgendaId());
            stmt.setTimestamp(7, Timestamp.valueOf(compromisso.getDataHoraNotificacao()));
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                compromisso.setId(generatedKeys.getInt(1));
                insertCompromissoConvidados(compromisso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsCompromissoById(int id) {
        String sql = "SELECT COUNT(*) FROM compromisso WHERE compromisso_id = ?";
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
        String sql = "SELECT * FROM compromisso WHERE compromisso_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                List<Integer> usuariosConvidados = getUsuariosConvidadosByCompromissoId(id);

                return new Compromisso(
                    rs.getInt("compromisso_id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getTimestamp("data_hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("data_hora_fim").toLocalDateTime(),
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
    
    public List<Compromisso> getCompromissosByUsuarioId(int idUsuario) {
        List<Compromisso> compromissos = new ArrayList<>();
        String sql = "SELECT c.* FROM compromisso c " +
                     "JOIN compromisso_convidado cc ON c.compromisso_id = cc.compromisso_id " +
                     "WHERE cc.user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                List<Integer> usuariosConvidados = getUsuariosConvidadosByCompromissoId(rs.getInt("compromisso_id"));

                Compromisso compromisso = new Compromisso(
                    rs.getInt("compromisso_id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getTimestamp("data_hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("data_hora_fim").toLocalDateTime(),
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

    public void updateCompromisso(Compromisso compromisso) {
        String sql = "UPDATE compromisso SET titulo = ?, descricao = ?, data_hora_inicio = ?, data_hora_fim = ?, local = ?, agenda_id = ?, data_hora_notificacao = ? WHERE compromisso_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, compromisso.getTitulo());
            stmt.setString(2, compromisso.getDescricao());
            stmt.setTimestamp(3, Timestamp.valueOf(compromisso.getDataHoraInicio()));
            stmt.setTimestamp(4, Timestamp.valueOf(compromisso.getDataHoraTermino()));
            stmt.setString(5, compromisso.getLocal());
            stmt.setInt(6, compromisso.getAgendaId());
            stmt.setTimestamp(7, Timestamp.valueOf(compromisso.getDataHoraNotificacao()));
            stmt.setInt(8, compromisso.getId());
            stmt.executeUpdate();

            deleteCompromissoConvidados(compromisso.getId());
            insertCompromissoConvidados(compromisso);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCompromisso(int id) {
        String sql = "DELETE FROM compromisso WHERE compromisso_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            deleteCompromissoConvidados(id);
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
                int compromissoId = rs.getInt("compromisso_id");
                List<Integer> usuariosConvidados = getUsuariosConvidadosByCompromissoId(compromissoId);

                Compromisso compromisso = new Compromisso(
                    rs.getInt("compromisso_id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getTimestamp("data_hora_inicio").toLocalDateTime(),
                    rs.getTimestamp("data_hora_fim").toLocalDateTime(),
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

    private void insertCompromissoConvidados(Compromisso compromisso) throws SQLException {
        String sql = "INSERT INTO compromisso_convidado (compromisso_id, user_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Integer userId : compromisso.getUsuariosConvidados()) {
                stmt.setInt(1, compromisso.getId());
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }
        }
    }

    private void deleteCompromissoConvidados(int compromissoId) throws SQLException {
        String sql = "DELETE FROM compromisso_convidado WHERE compromisso_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, compromissoId);
            stmt.executeUpdate();
        }
    }

    private List<Integer> getUsuariosConvidadosByCompromissoId(int compromissoId) {
        List<Integer> usuariosConvidados = new ArrayList<>();
        String sql = "SELECT user_id FROM compromisso_convidado WHERE compromisso_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, compromissoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usuariosConvidados.add(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuariosConvidados;
    }
}
