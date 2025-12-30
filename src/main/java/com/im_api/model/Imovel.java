package com.im_api.model;
import com.im_api.dto.ImovelDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "imoveis")
public class Imovel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Identificação
    @Column(unique = true)
    private String codigo;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;
    // Classificação
    private String tipo;
    private String subtipo;
    private String finalidade;
    private String status;
    private Boolean destaque = false;
    private Boolean exclusividade = false;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;
    // Endereço
    @Embedded
    private Endereco endereco;
    // Áreas
    private Double areaTotal;
    private Double areaConstruida;
    private Double areaUtil;
    private Integer anoConstrucao;
    // Cômodos
    private Integer quartos;
    private Integer suites;
    private Integer banheiros;
    private Integer vagas;
    private Integer vagasCobertas;
    private Integer andares;
    // Comodidades
    @ElementCollection
    @CollectionTable(name = "imovel_comodidades", joinColumns = @JoinColumn(name = "imovel_id"))
    @Column(name = "comodidade")
    private List<String> comodidades = new ArrayList<>();
    // Financeiro
    // Financeiro
    private BigDecimal precoVenda;
    private BigDecimal precoAluguel;
    private BigDecimal precoTemporada;
    private BigDecimal valorCondominio;
    private BigDecimal valorIptu;
    private BigDecimal valorEntrada;
    private Boolean aceitaFinanciamento = false;
    private Boolean aceitaFgts = false;
    private Boolean aceitaPermuta = false;
    private Boolean posseImediata = true;
    private BigDecimal comissaoVenda;
    private BigDecimal comissaoAluguel;
    // Documentação
    private String situacaoDocumental;

    @Column(columnDefinition = "TEXT")
    private String observacoesInternas;
    // Mídia
    @OneToMany(mappedBy = "imovel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos = new ArrayList<>();
    @OneToMany(mappedBy = "imovel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos = new ArrayList<>();
    @OneToMany(mappedBy = "imovel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentoImovel> documentos = new ArrayList<>();
    // Responsáveis
    private Long proprietarioId;
    private Long inquilinoId;
    private Long clienteId;
    private Long corretorId;
    public Imovel() {
    }
    public Imovel(ImovelDTO dto) {
        this.codigo = dto.getCodigo();
        this.titulo = dto.getTitulo();
        this.descricao = dto.getDescricao();
        this.tipo = dto.getTipo();
        this.subtipo = dto.getSubtipo();
        this.finalidade = dto.getFinalidade();
        this.status = dto.getStatus();
        this.destaque = dto.getDestaque();
        this.exclusividade = dto.getExclusividade();
        this.endereco = dto.getEndereco();
        this.areaTotal = dto.getAreaTotal();
        this.areaConstruida = dto.getAreaConstruida();
        this.areaUtil = dto.getAreaUtil();
        this.anoConstrucao = dto.getAnoConstrucao();
        this.quartos = dto.getQuartos();
        this.suites = dto.getSuites();
        this.banheiros = dto.getBanheiros();
        this.vagas = dto.getVagas();
        this.vagasCobertas = dto.getVagasCobertas();
        this.andares = dto.getAndares();
        this.comodidades = dto.getComodidades() != null ? dto.getComodidades() : new ArrayList<>();
        this.precoVenda = dto.getPrecoVenda();
        this.precoAluguel = dto.getPrecoAluguel();
        this.precoTemporada = dto.getPrecoTemporada();
        this.valorCondominio = dto.getValorCondominio();
        this.valorIptu = dto.getValorIptu();
        this.valorEntrada = dto.getValorEntrada();
        this.aceitaFinanciamento = dto.getAceitaFinanciamento();
        this.aceitaFgts = dto.getAceitaFgts();
        this.aceitaPermuta = dto.getAceitaPermuta();
        this.posseImediata = dto.getPosseImediata();
        this.comissaoVenda = dto.getComissaoVenda();
        this.comissaoAluguel = dto.getComissaoAluguel();
        this.situacaoDocumental = dto.getSituacaoDocumental();
        this.observacoesInternas = dto.getObservacoesInternas();
        this.proprietarioId = dto.getProprietarioId();
        this.inquilinoId = dto.getInquilinoId();
        this.clienteId = dto.getClienteId();
        this.corretorId = dto.getCorretorId();
    }
    // Getters e Setters para TODOS os campos...
    // (Gere automaticamente na sua IDE)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getSubtipo() { return subtipo; }
    public void setSubtipo(String subtipo) { this.subtipo = subtipo; }

    public String getFinalidade() { return finalidade; }
    public void setFinalidade(String finalidade) { this.finalidade = finalidade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getDestaque() { return destaque; }
    public void setDestaque(Boolean destaque) { this.destaque = destaque; }

    public Boolean getExclusividade() { return exclusividade; }
    public void setExclusividade(Boolean exclusividade) { this.exclusividade = exclusividade; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    public Double getAreaTotal() { return areaTotal; }
    public void setAreaTotal(Double areaTotal) { this.areaTotal = areaTotal; }

    public Double getAreaConstruida() { return areaConstruida; }
    public void setAreaConstruida(Double areaConstruida) { this.areaConstruida = areaConstruida; }

    public Double getAreaUtil() { return areaUtil; }
    public void setAreaUtil(Double areaUtil) { this.areaUtil = areaUtil; }

    public Integer getAnoConstrucao() { return anoConstrucao; }
    public void setAnoConstrucao(Integer anoConstrucao) { this.anoConstrucao = anoConstrucao; }

    public Integer getQuartos() { return quartos; }
    public void setQuartos(Integer quartos) { this.quartos = quartos; }

    public Integer getSuites() { return suites; }
    public void setSuites(Integer suites) { this.suites = suites; }

    public Integer getBanheiros() { return banheiros; }
    public void setBanheiros(Integer banheiros) { this.banheiros = banheiros; }

    public Integer getVagas() { return vagas; }
    public void setVagas(Integer vagas) { this.vagas = vagas; }

    public Integer getVagasCobertas() { return vagasCobertas; }
    public void setVagasCobertas(Integer vagasCobertas) { this.vagasCobertas = vagasCobertas; }

    public Integer getAndares() { return andares; }
    public void setAndares(Integer andares) { this.andares = andares; }

    public List<String> getComodidades() { return comodidades; }
    public void setComodidades(List<String> comodidades) { this.comodidades = comodidades; }

    public BigDecimal getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(BigDecimal precoVenda) { this.precoVenda = precoVenda; }

    public BigDecimal getPrecoAluguel() { return precoAluguel; }
    public void setPrecoAluguel(BigDecimal precoAluguel) { this.precoAluguel = precoAluguel; }

    public BigDecimal getPrecoTemporada() { return precoTemporada; }
    public void setPrecoTemporada(BigDecimal precoTemporada) { this.precoTemporada = precoTemporada; }

    public BigDecimal getValorCondominio() { return valorCondominio; }
    public void setValorCondominio(BigDecimal valorCondominio) { this.valorCondominio = valorCondominio; }

    public BigDecimal getValorIptu() { return valorIptu; }
    public void setValorIptu(BigDecimal valorIptu) { this.valorIptu = valorIptu; }

    public BigDecimal getValorEntrada() { return valorEntrada; }
    public void setValorEntrada(BigDecimal valorEntrada) { this.valorEntrada = valorEntrada; }

    public Boolean getAceitaFinanciamento() { return aceitaFinanciamento; }
    public void setAceitaFinanciamento(Boolean aceitaFinanciamento) { this.aceitaFinanciamento = aceitaFinanciamento; }

    public Boolean getAceitaFgts() { return aceitaFgts; }
    public void setAceitaFgts(Boolean aceitaFgts) { this.aceitaFgts = aceitaFgts; }

    public Boolean getAceitaPermuta() { return aceitaPermuta; }
    public void setAceitaPermuta(Boolean aceitaPermuta) { this.aceitaPermuta = aceitaPermuta; }

    public Boolean getPosseImediata() { return posseImediata; }
    public void setPosseImediata(Boolean posseImediata) { this.posseImediata = posseImediata; }

    public BigDecimal getComissaoVenda() { return comissaoVenda; }
    public void setComissaoVenda(BigDecimal comissaoVenda) { this.comissaoVenda = comissaoVenda; }

    public BigDecimal getComissaoAluguel() { return comissaoAluguel; }
    public void setComissaoAluguel(BigDecimal comissaoAluguel) { this.comissaoAluguel = comissaoAluguel; }

    public String getSituacaoDocumental() { return situacaoDocumental; }
    public void setSituacaoDocumental(String situacaoDocumental) { this.situacaoDocumental = situacaoDocumental; }

    public String getObservacoesInternas() { return observacoesInternas; }
    public void setObservacoesInternas(String observacoesInternas) { this.observacoesInternas = observacoesInternas; }

    public List<Foto> getFotos() { return fotos; }
    public void setFotos(List<Foto> fotos) { this.fotos = fotos; }

    public List<Video> getVideos() { return videos; }
    public void setVideos(List<Video> videos) { this.videos = videos; }

    public List<DocumentoImovel> getDocumentos() { return documentos; }
    public void setDocumentos(List<DocumentoImovel> documentos) { this.documentos = documentos; }

    public Long getProprietarioId() { return proprietarioId; }
    public void setProprietarioId(Long proprietarioId) { this.proprietarioId = proprietarioId; }

    public Long getInquilinoId() { return inquilinoId; }
    public void setInquilinoId(Long inquilinoId) { this.inquilinoId = inquilinoId; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getCorretorId() { return corretorId; }
    public void setCorretorId(Long corretorId) { this.corretorId = corretorId; }
}