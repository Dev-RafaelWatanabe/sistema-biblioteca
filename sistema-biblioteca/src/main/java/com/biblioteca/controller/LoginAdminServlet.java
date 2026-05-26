package com.biblioteca.controller;

import com.biblioteca.dao.TokenDAO;
import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

@WebServlet("/loginAdmin")
public class LoginAdminServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginAdminServlet.class.getName());
    private final UsuarioDAO dao = new UsuarioDAO();
    private final TokenDAO tokenDAO = new TokenDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogado") != null
                && "admin".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }
        request.getRequestDispatcher("/login-admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        boolean manter = "on".equals(request.getParameter("manter")) || "true".equals(request.getParameter("manter"));

        try {
            Usuario usuario = dao.autenticar(email, senha);
            if (usuario != null) {
                logger.info("[LOGIN] Tipo: BIBLIOTECÁRIO | E-mail: " + email + " | Nome: " + usuario.getNome());
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuario);
                session.setAttribute("tipoUsuario", "admin");
                session.setMaxInactiveInterval(30 * 60);

                if (manter) {
                    String token = UUID.randomUUID().toString();
                    LocalDateTime expiracao = LocalDateTime.now().plusDays(7);
                    tokenDAO.salvar(token, usuario.getId(), "admin", expiracao);

                    Cookie cookie = new Cookie("remember_token", token);
                    cookie.setMaxAge(7 * 24 * 60 * 60);
                    cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                }

                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                request.setAttribute("erro", "E-mail ou senha inválidos!");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/login-admin.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao autenticar: " + e.getMessage());
            request.getRequestDispatcher("/login-admin.jsp").forward(request, response);
        }
    }
}
