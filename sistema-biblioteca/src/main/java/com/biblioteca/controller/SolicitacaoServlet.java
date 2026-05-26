package com.biblioteca.controller;

import com.biblioteca.dao.AluguelDAO;
import com.biblioteca.model.Aluno;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/aluno/alugueis")
public class SolicitacaoServlet extends HttpServlet {

    private final AluguelDAO dao = new AluguelDAO();

    private Aluno alunoLogado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Object o = session.getAttribute("usuarioLogado");
        return (o instanceof Aluno) ? (Aluno) o : null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Aluno aluno = alunoLogado(request);
        if (aluno == null) {
            response.sendRedirect(request.getContextPath() + "/loginAluno");
            return;
        }
        try {
            request.setAttribute("alugueis", dao.listarPorAluno(aluno.getId()));
            request.setAttribute("pagina", "meusAlugueis");
            request.getRequestDispatcher("/aluno/meus-alugueis.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erro ao listar aluguéis do aluno", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Aluno aluno = alunoLogado(request);
        if (aluno == null) {
            response.sendRedirect(request.getContextPath() + "/loginAluno");
            return;
        }
        try {
            int livroId = Integer.parseInt(request.getParameter("livroId"));
            dao.criarSolicitacao(aluno.getId(), livroId);
            request.getSession().setAttribute("mensagem", "Solicitação enviada! Aguardando aprovação do bibliotecário.");
            request.getSession().setAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro ao solicitar aluguel: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
        }
        response.sendRedirect(request.getContextPath() + "/aluno/alugueis");
    }
}
