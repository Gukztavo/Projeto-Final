package com.projetofinal.main;

import com.projetofinal.controller.UsuarioController;
import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.view.LoginView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetoFinal", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        UsuarioController usuarioController = new UsuarioController();
        UsuarioDAO usuarioDAO = new UsuarioDAO(connection);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView(usuarioController, usuarioDAO).setVisible(true);
            }
        });
    }
}
