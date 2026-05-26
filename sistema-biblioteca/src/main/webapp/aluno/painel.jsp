<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Bem-vindo(a)" scope="request"/>
<%@ include file="/layout/header-aluno.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-house-fill me-2"></i>Olá, ${sessionScope.usuarioLogado.nome}!</h2>
</div>

<div class="row g-3 mt-2">
    <div class="col-md-6">
        <a href="${pageContext.request.contextPath}/aluno/catalogo" class="btn btn-primary-custom w-100 p-4">
            <i class="bi bi-journal-bookmark-fill fs-3 d-block mb-2"></i>
            <strong>Ver Livros Disponíveis</strong><br>
            <small>Solicite o aluguel de um livro</small>
        </a>
    </div>
    <div class="col-md-6">
        <a href="${pageContext.request.contextPath}/aluno/alugueis" class="btn btn-outline-primary w-100 p-4">
            <i class="bi bi-bookmark-check-fill fs-3 d-block mb-2"></i>
            <strong>Meus Aluguéis</strong><br>
            <small>Acompanhe suas solicitações</small>
        </a>
    </div>
</div>

<%@ include file="/layout/footer.jsp" %>
