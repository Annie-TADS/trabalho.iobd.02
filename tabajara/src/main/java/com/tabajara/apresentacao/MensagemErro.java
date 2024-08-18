package com.tabajara.apresentacao;

import javax.swing.*;
import java.awt.*;

public class MensagemErro extends JDialog {

    public MensagemErro(JDialog pai, Exception e) {
        super(pai, "Exception: " + e.getMessage(), true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(getStackTraceAsString(e));
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    public MensagemErro(JDialog pai, String mensagemErro) {
        super(pai, "Erro", true);
        setSize(600, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(mensagemErro);
        textArea.setFont(new Font("Arial", Font.BOLD, 24));
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    private String getStackTraceAsString(Exception e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void showException(Exception exception) {
        showException(null, exception);
    }

    public static void showException(JDialog pai, Exception exception) {
        SwingUtilities.invokeLater(() -> {
            MensagemErro dialog = new MensagemErro(pai, exception);
            dialog.setVisible(true);
            dialog.toFront();
        });
    }

    public static void showMensagem(JDialog pai, String mensagemErro) {
        SwingUtilities.invokeLater(() -> {
            MensagemErro dialog = new MensagemErro(pai, mensagemErro);
            dialog.setVisible(true);
            dialog.toFront();
        });
    }
}