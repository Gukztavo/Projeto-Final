package com.projetofinal.thread;

import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.projetofinal.dao.CompromissoDAO;
import com.projetofinal.entities.Compromisso;
import com.projetofinal.view.HomeView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificacaoCompromisso implements Runnable {

    private int userId;
    private CompromissoDAO compromissoDAO;
    private HomeView homeView; // Add a reference to the HomeView instance

    private static final Logger logger = Logger.getLogger(NotificacaoCompromisso.class.getName());

    public NotificacaoCompromisso(int userId, CompromissoDAO compromissoDAO) {
        this.userId = userId;
        this.compromissoDAO = compromissoDAO;
    }

    @Override
    public void run() {
        
    }
}
