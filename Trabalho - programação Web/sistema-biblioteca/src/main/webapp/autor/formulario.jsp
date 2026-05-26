<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="${autor != null ? 'Editar Autor' : 'Novo Autor'}" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-pen-fill me-2"></i>${autor != null ? 'Editar Autor' : 'Cadastrar Novo Autor'}</h2>
    <a href="${pageContext.request.contextPath}/admin/autores?action=listar" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left"></i> Voltar
    </a>
</div>

<div class="form-container">
    <form method="post" action="${pageContext.request.contextPath}/admin/autores">
        <c:if test="${autor != null}">
            <input type="hidden" name="id" value="${autor.id}"/>
        </c:if>

        <div class="mb-3">
            <label for="nome" class="form-label">Nome *</label>
            <input type="text" class="form-control" id="nome" name="nome"
                   value="${autor != null ? autor.nome : ''}" required>
        </div>

        <div class="mb-3">
            <label for="nacionalidade" class="form-label">Nacionalidade</label>
            <input type="text" class="form-control" id="nacionalidade" name="nacionalidade"
                   value="${autor != null ? autor.nacionalidade : ''}">
        </div>

        <div class="mb-4">
            <label for="dataNascimento" class="form-label">Data de Nascimento</label>
            <input type="date" class="form-control" id="dataNascimento" name="dataNascimento"
                   value="${autor != null ? autor.dataNascimento : ''}">
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary-custom">
                <i class="bi bi-check-lg"></i> Salvar
            </button>
            <a href="${pageContext.request.contextPath}/admin/autores?action=listar" class="btn btn-outline-secondary">
                Cancelar
            </a>
        </div>
    </form>
</div>

<%@ include file="/layout/footer.jsp" %>
