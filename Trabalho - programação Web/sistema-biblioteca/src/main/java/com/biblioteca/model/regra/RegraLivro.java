package com.biblioteca.model.regra;

import com.biblioteca.dao.LivroDAO;
import com.biblioteca.model.Livro;
import java.sql.SQLException;
import java.time.Year;

public class RegraLivro {

    public static String validarCampos(Livro livro) {
        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            return "Título é obrigatório.";
        }
        if (livro.getAutorId() <= 0) {
            return "Autor é obrigatório.";
        }
        if (livro.getQuantidadeTotal() < 1) {
            return "Quantidade total deve ser no mínimo 1.";
        }
        String erroAno = validarAnoPublicacao(livro.getAnoPublicacao());
        if (erroAno != null) return erroAno;
        return null;
    }

    public static String validarAnoPublicacao(int ano) {
        if (ano <= 0) return null; // opcional
        int anoAtual = Year.now().getValue();
        if (ano < 1450 || ano > anoAtual) {
            return "Ano de publicação deve estar entre 1450 e " + anoAtual + ".";
        }
        return null;
    }

    public static String existeDuplicado(Livro livro, LivroDAO dao) throws SQLException {
        Integer ignorarId = livro.getId() > 0 ? livro.getId() : null;
        if (dao.existeTituloAutor(livro.getTitulo(), livro.getAutorId(), ignorarId)) {
            return "Já existe um livro com este título e autor.";
        }
        return null;
    }
}
