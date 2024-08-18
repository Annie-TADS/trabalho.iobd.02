package com.tabajara.servicos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private class SqlParams {
        private static final String host = "localhost:5432";
        private static final String database = "tabajara";
        private static final String user = "postgres";
        private static final String password = "postgres";
    }

    public Connection getConexao() throws SQLException {
        String url = "jdbc:postgresql://"+SqlParams.host+"/"+SqlParams.database;
        return DriverManager.getConnection(url, SqlParams.user, SqlParams.password);
    }
}
