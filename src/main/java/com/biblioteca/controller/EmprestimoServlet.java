package com.biblioteca.controller;

import com.biblioteca.dao.EmprestimoDAO;
import com.biblioteca.dao.LivroDAO;
import com.biblioteca.dao.PessoaDAO;
import com.biblioteca.model.Emprestimo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/emprestimos")
public class EmprestimoServlet extends HttpServlet {

    private final EmprestimoDAO dao = new EmprestimoDAO();
    private final LivroDAO livroDAO = new LivroDAO();
    private final PessoaDAO pessoaDAO = new PessoaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "novo":
                    request.setAttribute("livros", livroDAO.listarTodos());
                    request.setAttribute("pessoas", pessoaDAO.listarTodos());
                    request.setAttribute("pagina", "emprestimos");
                    request.getRequestDispatcher("/emprestimo/formulario.jsp").forward(request, response);
                    break;
                case "devolver":
                    dao.devolver(Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("mensagem", "Livro devolvido com sucesso!");
                    request.getSession().setAttribute("tipoMensagem", "success");
                    response.sendRedirect(request.getContextPath() + "/emprestimos?action=listar");
                    break;
                case "deletar":
                    dao.deletar(Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("mensagem", "Empréstimo removido com sucesso!");
                    request.getSession().setAttribute("tipoMensagem", "success");
                    response.sendRedirect(request.getContextPath() + "/emprestimos?action=listar");
                    break;
                default:
                    request.setAttribute("emprestimos", dao.listarTodos());
                    request.setAttribute("pagina", "emprestimos");
                    request.getRequestDispatcher("/emprestimo/lista.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
            response.sendRedirect(request.getContextPath() + "/emprestimos?action=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Emprestimo emp = new Emprestimo();
            emp.setLivroId(Integer.parseInt(request.getParameter("livroId")));
            emp.setPessoaId(Integer.parseInt(request.getParameter("pessoaId")));

            String dataEmp = request.getParameter("dataEmprestimo");
            emp.setDataEmprestimo(dataEmp != null && !dataEmp.isEmpty() ? LocalDate.parse(dataEmp) : LocalDate.now());

            String dataPrev = request.getParameter("dataPrevisaoDevolucao");
            emp.setDataPrevisaoDevolucao(LocalDate.parse(dataPrev));

            dao.inserir(emp);
            request.getSession().setAttribute("mensagem", "Empréstimo registrado com sucesso!");
            request.getSession().setAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro ao registrar empréstimo: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
        }
        response.sendRedirect(request.getContextPath() + "/emprestimos?action=listar");
    }
}
