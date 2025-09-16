package com.im_api.dto;

public class FotoDTO {

    private Long id;
    private String nomeArquivo;
    private String base64; // Dados da imagem em Base64
    private String tipoConteudo;

    public FotoDTO() {
    }

    public FotoDTO(Long id, String tipoConteudo, String nomeArquivo) {
        this.id = id;
        this.tipoConteudo = tipoConteudo;
        this.nomeArquivo = nomeArquivo;
    }

    public FotoDTO(String nomeArquivo, String base64, String tipoConteudo) {
        this.nomeArquivo = nomeArquivo;
        this.base64 = base64;
        this.tipoConteudo = tipoConteudo;
    }

    public FotoDTO(Long id, String tipoConteudo, String nomeArquivo, String base64) {
        this.id = id;
        this.tipoConteudo = tipoConteudo;
        this.nomeArquivo = nomeArquivo;
        this.base64 = base64;
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
}
