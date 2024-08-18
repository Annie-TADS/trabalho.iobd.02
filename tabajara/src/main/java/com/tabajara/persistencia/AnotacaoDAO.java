package com.tabajara.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.tabajara.negocio.Anotacao;
import com.tabajara.servicos.Conexao;

public class AnotacaoDAO {
    public ArrayList<Anotacao> listar() throws SQLException {
        ArrayList<Anotacao> anotacoes = new ArrayList<>();
        String sql = "SELECT * FROM anotacao ORDER BY data_hora DESC;";
        Connection conexao = new Conexao().getConexao();
        ResultSet resultSet = conexao.prepareStatement(sql).executeQuery();
        while (resultSet.next()) {
            anotacoes.add(converterResultados(resultSet));
        }

        return anotacoes;
    } 

    public Anotacao get(int id) throws SQLException {
        String sql = "SELECT * FROM anotacao WHERE id = ?;";

        Connection conexao = new Conexao().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);

        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return converterResultados(resultSet);
        }

        return null;
    }

    public boolean inserir(Anotacao anotacao) throws SQLException {
        String sql = "INSERT INTO anotacao (titulo, cor, descricao, foto) VALUES(?, ?, ?, ?);";
        Connection conexao = new Conexao().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);

        preparedStatement.setString(1, anotacao.getTitulo());
        preparedStatement.setString(2, anotacao.getCor());
        preparedStatement.setString(3, anotacao.getDescricao());
        preparedStatement.setBytes(4, anotacao.getFoto());
        return preparedStatement.execute();
    }

    public boolean duplicar(int id) throws SQLException {
        return inserir(get(id));
    }

    public boolean update(Anotacao anotacao) throws SQLException {
        String sql = "UPDATE anotacao SET titulo = ?, cor = ?, descricao = ?, foto = ? WHERE id = ?;";
        Connection conexao = new Conexao().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);

        preparedStatement.setString(1, anotacao.getTitulo());
        preparedStatement.setString(2, anotacao.getCor());
        preparedStatement.setString(3, anotacao.getDescricao());
        preparedStatement.setBytes(4, anotacao.getFoto());
        preparedStatement.setInt(5, anotacao.getId());
        return preparedStatement.executeUpdate() == 1;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM anotacao WHERE id = ?;";
        Connection conexao = new Conexao().getConexao();
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);

        preparedStatement.setInt(1, id);
        return preparedStatement.execute();
    }

    private Anotacao converterResultados(ResultSet resultSet) throws SQLException {
        Anotacao anotacao = new Anotacao();

        anotacao.setId(resultSet.getInt("id"));
        anotacao.setTitulo(resultSet.getString("titulo"));
        anotacao.setDescricao(resultSet.getString("descricao"));
        anotacao.setDataHora(resultSet.getDate("data_hora"));
        anotacao.setCor(resultSet.getString("cor"));
        anotacao.setFoto(resultSet.getBytes("foto"));

        return anotacao;
    }
}
