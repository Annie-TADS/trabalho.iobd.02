package com.tabajara.apresentacao;

import javax.swing.*;

import com.tabajara.negocio.Anotacao;
import com.tabajara.persistencia.AnotacaoDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

class AnotacaoForm extends JDialog {
    private static AnotacaoDAO taskDAO = new AnotacaoDAO();
    private JTextField campoTitulo;
    private JTextField campoDescricao;
    private JButton campoCor;
    private JButton campoFoto;
    private boolean confirmed;
    private Anotacao anotacao;
    private Color selectedColor;
    private boolean novaAnotacao = false; 
    private String caminhoImagem = "";

    public AnotacaoForm(Anotacao anotacao) {
        if (anotacao == null) {
            novaAnotacao = true;
        }

        setTitle(novaAnotacao ? "Adicionar Anotação" : "Editar Anotação");
        setModal(true);
        setLayout(new GridLayout(5, 2));
        setSize(400, 300);
        setLocationRelativeTo(null);

        selectedColor = novaAnotacao ? Color.WHITE : Color.decode(anotacao.getCor());
        campoTitulo = new JTextField(novaAnotacao ? "" : anotacao.getTitulo());
        campoDescricao = new JTextField(novaAnotacao ? "" : anotacao.getDescricao());
        campoCor = new JButton("Choose Color");
        campoCor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(
                        null,
                        "Escolha uma cor",
                        selectedColor
                );

                if (newColor != null) {
                    selectedColor = newColor;
                }
            }
        });
        campoFoto = new JButton("Choose Photo");
        campoFoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    caminhoImagem = selectedFile.getAbsolutePath();
                }
            }
        });
        

        if (novaAnotacao) {
            this.anotacao = new Anotacao(); 
        } else {
            this.anotacao = anotacao;
        }

        add(new JLabel("Título:"));
        add(campoTitulo);
        add(new JLabel("Descrição:"));
        add(campoDescricao);
        add(new JLabel("Cor:"));
        add(campoCor);
        add(new JLabel("Imagem:"));
        add(campoFoto);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancelar");

        add(okButton);
        add(cancelButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                Anotacao anotacao = getAnotacao();

                if (validaDados()) {
                    try {
                        anotacao.setTitulo(campoTitulo.getText());
                        anotacao.setDescricao(campoDescricao.getText());
                        anotacao.setCor("#"+Integer.toHexString(selectedColor.getRGB()).substring(2));
                        if (!caminhoImagem.isEmpty()) {
                            anotacao.setFoto(caminhoImagem);
                        } 
                        if (novaAnotacao) {
                            taskDAO.inserir(anotacao);
                        } else {
                            taskDAO.update(anotacao);
                        }
                        
                        setVisible(false);

                    } catch (SQLException e1) {
                        MensagemErro.showException(e1);
                    } catch (FileNotFoundException e1) {
                        MensagemErro.showException(e1);
                    } catch (IOException e1) {
                        MensagemErro.showException(e1);
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                setVisible(false);
            }
        });
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Anotacao getAnotacao() {
        return anotacao;
    }

    public boolean validaDados() {
        if (campoTitulo.getText().isEmpty()) {
            MensagemErro.showMensagem(this, "O Título não foi preenchido");
            return false;
        }

        if (caminhoImagem.isEmpty() && novaAnotacao) {
            MensagemErro.showMensagem(this, "A imagem não foi escolhida");
            return false;
        }

        if (!caminhoImagem.isEmpty() && !validaArquivo()) {
            MensagemErro.showMensagem(this, "O Formato da imagem não é válido (validos: png, jpg e jpeg)");
            return false;
        }

        return true;
    }

    private boolean validaArquivo() {
        return (this.caminhoImagem.substring(this.caminhoImagem.length()-3).equals("png") || 
            this.caminhoImagem.substring(this.caminhoImagem.length()-3).equals("jpg") ||
            this.caminhoImagem.substring(this.caminhoImagem.length()-4).equals("jpeg"));
    }
}