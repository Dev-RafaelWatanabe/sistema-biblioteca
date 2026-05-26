<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Aluno - Sistema Biblioteca</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
    <div class="login-page">
        <div class="login-card">
            <div class="login-icon">
                <i class="bi bi-mortarboard"></i>
            </div>
            <h1>Acesso do Aluno</h1>
            <p class="login-subtitle">Entre com CPF e senha</p>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>${erro}
                </div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/loginAluno">
                <div class="mb-3">
                    <label for="cpf" class="form-label fw-semibold">CPF</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-person-vcard"></i></span>
                        <input type="text" class="form-control" id="cpf" name="cpf"
                               placeholder="000.000.000-00" value="${cpf}" required autofocus>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="senha" class="form-label fw-semibold">Senha</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-lock"></i></span>
                        <input type="password" class="form-control" id="senha" name="senha"
                               placeholder="••••••••" required>
                    </div>
                </div>
                <div class="form-check mb-4">
                    <input class="form-check-input" type="checkbox" id="manter" name="manter">
                    <label class="form-check-label" for="manter">Manter conectado por 7 dias</label>
                </div>
                <button type="submit" class="btn-login">
                    <i class="bi bi-box-arrow-in-right me-2"></i>Entrar
                </button>
            </form>

            <div class="text-center mt-4">
                <small class="text-muted">Aluno de teste: 111.222.333-44 / aluno123</small><br>
                <a href="${pageContext.request.contextPath}/" class="small">&larr; Voltar</a>
            </div>
        </div>
    </div>
</body>
</html>
