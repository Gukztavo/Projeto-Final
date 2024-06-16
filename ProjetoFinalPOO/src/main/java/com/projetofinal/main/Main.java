package com.projetofinal.main;

import com.projetofinal.controller.UsuarioController;
import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.view.LoginView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Configuração da conexão com o banco de dados
        String jdbcUrl = "jdbc:mysql://localhost:3306/projetoFinal";
        String username = "root";
        String password = "umasenhaponto";
        
        // Conexão com o banco de dados
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Instanciação do DAO
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            
            // Instanciação do Controller com o DAO
            UsuarioController usuarioController = new UsuarioController(usuarioDAO);
            
            // Instanciação da view de login
            LoginView loginView = new LoginView(usuarioController);
            loginView.setVisible(true);
            
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
