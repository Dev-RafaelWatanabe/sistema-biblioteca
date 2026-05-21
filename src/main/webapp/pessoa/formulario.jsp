<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="${pessoa != null ? 'Editar Pessoa' : 'Nova Pessoa'}" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-people-fill me-2"></i>${pessoa != null ? 'Editar Pessoa' : 'Cadastrar Nova Pessoa'}</h2>
    <a href="${pageContext.request.contextPath}/pessoas?action=listar" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left"></i> Voltar
    </a>
</div>

<div class="form-container">
    <form method="post" action="${pageContext.request.contextPath}/pessoas">
        <c:if test="${pessoa != null}">
            <input type="hidden" name="id" value="${pessoa.id}"/>
        </c:if>

        <div class="mb-3">
            <label for="nome" class="form-label">Nome *</label>
            <input type="text" class="form-control" id="nome" name="nome"
                   value="${pessoa != null ? pessoa.nome : ''}" required>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="email" class="form-label">E-mail</label>
                <input type="email" class="form-control" id="email" name="email"
                       value="${pessoa != null ? pessoa.email : ''}">
            </div>
            <div class="col-md-6 mb-3">
                <label for="telefone" class="form-label">Telefone</label>
                <input type="text" class="form-control" id="telefone" name="telefone"
                       value="${pessoa != null ? pessoa.telefone : ''}" placeholder="(00) 00000-0000">
            </div>
        </div>

        <div class="mb-3">
            <label for="cpf" class="form-label">CPF</label>
            <input type="text" class="form-control" id="cpf" name="cpf"
                   value="${pessoa != null ? pessoa.cpf : ''}" placeholder="000.000.000-00">
        </div>

        <div class="mb-4">
            <label for="endereco" class="form-label">Endereço</label>
            <input type="text" class="form-control" id="endereco" name="endereco"
                   value="${pessoa != null ? pessoa.endereco : ''}">
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary-custom">
                <i class="bi bi-check-lg"></i> Salvar
            </button>
            <a href="${pageContext.request.contextPath}/pessoas?action=listar" class="btn btn-outline-secondary">
                Cancelar
            </a>
        </div>
    </form>
</div>

<%@ include file="/layout/footer.jsp" %>
