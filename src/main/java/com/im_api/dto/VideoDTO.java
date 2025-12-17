package com.im_api.dto;

public class VideoDTO {

    private Long id;
    private String nomeArquivo;
    private String base64;
    private String tipoConteudo;
    private Long tamanho;

    public VideoDTO() {
    }

    public VideoDTO(Long id, String tipoConteudo, String nomeArquivo, Long tamanho) {
        this.id = id;
        this.tipoConteudo = tipoConteudo;
        this.nomeArquivo = nomeArquivo;
        this.tamanho = tamanho;
    }

    public VideoDTO(Long id, String tipoConteudo, String nomeArquivo, String base64, Long tamanho) {
        this.id = id;
        this.tipoConteudo = tipoConteudo;
        this.nomeArquivo = nomeArquivo;
        this.base64 = base64;
        this.tamanho = tamanho;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getTipoConteudo() {
        return tipoConteudo;
    }

    public void setTipoConteudo(String tipoConteudo) {
        this.tipoConteudo = tipoConteudo;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }
}