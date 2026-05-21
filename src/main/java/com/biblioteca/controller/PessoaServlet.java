package com.biblioteca.controller;

import com.biblioteca.dao.PessoaDAO;
import com.biblioteca.model.Pessoa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/pessoas")
public class PessoaServlet extends HttpServlet {

    private final PessoaDAO dao = new PessoaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "novo":
                    request.setAttribute("pagina", "pessoas");
                    request.getRequestDispatcher("/pessoa/formulario.jsp").forward(request, response);
                    break;
                case "editar":
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("pessoa", dao.buscarPorId(id));
                    request.setAttribute("pagina", "pessoas");
                    request.getRequestDispatcher("/pessoa/formulario.jsp").forward(request, response);
                    break;
                case "deletar":
                    dao.deletar(Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("mensagem", "Pessoa removida com sucesso!");
                    request.getSession().setAttribute("tipoMensagem", "success");
                    response.sendRedirect(request.getContextPath() + "/pessoas?action=listar");
                    break;
                default:
                    request.setAttribute("pessoas", dao.listarTodos());
                    request.setAttribute("pagina", "pessoas");
                    request.getRequestDispatcher("/pessoa/lista.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
            response.sendRedirect(request.getContextPath() + "/pessoas?action=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Pessoa pessoa = new Pessoa();
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                pessoa.setId(Integer.parseInt(idStr));
            }
            pessoa.setNome(request.getParameter("nome"));
            pessoa.setEmail(request.getParameter("email"));
            pessoa.setTelefone(request.getParameter("telefone"));
            pessoa.setCpf(request.getParameter("cpf"));
            pessoa.setEndereco(request.getParameter("endereco"));

            if (pessoa.getId() > 0) {
                dao.atualizar(pessoa);
                request.getSession().setAttribute("mensagem", "Pessoa atualizada com sucesso!");
            } else {
                dao.inserir(pessoa);
                request.getSession().setAttribute("mensagem", "Pessoa cadastrada com sucesso!");
            }
            request.getSession().setAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro ao salvar pessoa: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
        }
        response.sendRedirect(request.getContextPath() + "/pessoas?action=listar");
    }
}
