package com.im_api.model;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
@Entity
@Table(name = "documentos_imovel")
public class DocumentoImovel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_arquivo")
    private String nomeArquivo;
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    @Lob
    @Column(name = "dados", nullable = false)
    private byte[] dados;
    @Column(name = "tipo_conteudo")
    private String tipoConteudo;
    @Column(name = "tamanho")
    private Long tamanho;
    @CreationTimestamp
    @Column(name = "data_upload", updatable = false)
    private LocalDateTime dataUpload;
    @ManyToOne
    @JoinColumn(name = "imovel_id")
    private Imovel imovel;
    public DocumentoImovel() {}
    public DocumentoImovel(String nomeArquivo, String tipoDocumento, byte[] dados,
                           String tipoConteudo, Long tamanho, Imovel imovel) {
        this.nomeArquivo = nomeArquivo;
        this.tipoDocumento = tipoDocumento;
        this.dados = dados;
        this.tipoConteudo = tipoConteudo;
        this.tamanho = tamanho;
        this.imovel = imovel;
    }
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public byte[] getDados() { return dados; }
    public void setDados(byte[] dados) { this.dados = dados; }

    public String getTipoConteudo() { return tipoConteudo; }
    public void setTipoConteudo(String tipoConteudo) { this.tipoConteudo = tipoConteudo; }

    public Long getTamanho() { return tamanho; }
    public void setTamanho(Long tamanho) { this.tamanho = tamanho; }

    public LocalDateTime getDataUpload() { return dataUpload; }
    public void setDataUpload(LocalDateTime dataUpload) { this.dataUpload = dataUpload; }

    public Imovel getImovel() { return imovel; }
    public void setImovel(Imovel imovel) { this.imovel = imovel; }
}