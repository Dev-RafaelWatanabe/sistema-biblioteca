<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema Biblioteca</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="app-container">
    <!-- Sidebar -->
    <nav class="sidebar">
        <div class="sidebar-brand">
            <div class="brand-icon"><i class="bi bi-book-half"></i></div>
            <div>
                <h2>Biblioteca</h2>
                <small>Sistema de Gestão</small>
            </div>
        </div>

        <div class="sidebar-nav">
            <div class="nav-section-title">Menu Principal</div>
            <a href="${pageContext.request.contextPath}/dashboard"
               class="nav-link ${pagina == 'dashboard' ? 'active' : ''}">
                <i class="bi bi-grid-1x2-fill"></i> Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/autores?action=listar"
               class="nav-link ${pagina == 'autores' ? 'active' : ''}">
                <i class="bi bi-pen-fill"></i> Autores
            </a>
            <a href="${pageContext.request.contextPath}/livros?action=listar"
               class="nav-link ${pagina == 'livros' ? 'active' : ''}">
                <i class="bi bi-journal-bookmark-fill"></i> Livros
            </a>
            <a href="${pageContext.request.contextPath}/pessoas?action=listar"
               class="nav-link ${pagina == 'pessoas' ? 'active' : ''}">
                <i class="bi bi-people-fill"></i> Pessoas
            </a>

            <div class="nav-section-title" style="margin-top: 16px;">Operações</div>
            <a href="${pageContext.request.contextPath}/emprestimos?action=listar"
               class="nav-link ${pagina == 'emprestimos' ? 'active' : ''}">
                <i class="bi bi-arrow-left-right"></i> Empréstimos
            </a>
        </div>

        <div class="sidebar-footer">
            <a href="${pageContext.request.contextPath}/login?action=logout" class="nav-link">
                <i class="bi bi-box-arrow-left"></i> Sair
            </a>
        </div>
    </nav>

    <!-- Main Content -->
    <main class="main-content">
        <div class="content-header">
            <h1>${titulo != null ? titulo : 'Dashboard'}</h1>
            <div class="user-info">
                <span>${sessionScope.usuario.nome}</span>
                <div class="user-avatar">
                    ${sessionScope.usuario.nome.substring(0, 1)}
                </div>
            </div>
        </div>
        <div class="content-body">
            <!-- Flash Messages -->
            <c:if test="${not empty sessionScope.mensagem}">
                <div class="alert alert-${sessionScope.tipoMensagem} alert-dismissible fade show" role="alert">
                    <i class="bi bi-${sessionScope.tipoMensagem == 'success' ? 'check-circle' : 'exclamation-triangle'}-fill me-2"></i>
                    ${sessionScope.mensagem}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="mensagem" scope="session"/>
                <c:remove var="tipoMensagem" scope="session"/>
            </c:if>
