package com.biblioteca.controller;

import com.biblioteca.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AutorDAO autorDAO = new AutorDAO();
            LivroDAO livroDAO = new LivroDAO();
            PessoaDAO pessoaDAO = new PessoaDAO();
            EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

            request.setAttribute("totalAutores", autorDAO.listarTodos().size());
            request.setAttribute("totalLivros", livroDAO.contarTotal());
            request.setAttribute("totalPessoas", pessoaDAO.contarTotal());
            request.setAttribute("emprestimosAtivos", emprestimoDAO.contarAtivos());
            request.setAttribute("emprestimosAtrasados", emprestimoDAO.contarAtrasados());
            request.setAttribute("pagina", "dashboard");

            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erro ao carregar dashboard", e);
        }
    }
}
