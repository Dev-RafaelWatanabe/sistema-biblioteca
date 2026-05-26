package com.biblioteca.dao;

import com.biblioteca.config.ConnectionFactory;
import com.biblioteca.model.Aluguel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AluguelDAO {

    private static final String SELECT_BASE =
        "SELECT al.*, l.titulo AS livro_titulo, a.nome AS aluno_nome, au.nome AS autor_nome " +
        "FROM aluguel al " +
        "JOIN livro l ON al.livro_id = l.id " +
        "JOIN aluno a ON al.aluno_id = a.id " +
        "LEFT JOIN autor au ON l.autor_id = au.id ";

    public List<Aluguel> listarTodos() throws SQLException {
        List<Aluguel> lista = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY al.data_solicitacao DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<Aluguel> listarPorAluno(int alunoId) throws SQLException {
        List<Aluguel> lista = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE al.aluno_id = ? ORDER BY al.data_solicitacao DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, alunoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Aluguel buscarPorId(int id) throws SQLException {
        String sql = SELECT_BASE + "WHERE al.id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void criarSolicitacao(int alunoId, int livroId) throws SQLException {
        String sql = "INSERT INTO aluguel (aluno_id, livro_id, status) VALUES (?, ?, 'Solicitado')";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, alunoId);
            ps.setInt(2, livroId);
            ps.executeUpdate();
        }
    }

    /** Aprova: status Concluído + livro Alugado (transacional). */
    public void aprovar(int aluguelId) throws SQLException {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            int livroId;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT livro_id FROM aluguel WHERE id = ? AND status = 'Solicitado'")) {
                ps.setInt(1, aluguelId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Solicitação não encontrada ou já processada.");
                    }
                    livroId = rs.getInt("livro_id");
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE aluguel SET status = 'Concluído', data_aprovacao = CURRENT_TIMESTAMP WHERE id = ?")) {
                ps.setInt(1, aluguelId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE livro SET status = 'Alugado', quantidade_disponivel = GREATEST(quantidade_disponivel - 1, 0) WHERE id = ?")) {
                ps.setInt(1, livroId);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    /** Finaliza: status Finalizado + livro Disponível (transacional). */
    public void finalizar(int aluguelId) throws SQLException {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            int livroId;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT livro_id FROM aluguel WHERE id = ? AND status = 'Concluído'")) {
                ps.setInt(1, aluguelId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Aluguel não encontrado ou não está em andamento.");
                    }
                    livroId = rs.getInt("livro_id");
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE aluguel SET status = 'Finalizado', data_finalizacao = CURRENT_TIMESTAMP WHERE id = ?")) {
                ps.setInt(1, aluguelId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE livro SET status = 'Disponível', quantidade_disponivel = quantidade_disponivel + 1 WHERE id = ?")) {
                ps.setInt(1, livroId);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void recusar(int aluguelId) throws SQLException {
        String sql = "UPDATE aluguel SET status = 'Recusada' WHERE id = ? AND status = 'Solicitado'";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, aluguelId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Solicitação não encontrada ou não pode ser recusada.");
            }
        }
    }

    public int contarPendentes() throws SQLException {
        String sql = "SELECT COUNT(*) FROM aluguel WHERE status = 'Solicitado'";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int contarAtivos() throws SQLException {
        String sql = "SELECT COUNT(*) FROM aluguel WHERE status IN ('Solicitado','Concluído')";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    private Aluguel mapear(ResultSet rs) throws SQLException {
        Aluguel a = new Aluguel();
        a.setId(rs.getInt("id"));
        a.setAlunoId(rs.getInt("aluno_id"));
        a.setLivroId(rs.getInt("livro_id"));
        Timestamp ds = rs.getTimestamp("data_solicitacao");
        if (ds != null) a.setDataSolicitacao(ds.toLocalDateTime());
        Timestamp da = rs.getTimestamp("data_aprovacao");
        if (da != null) a.setDataAprovacao(da.toLocalDateTime());
        Timestamp df = rs.getTimestamp("data_finalizacao");
        if (df != null) a.setDataFinalizacao(df.toLocalDateTime());
        a.setStatus(rs.getString("status"));
        a.setLivroTitulo(rs.getString("livro_titulo"));
        a.setAlunoNome(rs.getString("aluno_nome"));
        a.setAutorNome(rs.getString("autor_nome"));
        return a;
    }
}
