package com.im_api.dto;
public class DocumentoImovelDTO {
    private Long id;
    private String nomeArquivo;
    private String tipoDocumento;
    private String tipoConteudo;
    private Long tamanho;
    public DocumentoImovelDTO() {}
    public DocumentoImovelDTO(Long id, String nomeArquivo, String tipoDocumento,
                              String tipoConteudo, Long tamanho) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.tipoDocumento = tipoDocumento;
        this.tipoConteudo = tipoConteudo;
        this.tamanho = tamanho;
    }
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getTipoConteudo() { return tipoConteudo; }
    public void setTipoConteudo(String tipoConteudo) { this.tipoConteudo = tipoConteudo; }

    public Long getTamanho() { return tamanho; }
    public void setTamanho(Long tamanho) { this.tamanho = tamanho; }
}