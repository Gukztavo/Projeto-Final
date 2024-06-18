package com.projetofinal.controller;

import com.projetofinal.dao.UsuarioDAO;
import com.projetofinal.entities.Usuario;

import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    public UsuarioController() {
    }

    public void createUser(Usuario usuario) {
    	Usuario usuairo = usuarioDAO.getUserByUsername(usuario.getNomeUsuario());
        if (usuairo != null) {
            throw new RuntimeException("Usuário já existe!");
        }
        usuarioDAO.createUser(usuario);
    }

    public Usuario getUserByUsername(String nomeUsuario) {
        Usuario usuairo = usuarioDAO.getUserByUsername(nomeUsuario);
        if (usuairo == null) {
            System.out.println("Erro: Não foi encontrado usuário com o nome de usuário fornecido.");
        }
        return usuairo;
    }

    public void updateUser(Usuario usuario) {
        Usuario usuairo = usuarioDAO.getUserByUsername(usuario.getNomeUsuario());
        if (usuairo == null) {
            System.out.println("Erro: Não foi encontrado usuário com o nome de usuário fornecido para atualização.");
            return; 
        }
        
        usuarioDAO.updateUser(usuario);
    }

    public void deleteUser(String nomeUsuario) {
        Usuario usuairo = usuarioDAO.getUserByUsername(nomeUsuario);
        if (usuairo == null) {
            System.out.println("Erro: Não foi encontrado usuário com o nome de usuário fornecido para exclusão.");
            return;
        }
        
        usuarioDAO.deleteUser(nomeUsuario);
    }

    public List<Usuario> getAllUsers() {
        return usuarioDAO.getAllUsers();
    }
}
