package com.biblioteca.dao;

import com.biblioteca.config.ConnectionFactory;
import com.biblioteca.model.TokenLogin;
import java.sql.*;
import java.time.LocalDateTime;

public class TokenDAO {

    public void salvar(String token, int usuarioId, String tipoUsuario, LocalDateTime expiracao) throws SQLException {
        String sql = "INSERT INTO tokens_login (token, usuario_id, tipo_usuario, data_expiracao) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.setInt(2, usuarioId);
            ps.setString(3, tipoUsuario);
            ps.setTimestamp(4, Timestamp.valueOf(expiracao));
            ps.executeUpdate();
        }
    }

    public TokenLogin buscarPorToken(String token) throws SQLException {
        String sql = "SELECT * FROM tokens_login WHERE token = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TokenLogin t = new TokenLogin();
                    t.setId(rs.getInt("id"));
                    t.setToken(rs.getString("token"));
                    t.setUsuarioId(rs.getInt("usuario_id"));
                    t.setTipoUsuario(rs.getString("tipo_usuario"));
                    Timestamp ts = rs.getTimestamp("data_expiracao");
                    if (ts != null) t.setDataExpiracao(ts.toLocalDateTime());
                    return t;
                }
            }
        }
        return null;
    }

    public void excluirPorToken(String token) throws SQLException {
        String sql = "DELETE FROM tokens_login WHERE token = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.executeUpdate();
        }
    }

    public void excluirExpirados() throws SQLException {
        String sql = "DELETE FROM tokens_login WHERE data_expiracao < CURRENT_TIMESTAMP";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }
}
