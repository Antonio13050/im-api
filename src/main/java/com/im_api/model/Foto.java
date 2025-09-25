package com.im_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fotos")
public class Foto {

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

    @ManyToOne
    @JoinColumn(name = "imovel_id")
    private Imovel imovel;

    public Foto() {
    }

    public Foto(String nomeArquivo, byte[] dados, String tipoConteudo, Imovel imovel) {
        this.dados = dados;
        this.nomeArquivo = nomeArquivo;
        this.tipoConteudo = tipoConteudo;
        this.imovel = imovel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDados() {
        return dados;
    }

    public void setDados(byte[] dados) {
        this.dados = dados;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getTipoConteudo() {
        return tipoConteudo;
    }

    public void setTipoConteudo(String tipoConteudo) {
        this.tipoConteudo = tipoConteudo;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
}
