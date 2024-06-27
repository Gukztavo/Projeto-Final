package com.projetofinal.dao;

import com.projetofinal.entities.Usuario;

import com.projetofinal.dao.BancoDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void createUser(Usuario usuario) {
        if (getUserByUsername(usuario.getNomeUsuario()) != null) {
            System.out.println("Usuário já existe. Não é possível criar novamente.");
            return; 
        }
        
        String sql = "INSERT INTO usuario (nome_completo, data_nascimento, genero, foto, email, nome_usuario, senha) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNomeCompleto());
            stmt.setDate(2, usuario.getDataNascimento());
            stmt.setString(3, usuario.getGenero());
            stmt.setBytes(4, usuario.getFotoPessoal());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getNomeUsuario());
            stmt.setString(7, usuario.getSenha());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }

    public Usuario getUserByUsername(String nomeUsuario) {
        String sql = "SELECT * FROM usuario WHERE nome_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("user_id"),
                    rs.getString("nome_completo"),
                    rs.getDate("data_nascimento"),
                    rs.getString("genero"),
                    rs.getBytes("foto"),
                    rs.getString("email"),
                    rs.getString("nome_usuario"),
                    rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Usuario getUserById(int id) {
        String sql = "SELECT * FROM usuario WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("user_id"),
                    rs.getString("nome_completo"),
                    rs.getDate("data_nascimento"),
                    rs.getString("genero"),
                    rs.getBytes("foto"),
                    rs.getString("email"),
                    rs.getString("nome_usuario"),
                    rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(Usuario usuario) {
    	String sql = "UPDATE usuario SET nome_completo = ?, data_nascimento = ?, genero = ?, email = ?, nome_usuario = ?, senha = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNomeCompleto());
            stmt.setDate(2, usuario.getDataNascimento());
            stmt.setString(3, usuario.getGenero());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getNomeUsuario());
            stmt.setString(6, usuario.getSenha());
            stmt.setInt(7, usuario.getId()); // Use the user's ID to identify the record
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String nomeUsuario) {
        String sql = "DELETE FROM usuario WHERE nome_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> getAllUsers() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("user_id"),
                    rs.getString("nome_completo"),
                    rs.getDate("data_nascimento"),
                    rs.getString("genero"),
                    rs.getBytes("foto"),
                    rs.getString("email"),
                    rs.getString("nome_usuario"),
                    rs.getString("senha")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    
    public boolean validarCredenciais(String nomeUsuario, String senha) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE nome_usuario = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            stmt.setString(2, senha);
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

	public Connection getConnection() {
		
		return this.connection;
	}
	
	public byte[] getUserImage(String username) {
	    String sql = "SELECT foto FROM usuario WHERE nome_usuario = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, username);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBytes("foto");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
    
}