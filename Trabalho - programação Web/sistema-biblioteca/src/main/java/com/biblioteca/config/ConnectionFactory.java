package com.biblioteca.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {

    private static HikariDataSource dataSource;

    static {
        try {
            // Explicitly load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            HikariConfig config = new HikariConfig();
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String pass = System.getenv("DB_PASS");

            config.setDriverClassName("org.postgresql.Driver");
            config.setJdbcUrl(url != null ? url : "jdbc:postgresql://localhost:5432/biblioteca");
            config.setUsername(user != null ? user : "biblioteca");
            config.setPassword(pass != null ? pass : "biblioteca123");
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(10000);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar pool de conexões", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
