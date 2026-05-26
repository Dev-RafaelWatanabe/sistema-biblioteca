package com.biblioteca.controller;

import com.biblioteca.dao.AlunoDAO;
import com.biblioteca.dao.TokenDAO;
import com.biblioteca.model.Aluno;
import com.biblioteca.model.regra.RegraAluno;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

@WebServlet("/loginAluno")
public class LoginAlunoServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginAlunoServlet.class.getName());
    private final AlunoDAO dao = new AlunoDAO();
    private final TokenDAO tokenDAO = new TokenDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogado") != null
                && "aluno".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/aluno/painel");
            return;
        }
        request.getRequestDispatcher("/login-aluno.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        boolean manter = "on".equals(request.getParameter("manter")) || "true".equals(request.getParameter("manter"));

        String erroValidacao = RegraAluno.validarLogin(cpf, senha);
        if (erroValidacao != null) {
            request.setAttribute("erro", erroValidacao);
            request.setAttribute("cpf", cpf);
            request.getRequestDispatcher("/login-aluno.jsp").forward(request, response);
            return;
        }

        try {
            Aluno aluno = dao.autenticar(cpf, senha);
            if (aluno != null) {
                logger.info("[LOGIN] Tipo: ALUNO | CPF: " + cpf + " | Nome: " + aluno.getNome());
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", aluno);
                session.setAttribute("tipoUsuario", "aluno");
                session.setMaxInactiveInterval(30 * 60);

                if (manter) {
                    String token = UUID.randomUUID().toString();
                    LocalDateTime expiracao = LocalDateTime.now().plusDays(7);
                    tokenDAO.salvar(token, aluno.getId(), "aluno", expiracao);

                    Cookie cookie = new Cookie("remember_token", token);
                    cookie.setMaxAge(7 * 24 * 60 * 60);
                    cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                }

                response.sendRedirect(request.getContextPath() + "/aluno/painel");
            } else {
                request.setAttribute("erro", "CPF ou senha inválidos!");
                request.setAttribute("cpf", cpf);
                request.getRequestDispatcher("/login-aluno.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao autenticar: " + e.getMessage());
            request.getRequestDispatcher("/login-aluno.jsp").forward(request, response);
        }
    }
}
