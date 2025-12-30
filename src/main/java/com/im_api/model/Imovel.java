package com.im_api.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Builder.Default
    private Boolean destaque = false;
    @Builder.Default
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
    @Builder.Default
    private List<String> comodidades = new ArrayList<>();
    // Financeiro
    // Financeiro
    private BigDecimal precoVenda;
    private BigDecimal precoAluguel;
    private BigDecimal precoTemporada;
    private BigDecimal valorCondominio;
    private BigDecimal valorIptu;
    private BigDecimal valorEntrada;
    @Builder.Default
    private Boolean aceitaFinanciamento = false;
    @Builder.Default
    private Boolean aceitaFgts = false;
    @Builder.Default
    private Boolean aceitaPermuta = false;
    @Builder.Default
    private Boolean posseImediata = true;
    private BigDecimal comissaoVenda;
    private BigDecimal comissaoAluguel;
    // Documentação
    private String situacaoDocumental;

    @Column(columnDefinition = "TEXT")
    private String observacoesInternas;
    // Mídia
    @OneToMany(mappedBy = "imovel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Foto> fotos = new ArrayList<>();
    @OneToMany(mappedBy = "imovel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Video> videos = new ArrayList<>();
    @OneToMany(mappedBy = "imovel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DocumentoImovel> documentos = new ArrayList<>();
    // Responsáveis
    private Long proprietarioId;
    private Long inquilinoId;
    private Long clienteId;
    private Long corretorId;
}