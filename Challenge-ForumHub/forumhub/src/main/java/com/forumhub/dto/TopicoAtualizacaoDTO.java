package com.forumhub.dto;

import jakarta.validation.constraints.NotBlank;

public class TopicoAtualizacaoDTO {

    @NotBlank
    private String titulo;

    @NotBlank
    private String mensagem;

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
