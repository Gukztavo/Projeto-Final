package com.projetofinal.thread;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.JOptionPane;

import com.projetofinal.dao.CompromissoDAO;
import com.projetofinal.entities.Compromisso;

public class NotificacaoCompromisso implements Runnable {

    private int userId;
    private CompromissoDAO compromissoDAO;

    public NotificacaoCompromisso(int userId, CompromissoDAO compromissoDAO) {
        this.userId = userId;
        this.compromissoDAO = compromissoDAO;
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<Compromisso> compromissos = compromissoDAO.getUpcomingCompromissosByUserId(userId);
                LocalDateTime now = LocalDateTime.now();

                for (Compromisso compromisso : compromissos) {
                    if (compromisso.getDataHoraNotificacao().isBefore(now.plusMinutes(15)) && compromisso.getDataHoraNotificacao().isAfter(now)) {
                        JOptionPane.showMessageDialog(null, "Você tem um compromisso próximo: " + compromisso.getTitulo());
                    }
                }

                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
