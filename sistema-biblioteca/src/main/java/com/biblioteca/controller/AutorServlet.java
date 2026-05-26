package com.biblioteca.controller;

import com.biblioteca.dao.AutorDAO;
import com.biblioteca.model.Autor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/admin/autores")
public class AutorServlet extends HttpServlet {

    private final AutorDAO dao = new AutorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "novo":
                    request.setAttribute("pagina", "autores");
                    request.getRequestDispatcher("/autor/formulario.jsp").forward(request, response);
                    break;
                case "editar":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Autor autor = dao.buscarPorId(id);
                    request.setAttribute("autor", autor);
                    request.setAttribute("pagina", "autores");
                    request.getRequestDispatcher("/autor/formulario.jsp").forward(request, response);
                    break;
                case "deletar":
                    dao.deletar(Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("mensagem", "Autor removido com sucesso!");
                    request.getSession().setAttribute("tipoMensagem", "success");
                    response.sendRedirect(request.getContextPath() + "/admin/autores?action=listar");
                    break;
                default:
                    request.setAttribute("autores", dao.listarTodos());
                    request.setAttribute("pagina", "autores");
                    request.getRequestDispatcher("/autor/lista.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
            response.sendRedirect(request.getContextPath() + "/admin/autores?action=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Autor autor = new Autor();
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                autor.setId(Integer.parseInt(idStr));
            }
            autor.setNome(request.getParameter("nome"));
            autor.setNacionalidade(request.getParameter("nacionalidade"));
            String dataNasc = request.getParameter("dataNascimento");
            if (dataNasc != null && !dataNasc.isEmpty()) {
                autor.setDataNascimento(LocalDate.parse(dataNasc));
            }

            if (autor.getId() > 0) {
                dao.atualizar(autor);
                request.getSession().setAttribute("mensagem", "Autor atualizado com sucesso!");
            } else {
                dao.inserir(autor);
                request.getSession().setAttribute("mensagem", "Autor cadastrado com sucesso!");
            }
            request.getSession().setAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro ao salvar autor: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
        }
        response.sendRedirect(request.getContextPath() + "/admin/autores?action=listar");
    }
}
