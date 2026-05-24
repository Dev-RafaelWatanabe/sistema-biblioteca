package com.biblioteca.dao;

import com.biblioteca.config.ConnectionFactory;
import com.biblioteca.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class UsuarioDAO {

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null) u.setCreatedAt(ts.toLocalDateTime());
                    return u;
                }
            }
        }
        return null;
    }

    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null) u.setCreatedAt(ts.toLocalDateTime());
                    return u;
                }
            }
        }
        return null;
    }

    public Usuario autenticar(String email, String senhaPlana) throws SQLException {
        Usuario u = buscarPorEmail(email);
        if (u != null && BCrypt.checkpw(senhaPlana, u.getSenha())) {
            return u;
        }
        return null;
    }

    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
            ps.executeUpdate();
        }
    }

    public boolean existeAlgum() throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }
}
