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
        <div class="stat-value">${totalAlunos}</div>
        <div class="stat-label">Alunos Cadastrados</div>
    </div>
    <div class="stat-card warning">
        <div class="stat-icon"><i class="bi bi-hourglass-split"></i></div>
        <div class="stat-value">${alugueisPendentes}</div>
        <div class="stat-label">Solicitações Pendentes</div>
    </div>
    <div class="stat-card danger">
        <div class="stat-icon"><i class="bi bi-arrow-left-right"></i></div>
        <div class="stat-value">${alugueisAtivos}</div>
        <div class="stat-label">Aluguéis em Andamento</div>
    </div>
</div>

<!-- Quick Actions -->
<div class="page-header">
    <h2>Ações Rápidas</h2>
</div>
<div class="row g-3">
    <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/admin/livros?action=novo" class="btn btn-primary-custom w-100">
            <i class="bi bi-plus-lg"></i> Novo Livro
        </a>
    </div>
    <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/admin/autores?action=novo" class="btn btn-primary-custom w-100">
            <i class="bi bi-plus-lg"></i> Novo Autor
        </a>
    </div>
    <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/admin/alunos?action=novo" class="btn btn-primary-custom w-100">
            <i class="bi bi-plus-lg"></i> Novo Aluno
        </a>
    </div>
    <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/admin/alugueis?action=listar" class="btn btn-primary-custom w-100">
            <i class="bi bi-arrow-left-right"></i> Solicitações
        </a>
    </div>
</div>

<%@ include file="/layout/footer.jsp" %>
