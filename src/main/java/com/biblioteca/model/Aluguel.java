package com.biblioteca.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Aluguel {
    private int id;
    private int alunoId;
    private int livroId;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataAprovacao;
    private LocalDateTime dataFinalizacao;
    private String status; // Solicitado | Concluído | Finalizado | Recusada

    // Campos auxiliares para exibição
    private String alunoNome;
    private String livroTitulo;
    private String autorNome;

    public Aluguel() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }

    public int getLivroId() { return livroId; }
    public void setLivroId(int livroId) { this.livroId = livroId; }

    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }

    public LocalDateTime getDataAprovacao() { return dataAprovacao; }
    public void setDataAprovacao(LocalDateTime dataAprovacao) { this.dataAprovacao = dataAprovacao; }

    public LocalDateTime getDataFinalizacao() { return dataFinalizacao; }
    public void setDataFinalizacao(LocalDateTime dataFinalizacao) { this.dataFinalizacao = dataFinalizacao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAlunoNome() { return alunoNome; }
    public void setAlunoNome(String alunoNome) { this.alunoNome = alunoNome; }

    public String getLivroTitulo() { return livroTitulo; }
    public void setLivroTitulo(String livroTitulo) { this.livroTitulo = livroTitulo; }

    public String getAutorNome() { return autorNome; }
    public void setAutorNome(String autorNome) { this.autorNome = autorNome; }

    public String getDataSolicitacaoFormatada() {
        if (dataSolicitacao == null) return "—";
        return dataSolicitacao.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
