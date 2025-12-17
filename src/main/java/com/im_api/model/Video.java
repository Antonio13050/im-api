package com.im_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    @Lob
    @Column(name = "dados", nullable = false)
    private byte[] dados;

    @Column(name = "tipo_conteudo")
    private String tipoConteudo;

    @Column(name = "tamanho")
    private Long tamanho;

    @ManyToOne
    @JoinColumn(name = "imovel_id")
    private Imovel imovel;

    public Video() {
    }

    public Video(String nomeArquivo, byte[] dados, String tipoConteudo, Long tamanho, Imovel imovel) {
        this.nomeArquivo = nomeArquivo;
        this.dados = dados;
        this.tipoConteudo = tipoConteudo;
        this.tamanho = tamanho;
        this.imovel = imovel;
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

    public byte[] getDados() {
        return dados;
    }

    public void setDados(byte[] dados) {
        this.dados = dados;
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

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
}