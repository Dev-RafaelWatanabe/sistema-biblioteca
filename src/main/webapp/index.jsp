<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <div class="login-page">
        <div class="login-card" style="max-width: 560px;">
            <div class="login-icon">
                <i class="bi bi-book-half"></i>
            </div>
            <h1>Sistema Biblioteca</h1>
            <p class="login-subtitle">Escolha como deseja acessar o sistema</p>

            <div class="d-grid gap-3 mt-4">
                <a href="${pageContext.request.contextPath}/loginAdmin" class="btn btn-primary-custom btn-role">
                    <i class="bi bi-person-badge me-2"></i> Sou Bibliotecário
                </a>
                <a href="${pageContext.request.contextPath}/loginAluno" class="btn btn-primary-custom btn-role">
                    <i class="bi bi-mortarboard me-2"></i> Sou Aluno
                </a>
            </div>
        </div>
    </div>
</body>
</html>
