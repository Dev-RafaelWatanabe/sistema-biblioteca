package com.biblioteca.dao;

import com.biblioteca.config.ConnectionFactory;
import com.biblioteca.model.Aluno;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public List<Aluno> listarTodos() throws SQLException {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Aluno buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public Aluno buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE cpf = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public Aluno autenticar(String cpf, String senhaPlana) throws SQLException {
        Aluno a = buscarPorCpf(cpf);
        if (a != null && a.getSenha() != null && BCrypt.checkpw(senhaPlana, a.getSenha())) {
            return a;
        }
        return null;
    }

    public void inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO aluno (nome, email, telefone, cpf, senha, endereco) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getEmail());
            ps.setString(3, aluno.getTelefone());
            ps.setString(4, aluno.getCpf());
            ps.setString(5, BCrypt.hashpw(aluno.getSenha(), BCrypt.gensalt()));
            ps.setString(6, aluno.getEndereco());
            ps.executeUpdate();
        }
    }

    /** Atualiza dados do aluno. Se senha for null/vazia, mantém a anterior. */
    public void atualizar(Aluno aluno) throws SQLException {
        boolean alterarSenha = aluno.getSenha() != null && !aluno.getSenha().trim().isEmpty();
        String sql = alterarSenha
            ? "UPDATE aluno SET nome = ?, email = ?, telefone = ?, cpf = ?, senha = ?, endereco = ? WHERE id = ?"
            : "UPDATE aluno SET nome = ?, email = ?, telefone = ?, cpf = ?, endereco = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getEmail());
            ps.setString(3, aluno.getTelefone());
            ps.setString(4, aluno.getCpf());
            if (alterarSenha) {
                ps.setString(5, BCrypt.hashpw(aluno.getSenha(), BCrypt.gensalt()));
                ps.setString(6, aluno.getEndereco());
                ps.setInt(7, aluno.getId());
            } else {
                ps.setString(5, aluno.getEndereco());
                ps.setInt(6, aluno.getId());
            }
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM aluno WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public int contarTotal() throws SQLException {
        String sql = "SELECT COUNT(*) FROM aluno";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public boolean existeCpf(String cpf, Integer ignorarId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM aluno WHERE cpf = ?");
        if (ignorarId != null) sql.append(" AND id <> ?");
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ps.setString(1, cpf);
            if (ignorarId != null) ps.setInt(2, ignorarId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    private Aluno mapear(ResultSet rs) throws SQLException {
        Aluno a = new Aluno();
        a.setId(rs.getInt("id"));
        a.setNome(rs.getString("nome"));
        a.setEmail(rs.getString("email"));
        a.setTelefone(rs.getString("telefone"));
        a.setCpf(rs.getString("cpf"));
        a.setSenha(rs.getString("senha"));
        a.setEndereco(rs.getString("endereco"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) a.setCreatedAt(ts.toLocalDateTime());
        return a;
    }
}
