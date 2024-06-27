package com.projetofinal.controller;

import com.projetofinal.dao.CompromissoDAO;
import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.entities.Usuario;
import com.projetofinal.thread.NotificacaoCompromisso;

public class LoginController {
    private UsuarioDAO usuarioDAO;
    private CompromissoDAO compromissoDAO;

    public void login(String username, String password) {
        if (usuarioDAO.validarCredenciais(username, password)) {
            Usuario user = usuarioDAO.getUserByUsername(username);
            if (user != null) {
            	NotificacaoCompromisso checker = new NotificacaoCompromisso(user.getId(), compromissoDAO);
                Thread checkerThread = new Thread(checker);
                checkerThread.start();
            }
        } else {
            
        }
    }
}
