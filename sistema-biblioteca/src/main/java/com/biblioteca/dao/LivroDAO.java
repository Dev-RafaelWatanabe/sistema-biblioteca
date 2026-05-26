package com.biblioteca.dao;

import com.biblioteca.config.ConnectionFactory;
import com.biblioteca.model.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public List<Livro> listarTodos() throws SQLException {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT l.*, a.nome AS autor_nome FROM livro l " +
                     "LEFT JOIN autor a ON l.autor_id = a.id ORDER BY l.titulo";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<Livro> listarDisponiveis() throws SQLException {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT l.*, a.nome AS autor_nome FROM livro l " +
                     "LEFT JOIN autor a ON l.autor_id = a.id " +
                     "WHERE l.status = 'Disponível' AND l.quantidade_disponivel > 0 " +
                     "ORDER BY l.titulo";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Livro buscarPorId(int id) throws SQLException {
        String sql = "SELECT l.*, a.nome AS autor_nome FROM livro l " +
                     "LEFT JOIN autor a ON l.autor_id = a.id WHERE l.id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO livro (titulo, ano_publicacao, genero, quantidade_total, quantidade_disponivel, status, autor_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, livro.getTitulo());
            ps.setInt(2, livro.getAnoPublicacao());
            ps.setString(3, livro.getGenero());
            ps.setInt(4, livro.getQuantidadeTotal());
            ps.setInt(5, livro.getQuantidadeDisponivel());
            ps.setString(6, livro.getStatus() != null ? livro.getStatus() : "Disponível");
            ps.setInt(7, livro.getAutorId());
            ps.executeUpdate();
        }
    }

    public void atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE livro SET titulo = ?, ano_publicacao = ?, genero = ?, " +
                     "quantidade_total = ?, quantidade_disponivel = ?, autor_id = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, livro.getTitulo());
            ps.setInt(2, livro.getAnoPublicacao());
            ps.setString(3, livro.getGenero());
            ps.setInt(4, livro.getQuantidadeTotal());
            ps.setInt(5, livro.getQuantidadeDisponivel());
            ps.setInt(6, livro.getAutorId());
            ps.setInt(7, livro.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM livro WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public int contarTotal() throws SQLException {
        String sql = "SELECT COUNT(*) FROM livro";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public boolean existeTituloAutor(String titulo, int autorId, Integer ignorarId) throws SQLException {
        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(*) FROM livro WHERE LOWER(titulo) = LOWER(?) AND autor_id = ?");
        if (ignorarId != null) sql.append(" AND id <> ?");
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ps.setString(1, titulo);
            ps.setInt(2, autorId);
            if (ignorarId != null) ps.setInt(3, ignorarId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    private Livro mapear(ResultSet rs) throws SQLException {
        Livro l = new Livro();
        l.setId(rs.getInt("id"));
        l.setTitulo(rs.getString("titulo"));
        l.setAnoPublicacao(rs.getInt("ano_publicacao"));
        l.setGenero(rs.getString("genero"));
        l.setQuantidadeTotal(rs.getInt("quantidade_total"));
        l.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));
        l.setStatus(rs.getString("status"));
        l.setAutorId(rs.getInt("autor_id"));
        l.setAutorNome(rs.getString("autor_nome"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) l.setCreatedAt(ts.toLocalDateTime());
        return l;
    }
}
