package com.biblioteca.model.regra;

import com.biblioteca.dao.AlunoDAO;
import com.biblioteca.model.Aluno;
import java.sql.SQLException;

public class RegraAluno {

    public static String validarCampos(Aluno aluno, boolean exigirSenha) {
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            return "Nome é obrigatório.";
        }
        if (aluno.getCpf() == null || aluno.getCpf().trim().isEmpty()) {
            return "CPF é obrigatório.";
        }
        if (exigirSenha && (aluno.getSenha() == null || aluno.getSenha().trim().isEmpty())) {
            return "Senha é obrigatória.";
        }
        return null;
    }

    public static String validarLogin(String cpf, String senha) {
        if (cpf == null || cpf.trim().isEmpty()) return "CPF é obrigatório.";
        if (senha == null || senha.trim().isEmpty()) return "Senha é obrigatória.";
        return null;
    }

    public static String existeCpfDuplicado(Aluno aluno, AlunoDAO dao) throws SQLException {
        Integer ignorarId = aluno.getId() > 0 ? aluno.getId() : null;
        if (dao.existeCpf(aluno.getCpf(), ignorarId)) {
            return "Já existe um aluno com este CPF.";
        }
        return null;
    }
}
