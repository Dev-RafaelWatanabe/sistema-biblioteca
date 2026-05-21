package com.biblioteca.dao;

import com.biblioteca.config.ConnectionFactory;
import com.biblioteca.model.Emprestimo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    /** Atualiza empréstimos ativos cujo prazo já passou para ATRASADO */
    public void atualizarAtrasados() throws SQLException {
        String sql = "UPDATE emprestimo SET status = 'ATRASADO' " +
                     "WHERE status = 'ATIVO' AND data_previsao_devolucao < CURRENT_DATE";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    public List<Emprestimo> listarTodos() throws SQLException {
        atualizarAtrasados();
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT e.*, l.titulo AS livro_titulo, p.nome AS pessoa_nome " +
                     "FROM emprestimo e " +
                     "JOIN livro l ON e.livro_id = l.id " +
                     "JOIN pessoa p ON e.pessoa_id = p.id " +
                     "ORDER BY e.data_emprestimo DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Emprestimo buscarPorId(int id) throws SQLException {
        String sql = "SELECT e.*, l.titulo AS livro_titulo, p.nome AS pessoa_nome " +
                     "FROM emprestimo e " +
                     "JOIN livro l ON e.livro_id = l.id " +
                     "JOIN pessoa p ON e.pessoa_id = p.id " +
                     "WHERE e.id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void inserir(Emprestimo emp) throws SQLException {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            // Inserir empréstimo
            String sqlEmp = "INSERT INTO emprestimo (livro_id, pessoa_id, data_emprestimo, data_previsao_devolucao, status) " +
                            "VALUES (?, ?, ?, ?, 'ATIVO')";
            try (PreparedStatement ps = conn.prepareStatement(sqlEmp)) {
                ps.setInt(1, emp.getLivroId());
                ps.setInt(2, emp.getPessoaId());
                ps.setDate(3, Date.valueOf(emp.getDataEmprestimo() != null ? emp.getDataEmprestimo() : LocalDate.now()));
                ps.setDate(4, Date.valueOf(emp.getDataPrevisaoDevolucao()));
                ps.executeUpdate();
            }

            // Decrementar quantidade disponível do livro
            String sqlLivro = "UPDATE livro SET quantidade_disponivel = quantidade_disponivel - 1 " +
                              "WHERE id = ? AND quantidade_disponivel > 0";
            try (PreparedStatement ps = conn.prepareStatement(sqlLivro)) {
                ps.setInt(1, emp.getLivroId());
                int rows = ps.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    throw new SQLException("Livro sem exemplares disponíveis!");
                }
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

    public void devolver(int id) throws SQLException {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            // Buscar o livro_id do empréstimo
            int livroId = 0;
            String sqlBusca = "SELECT livro_id FROM emprestimo WHERE id = ? AND data_devolucao IS NULL";
            try (PreparedStatement ps = conn.prepareStatement(sqlBusca)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        livroId = rs.getInt("livro_id");
                    } else {
                        throw new SQLException("Empréstimo não encontrado ou já devolvido!");
                    }
                }
            }

            // Registrar devolução
            String sqlDev = "UPDATE emprestimo SET data_devolucao = CURRENT_DATE, status = 'DEVOLVIDO' WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDev)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // Incrementar quantidade disponível do livro
            String sqlLivro = "UPDATE livro SET quantidade_disponivel = quantidade_disponivel + 1 WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlLivro)) {
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

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM emprestimo WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public int contarAtivos() throws SQLException {
        atualizarAtrasados();
        String sql = "SELECT COUNT(*) FROM emprestimo WHERE status = 'ATIVO'";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int contarAtrasados() throws SQLException {
        atualizarAtrasados();
        String sql = "SELECT COUNT(*) FROM emprestimo WHERE status = 'ATRASADO'";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    private Emprestimo mapear(ResultSet rs) throws SQLException {
        Emprestimo e = new Emprestimo();
        e.setId(rs.getInt("id"));
        e.setLivroId(rs.getInt("livro_id"));
        e.setPessoaId(rs.getInt("pessoa_id"));
        Date de = rs.getDate("data_emprestimo");
        if (de != null) e.setDataEmprestimo(de.toLocalDate());
        Date dp = rs.getDate("data_previsao_devolucao");
        if (dp != null) e.setDataPrevisaoDevolucao(dp.toLocalDate());
        Date dd = rs.getDate("data_devolucao");
        if (dd != null) e.setDataDevolucao(dd.toLocalDate());
        e.setStatus(rs.getString("status"));
        e.setLivroTitulo(rs.getString("livro_titulo"));
        e.setPessoaNome(rs.getString("pessoa_nome"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) e.setCreatedAt(ts.toLocalDateTime());
        return e;
    }
}
