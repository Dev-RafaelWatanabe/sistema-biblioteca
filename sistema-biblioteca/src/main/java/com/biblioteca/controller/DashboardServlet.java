package com.biblioteca.controller;

import com.biblioteca.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AutorDAO autorDAO = new AutorDAO();
            LivroDAO livroDAO = new LivroDAO();
            AlunoDAO alunoDAO = new AlunoDAO();
            AluguelDAO aluguelDAO = new AluguelDAO();

            request.setAttribute("totalAutores", autorDAO.listarTodos().size());
            request.setAttribute("totalLivros", livroDAO.contarTotal());
            request.setAttribute("totalAlunos", alunoDAO.contarTotal());
            request.setAttribute("alugueisPendentes", aluguelDAO.contarPendentes());
            request.setAttribute("alugueisAtivos", aluguelDAO.contarAtivos());
            request.setAttribute("pagina", "dashboard");

            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erro ao carregar dashboard", e);
        }
    }
}
