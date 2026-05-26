package com.biblioteca.controller;

import com.biblioteca.dao.TokenDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private final TokenDAO tokenDAO = new TokenDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        // Remove cookie remember_token + apaga do banco
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("remember_token".equals(c.getName())) {
                    try { tokenDAO.excluirPorToken(c.getValue()); } catch (Exception ignored) {}
                    Cookie del = new Cookie("remember_token", "");
                    del.setMaxAge(0);
                    del.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
                    response.addCookie(del);
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}
