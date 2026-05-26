<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="${aluno != null ? 'Editar Aluno' : 'Novo Aluno'}" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-people-fill me-2"></i>${aluno != null ? 'Editar Aluno' : 'Cadastrar Novo Aluno'}</h2>
    <a href="${pageContext.request.contextPath}/admin/alunos?action=listar" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left"></i> Voltar
    </a>
</div>

<div class="form-container">
    <form method="post" action="${pageContext.request.contextPath}/admin/alunos">
        <c:if test="${aluno != null}">
            <input type="hidden" name="id" value="${aluno.id}"/>
        </c:if>

        <div class="mb-3">
            <label for="nome" class="form-label">Nome *</label>
            <input type="text" class="form-control" id="nome" name="nome"
                   value="${aluno != null ? aluno.nome : ''}" required>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="email" class="form-label">E-mail</label>
                <input type="email" class="form-control" id="email" name="email"
                       value="${aluno != null ? aluno.email : ''}">
            </div>
            <div class="col-md-6 mb-3">
                <label for="telefone" class="form-label">Telefone</label>
                <input type="text" class="form-control" id="telefone" name="telefone"
                       value="${aluno != null ? aluno.telefone : ''}" placeholder="(00) 00000-0000">
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="cpf" class="form-label">CPF * <small class="text-muted">(usado no login)</small></label>
                <input type="text" class="form-control" id="cpf" name="cpf"
                       value="${aluno != null ? aluno.cpf : ''}" placeholder="000.000.000-00" required>
            </div>
            <div class="col-md-6 mb-3">
                <label for="senha" class="form-label">
                    Senha ${aluno != null ? '' : '*'}
                    <c:if test="${aluno != null}"><small class="text-muted">(deixe em branco para manter)</small></c:if>
                </label>
                <input type="password" class="form-control" id="senha" name="senha"
                       placeholder="••••••••" ${aluno != null ? '' : 'required'}>
            </div>
        </div>

        <div class="mb-4">
            <label for="endereco" class="form-label">Endereço</label>
            <input type="text" class="form-control" id="endereco" name="endereco"
                   value="${aluno != null ? aluno.endereco : ''}">
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary-custom">
                <i class="bi bi-check-lg"></i> Salvar
            </button>
            <a href="${pageContext.request.contextPath}/admin/alunos?action=listar" class="btn btn-outline-secondary">
                Cancelar
            </a>
        </div>
    </form>
</div>

<%@ include file="/layout/footer.jsp" %>
