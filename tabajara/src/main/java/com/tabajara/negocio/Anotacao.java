package com.tabajara.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;

public class Anotacao {
    private int id;
    private String titulo;
    private String descricao;
    private Date dataHora;
    private String cor;
    private byte[] foto;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Date getDataHora() {
        return dataHora;
    }
    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }

    public byte[] getFoto() {
        return foto;
    }
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public void setFoto(String diretorio) throws FileNotFoundException, IOException {        
        File f = new File(diretorio);
        FileInputStream fileInputStream = new FileInputStream(f);
        this.foto = fileInputStream.readAllBytes();
        fileInputStream.close();
    }
    
    public String toString() {
        return titulo + (descricao.isEmpty() ? "" : ":"+descricao);
    }
}
