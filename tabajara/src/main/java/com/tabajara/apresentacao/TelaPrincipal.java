package com.tabajara.apresentacao;

import javax.swing.*;

import com.tabajara.negocio.Anotacao;
import com.tabajara.persistencia.AnotacaoDAO;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaPrincipal {
    private static JList<Anotacao> anotacaoList;
    private static DefaultListModel<Anotacao> anotacaoListModel;
    private static AnotacaoDAO anotacaoDAO = new AnotacaoDAO();

    public void run() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Google Tabajara");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            anotacaoListModel = new DefaultListModel<>();
            anotacaoList = new JList<>(anotacaoListModel);
            anotacaoList.setCellRenderer(new AnotacaoItem());
            loadTasks();

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());

            JButton botaoAdicionar = new JButton("Adicionar");
            JButton botaoRemover = new JButton("Remover");
            JButton botaoEditar = new JButton("Editar");
            JButton botaoDuplicar = new JButton("Duplicar");

            buttonPanel.add(botaoAdicionar);
            buttonPanel.add(botaoRemover);
            buttonPanel.add(botaoEditar);
            buttonPanel.add(botaoDuplicar);

            botaoAdicionar.addActionListener(e -> {
                try {
                    adicionarAnotacao();
                } catch (SQLException e1) {
                    MensagemErro.showException(e1);
                }
            });
            botaoRemover.addActionListener(e -> {
                try {
                    removerAnotacao();
                } catch (SQLException e1) {
                    MensagemErro.showException(e1);
                }
            });
            botaoEditar.addActionListener(e -> {
                try {
                    editarAnotacao();
                } catch (SQLException e1) {
                    MensagemErro.showException(e1);
                }
            });
            botaoDuplicar.addActionListener(e -> {
                try {
                    duplicarAnotacao();
                } catch (SQLException e1) {
                    MensagemErro.showException(e1);
                }
            });

            frame.add(new JScrollPane(anotacaoList), BorderLayout.CENTER);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }

    private static void loadTasks() {
        try {
            anotacaoListModel.clear();
            List<Anotacao> anotacoes = anotacaoDAO.listar();
            for (Anotacao anotacao : anotacoes) {
                anotacaoListModel.addElement(anotacao);
            }
            anotacaoList.setSelectedIndex(0);
        } catch (SQLException e) {
            MensagemErro.showException(e);
        }
    }

    private static void adicionarAnotacao() throws SQLException {
        AnotacaoForm dialog = new AnotacaoForm(null);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            loadTasks();
        }
    }

    private static void removerAnotacao() throws SQLException {
        Anotacao anotacaoSelecionada = anotacaoList.getSelectedValue();
        if (anotacaoSelecionada != null) {
            anotacaoDAO.delete(anotacaoSelecionada.getId());
            loadTasks();
        }
    }

    private static void editarAnotacao() throws SQLException {
        Anotacao anotacaoSelecionada = anotacaoList.getSelectedValue();
        if (anotacaoSelecionada != null) {
            AnotacaoForm dialog = new AnotacaoForm(anotacaoSelecionada);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                loadTasks();
            }
        }

    }

    private static void duplicarAnotacao() throws SQLException {
        Anotacao anotacaoSelecionada = anotacaoList.getSelectedValue();
        if (anotacaoSelecionada != null) {
            anotacaoDAO.duplicar(anotacaoSelecionada.getId());
            
            loadTasks();
        }
    }
}