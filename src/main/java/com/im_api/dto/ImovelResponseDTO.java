package com.im_api.dto;

import com.im_api.model.Endereco;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImovelResponseDTO {
    // Identificação
    private Long id;
    private String codigo;
    private String titulo;
    private String descricao;

    // Classificação
    private String tipo;
    private String subtipo;
    private String finalidade;
    private String status;
    private Boolean destaque;
    private Boolean exclusividade;
    private LocalDateTime createdDate;

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
    private List<String> comodidades;

    // Financeiro
    private BigDecimal precoVenda;
    private BigDecimal precoAluguel;
    private BigDecimal precoTemporada;
    private BigDecimal valorCondominio;
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
}
