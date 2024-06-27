// ConviteController.java
package com.projetofinal.controller;

import com.projetofinal.dao.ConviteDAO;
import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.entities.Convite;
import com.projetofinal.entities.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

public class ConviteController {
    private ConviteDAO conviteDAO;
    private UsuarioDAO usuarioDAO;

    public ConviteController(Connection connection) {
        this.conviteDAO = new ConviteDAO(connection);
        this.usuarioDAO = new UsuarioDAO(connection);
    }

    public List<Convite> listarConvites(int idUsuario) throws SQLException {
        return conviteDAO.getConvitesPorUsuario(idUsuario);
    }

    public void responderConvite(int idConvite, String resposta) throws SQLException {
        conviteDAO.atualizarStatusConvite(idConvite, resposta);
    }
    
    public void enviarConvite(String nomeUsuarioConvidado, int idCompromisso, int idUsuarioLogado) throws SQLException {
        Usuario usuarioConvidado = usuarioDAO.getUserByUsername(nomeUsuarioConvidado);
        if (usuarioConvidado!= null) {
            Convite convite = new Convite();
            convite.setIdUsuarioConvidado(usuarioConvidado.getId());
            convite.setIdCompromisso(idCompromisso);
            convite.setStatus("Pendente");
            convite.setDataConvite(new Date(System.currentTimeMillis()));
            try {
                conviteDAO.enviarConvite(convite);
                JOptionPane.showMessageDialog(null, "Convite enviado com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao enviar convite: " + e.getMessage());
                System.out.println(e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
        }
    }
}
