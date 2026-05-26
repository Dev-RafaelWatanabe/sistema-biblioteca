package com.biblioteca.controller;

import com.biblioteca.dao.LivroDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/aluno/catalogo")
public class CatalogoServlet extends HttpServlet {

    private final LivroDAO livroDAO = new LivroDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("livros", livroDAO.listarDisponiveis());
            request.setAttribute("pagina", "catalogo");
            request.getRequestDispatcher("/aluno/catalogo.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erro ao listar catálogo", e);
        }
    }
}
