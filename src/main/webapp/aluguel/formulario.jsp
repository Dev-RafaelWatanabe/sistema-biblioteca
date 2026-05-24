<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Nova Solicitação" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-arrow-left-right me-2"></i>Criar Solicitação Manual</h2>
    <a href="${pageContext.request.contextPath}/admin/alugueis?action=listar" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left"></i> Voltar
    </a>
</div>

<div class="form-container">
    <form method="post" action="${pageContext.request.contextPath}/admin/alugueis">
        <div class="mb-3">
            <label for="alunoId" class="form-label">Aluno *</label>
            <select class="form-select" id="alunoId" name="alunoId" required>
                <option value="">Selecione um aluno...</option>
                <c:forEach var="aluno" items="${alunos}">
                    <option value="${aluno.id}">${aluno.nome} - ${aluno.cpf}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-4">
            <label for="livroId" class="form-label">Livro *</label>
            <select class="form-select" id="livroId" name="livroId" required>
                <option value="">Selecione um livro disponível...</option>
                <c:forEach var="livro" items="${livros}">
                    <option value="${livro.id}">${livro.titulo}</option>
                </c:forEach>
            </select>
        </div>

        <div class="alert alert-info">
            <i class="bi bi-info-circle me-2"></i>
            A solicitação será criada com status <strong>Solicitado</strong>. Use o botão "Aprovar" na lista para concluir o aluguel.
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary-custom">
                <i class="bi bi-check-lg"></i> Criar Solicitação
            </button>
            <a href="${pageContext.request.contextPath}/admin/alugueis?action=listar" class="btn btn-outline-secondary">
                Cancelar
            </a>
        </div>
    </form>
</div>

<%@ include file="/layout/footer.jsp" %>
