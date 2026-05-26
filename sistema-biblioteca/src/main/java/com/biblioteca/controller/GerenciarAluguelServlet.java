package com.biblioteca.controller;

import com.biblioteca.dao.AlunoDAO;
import com.biblioteca.dao.AluguelDAO;
import com.biblioteca.dao.LivroDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/alugueis")
public class GerenciarAluguelServlet extends HttpServlet {

    private final AluguelDAO dao = new AluguelDAO();
    private final LivroDAO livroDAO = new LivroDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "novo":
                    request.setAttribute("livros", livroDAO.listarDisponiveis());
                    request.setAttribute("alunos", alunoDAO.listarTodos());
                    request.setAttribute("pagina", "alugueis");
                    request.getRequestDispatcher("/aluguel/formulario.jsp").forward(request, response);
                    break;
                default:
                    request.setAttribute("alugueis", dao.listarTodos());
                    request.setAttribute("pagina", "alugueis");
                    request.getRequestDispatcher("/aluguel/lista.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
            response.sendRedirect(request.getContextPath() + "/admin/alugueis?action=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            if ("aprovar".equals(acao)) {
                dao.aprovar(Integer.parseInt(idStr));
                request.getSession().setAttribute("mensagem", "Solicitação aprovada com sucesso!");
            } else if ("recusar".equals(acao)) {
                dao.recusar(Integer.parseInt(idStr));
                request.getSession().setAttribute("mensagem", "Solicitação recusada.");
            } else if ("finalizar".equals(acao)) {
                dao.finalizar(Integer.parseInt(idStr));
                request.getSession().setAttribute("mensagem", "Aluguel finalizado com sucesso!");
            } else {
                // Criação manual de aluguel (formulário do admin)
                int alunoId = Integer.parseInt(request.getParameter("alunoId"));
                int livroId = Integer.parseInt(request.getParameter("livroId"));
                dao.criarSolicitacao(alunoId, livroId);
                request.getSession().setAttribute("mensagem", "Solicitação criada. Aprove para concluir o aluguel.");
            }
            request.getSession().setAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
        }
        response.sendRedirect(request.getContextPath() + "/admin/alugueis?action=listar");
    }
}
