package com.im_api.dto;

import com.im_api.model.Endereco;
import com.im_api.model.Imovel;
import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}