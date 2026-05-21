<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Dashboard" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="stats-grid">
    <div class="stat-card primary">
        <div class="stat-icon"><i class="bi bi-journal-bookmark-fill"></i></div>
        <div class="stat-value">${totalLivros}</div>
        <div class="stat-label">Livros Cadastrados</div>
    </div>
    <div class="stat-card info">
        <div class="stat-icon"><i class="bi bi-pen-fill"></i></div>
        <div class="stat-value">${totalAutores}</div>
        <div class="stat-label">Autores</div>
    </div>
    <div class="stat-card success">
        <div class="stat-icon"><i class="bi bi-people-fill"></i></div>
        <div class="stat-value">${totalPessoas}</div>
        <div class="stat-label">Pessoas Cadastradas</div>
    </div>
    <div class="stat-card warning">
        <div class="stat-icon"><i class="bi bi-arrow-left-right"></i></div>
        <div class="stat-value">${emprestimosAtivos}</div>
        <div class="stat-label">Empréstimos Ativos</div>
    </div>
    <div class="stat-card danger">
        <div class="stat-icon"><i class="bi bi-exclamation-triangle-fill"></i></div>
        <div class="stat-value">${emprestimosAtrasados}</div>
        <div class="stat-label">Empréstimos Atrasados</div>
    </div>
</div>

<!-- Quick Actions -->
<div class="page-header">
    <h2>Ações Rápidas</h2>
</div>
<div class="row g-3">
    <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/livros?action=novo" class="btn btn-primary-custom w-100">
            <i class="bi bi-plus-lg"></i> Novo Livro
        </a>
    </div>
    <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/autores?action=novo" class="btn btn-primary-custom w-100">
            <i class="bi bi-plus-lg"></i> Novo Autor
        </a>
    </div>
    <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/pessoas?action=novo" class="btn btn-primary-custom w-100">
            <i class="bi bi-plus-lg"></i> Nova Pessoa
        </a>
    </div>
    <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/emprestimos?action=novo" class="btn btn-primary-custom w-100">
            <i class="bi bi-plus-lg"></i> Novo Empréstimo
        </a>
    </div>
</div>

<%@ include file="/layout/footer.jsp" %>
