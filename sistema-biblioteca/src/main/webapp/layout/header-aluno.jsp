<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema Biblioteca - Aluno</title>
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
            <div class="brand-icon"><i class="bi bi-mortarboard"></i></div>
            <div>
                <h2>Biblioteca</h2>
                <small>Painel do Aluno</small>
            </div>
        </div>

        <div class="sidebar-nav">
            <div class="nav-section-title">Menu Principal</div>
            <a href="${pageContext.request.contextPath}/aluno/painel"
               class="nav-link ${pagina == 'painel' ? 'active' : ''}">
                <i class="bi bi-house-fill"></i> Início
            </a>
            <a href="${pageContext.request.contextPath}/aluno/catalogo"
               class="nav-link ${pagina == 'catalogo' ? 'active' : ''}">
                <i class="bi bi-journal-bookmark-fill"></i> Livros Disponíveis
            </a>
            <a href="${pageContext.request.contextPath}/aluno/alugueis"
               class="nav-link ${pagina == 'meusAlugueis' ? 'active' : ''}">
                <i class="bi bi-bookmark-check-fill"></i> Meus Aluguéis
            </a>
        </div>

        <div class="sidebar-footer">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link">
                <i class="bi bi-box-arrow-left"></i> Sair
            </a>
        </div>
    </nav>

    <!-- Main Content -->
    <main class="main-content">
        <div class="content-header">
            <h1>${titulo != null ? titulo : 'Painel do Aluno'}</h1>
            <div class="user-info">
                <span>${sessionScope.usuarioLogado.nome}</span>
                <div class="user-avatar">
                    ${sessionScope.usuarioLogado.nome.substring(0, 1)}
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
