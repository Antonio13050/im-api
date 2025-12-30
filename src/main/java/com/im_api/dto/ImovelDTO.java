package com.im_api.dto;

import com.im_api.model.Endereco;
import com.im_api.model.Imovel;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class ImovelDTO {
    // Identificação
    private Long id;
    
    private String codigo;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    private String descricao;

    // Classificação
    @NotBlank(message = "O tipo do imóvel é obrigatório")
    private String tipo;

    private String subtipo;

    @NotBlank(message = "A finalidade é obrigatória")
    private String finalidade;

    private String status;
    private Boolean destaque;
    private Boolean exclusividade;
    private LocalDateTime createdDate;

    @NotNull(message = "O endereço é obrigatório")
    private Endereco endereco;

    // Áreas
    @PositiveOrZero(message = "Área total deve ser positiva")
    private Double areaTotal;

    @PositiveOrZero(message = "Área construída deve ser positiva")
    private Double areaConstruida;

    @PositiveOrZero(message = "Área útil deve ser positiva")
    private Double areaUtil;

    private Integer anoConstrucao;

    // Cômodos
    @PositiveOrZero(message = "Número de quartos deve ser positivo")
    private Integer quartos;

    @PositiveOrZero(message = "Número de suítes deve ser positivo")
    private Integer suites;

    @PositiveOrZero(message = "Número de banheiros deve ser positivo")
    private Integer banheiros;

    @PositiveOrZero(message = "Número de vagas deve ser positivo")
    private Integer vagas;

    private Integer vagasCobertas;
    private Integer andares;

    // Comodidades
    private List<String> comodidades;

    // Financeiro
    // Financeiro
    @PositiveOrZero(message = "Preço de venda deve ser positivo")
    private BigDecimal precoVenda;

    @PositiveOrZero(message = "Preço de aluguel deve ser positivo")
    private BigDecimal precoAluguel;

    private BigDecimal precoTemporada;

    @PositiveOrZero(message = "Valor do condomínio deve ser positivo")
    private BigDecimal valorCondominio;

    @PositiveOrZero(message = "Valor do IPTU deve ser positivo")
    private BigDecimal valorIptu;

    private BigDecimal valorEntrada;
    private Boolean aceitaFinanciamento;
    private Boolean aceitaFgts;
    private Boolean aceitaPermuta;
    private Boolean posseImediata;
    private BigDecimal comissaoVenda;
    private BigDecimal comissaoAluguel;

    // Documentação
    private String situacaoDocumental;
    private String observacoesInternas;

    // Mídia
    private List<FotoDTO> fotos;
    private List<VideoDTO> videos;
    private List<DocumentoImovelDTO> documentos;

    // Responsáveis
    private Long proprietarioId;
    private Long inquilinoId;
    private Long clienteId;
    private Long corretorId;

    public ImovelDTO() {}

    public ImovelDTO(Imovel imovel) {
        this.id = imovel.getId();
        this.codigo = imovel.getCodigo();
        this.titulo = imovel.getTitulo();
        this.descricao = imovel.getDescricao();
        this.tipo = imovel.getTipo();
        this.subtipo = imovel.getSubtipo();
        this.finalidade = imovel.getFinalidade();
        this.status = imovel.getStatus();
        this.destaque = imovel.getDestaque();
        this.exclusividade = imovel.getExclusividade();
        this.createdDate = imovel.getCreatedDate();
        this.endereco = imovel.getEndereco();
        this.areaTotal = imovel.getAreaTotal();
        this.areaConstruida = imovel.getAreaConstruida();
        this.areaUtil = imovel.getAreaUtil();
        this.anoConstrucao = imovel.getAnoConstrucao();
        this.quartos = imovel.getQuartos();
        this.suites = imovel.getSuites();
        this.banheiros = imovel.getBanheiros();
        this.vagas = imovel.getVagas();
        this.vagasCobertas = imovel.getVagasCobertas();
        this.andares = imovel.getAndares();
        this.comodidades = imovel.getComodidades();
        this.precoVenda = imovel.getPrecoVenda();
        this.precoAluguel = imovel.getPrecoAluguel();
        this.precoTemporada = imovel.getPrecoTemporada();
        this.valorCondominio = imovel.getValorCondominio();
        this.valorIptu = imovel.getValorIptu();
        this.valorEntrada = imovel.getValorEntrada();
        this.aceitaFinanciamento = imovel.getAceitaFinanciamento();
        this.aceitaFgts = imovel.getAceitaFgts();
        this.aceitaPermuta = imovel.getAceitaPermuta();
        this.posseImediata = imovel.getPosseImediata();
        this.comissaoVenda = imovel.getComissaoVenda();
        this.comissaoAluguel = imovel.getComissaoAluguel();
        this.situacaoDocumental = imovel.getSituacaoDocumental();
        this.observacoesInternas = imovel.getObservacoesInternas();
        this.proprietarioId = imovel.getProprietarioId();
        this.inquilinoId = imovel.getInquilinoId();
        this.clienteId = imovel.getClienteId();
        this.corretorId = imovel.getCorretorId();

        // Mapear fotos
        if (imovel.getFotos() != null) {
            this.fotos = imovel.getFotos().stream()
                    .map(foto -> new FotoDTO(
                            foto.getId(),
                            foto.getTipoConteudo(),
                            foto.getNomeArquivo(),
                            Base64.getEncoder().encodeToString(foto.getDados())
                    ))
                    .collect(Collectors.toList());
        }

        // Mapear vídeos
        if (imovel.getVideos() != null) {
            this.videos = imovel.getVideos().stream()
                    .map(video -> new VideoDTO(
                            video.getId(),
                            video.getTipoConteudo(),
                            video.getNomeArquivo(),
                            Base64.getEncoder().encodeToString(video.getDados()),
                            video.getTamanho()
                    ))
                    .collect(Collectors.toList());
        }

        // Mapear documentos
        if (imovel.getDocumentos() != null) {
            this.documentos = imovel.getDocumentos().stream()
                    .map(doc -> new DocumentoImovelDTO(
                            doc.getId(),
                            doc.getNomeArquivo(),
                            doc.getTipoDocumento(),
                            doc.getTipoConteudo(),
                            doc.getTamanho()
                    ))
                    .collect(Collectors.toList());
        }
    }

    // Getters e Setters
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

    public List<FotoDTO> getFotos() { return fotos; }
    public void setFotos(List<FotoDTO> fotos) { this.fotos = fotos; }

    public List<VideoDTO> getVideos() { return videos; }
    public void setVideos(List<VideoDTO> videos) { this.videos = videos; }

    public List<DocumentoImovelDTO> getDocumentos() { return documentos; }
    public void setDocumentos(List<DocumentoImovelDTO> documentos) { this.documentos = documentos; }

    public Long getProprietarioId() { return proprietarioId; }
    public void setProprietarioId(Long proprietarioId) { this.proprietarioId = proprietarioId; }

    public Long getInquilinoId() { return inquilinoId; }
    public void setInquilinoId(Long inquilinoId) { this.inquilinoId = inquilinoId; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getCorretorId() { return corretorId; }
    public void setCorretorId(Long corretorId) { this.corretorId = corretorId; }
}