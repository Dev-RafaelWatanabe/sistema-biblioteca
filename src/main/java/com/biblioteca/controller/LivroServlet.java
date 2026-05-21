package com.biblioteca.controller;

import com.biblioteca.dao.AutorDAO;
import com.biblioteca.dao.LivroDAO;
import com.biblioteca.model.Livro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/livros")
public class LivroServlet extends HttpServlet {

    private final LivroDAO dao = new LivroDAO();
    private final AutorDAO autorDAO = new AutorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "novo":
                    request.setAttribute("autores", autorDAO.listarTodos());
                    request.setAttribute("pagina", "livros");
                    request.getRequestDispatcher("/livro/formulario.jsp").forward(request, response);
                    break;
                case "editar":
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("livro", dao.buscarPorId(id));
                    request.setAttribute("autores", autorDAO.listarTodos());
                    request.setAttribute("pagina", "livros");
                    request.getRequestDispatcher("/livro/formulario.jsp").forward(request, response);
                    break;
                case "deletar":
                    dao.deletar(Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("mensagem", "Livro removido com sucesso!");
                    request.getSession().setAttribute("tipoMensagem", "success");
                    response.sendRedirect(request.getContextPath() + "/livros?action=listar");
                    break;
                default:
                    request.setAttribute("livros", dao.listarTodos());
                    request.setAttribute("pagina", "livros");
                    request.getRequestDispatcher("/livro/lista.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
            response.sendRedirect(request.getContextPath() + "/livros?action=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Livro livro = new Livro();
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                livro.setId(Integer.parseInt(idStr));
            }
            livro.setTitulo(request.getParameter("titulo"));
            livro.setIsbn(request.getParameter("isbn"));
            String ano = request.getParameter("anoPublicacao");
            if (ano != null && !ano.isEmpty()) livro.setAnoPublicacao(Integer.parseInt(ano));
            livro.setGenero(request.getParameter("genero"));
            String qtd = request.getParameter("quantidadeTotal");
            int quantidade = qtd != null && !qtd.isEmpty() ? Integer.parseInt(qtd) : 1;
            livro.setQuantidadeTotal(quantidade);
            livro.setQuantidadeDisponivel(quantidade);
            String autorId = request.getParameter("autorId");
            if (autorId != null && !autorId.isEmpty()) livro.setAutorId(Integer.parseInt(autorId));

            if (livro.getId() > 0) {
                // Ao editar, manter quantidade disponível proporcional
                Livro antigo = dao.buscarPorId(livro.getId());
                int emprestados = antigo.getQuantidadeTotal() - antigo.getQuantidadeDisponivel();
                livro.setQuantidadeDisponivel(Math.max(0, livro.getQuantidadeTotal() - emprestados));
                dao.atualizar(livro);
                request.getSession().setAttribute("mensagem", "Livro atualizado com sucesso!");
            } else {
                dao.inserir(livro);
                request.getSession().setAttribute("mensagem", "Livro cadastrado com sucesso!");
            }
            request.getSession().setAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro ao salvar livro: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
        }
        response.sendRedirect(request.getContextPath() + "/livros?action=listar");
    }
}
