package com.biblioteca.model;

import java.time.LocalDateTime;

public class TokenLogin {
    private int id;
    private String token;
    private int usuarioId;
    private String tipoUsuario; // "admin" | "aluno"
    private LocalDateTime dataExpiracao;

    public TokenLogin() {}

    public TokenLogin(String token, int usuarioId, String tipoUsuario, LocalDateTime dataExpiracao) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.tipoUsuario = tipoUsuario;
        this.dataExpiracao = dataExpiracao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public LocalDateTime getDataExpiracao() { return dataExpiracao; }
    public void setDataExpiracao(LocalDateTime dataExpiracao) { this.dataExpiracao = dataExpiracao; }
}
