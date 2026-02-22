package com.im_api.model;

import com.im_api.model.enums.Perfil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dados Pessoais
    private String nome;
    private String email;
    private String emailAlternativo;
    private String telefone;
    private String telefoneAlternativo;
    private String cpfCnpj;
    private LocalDate dataNascimento;
    private String estadoCivil;
    private String profissao;
    private String nacionalidade;
    private String rg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Perfil perfil = Perfil.CLIENTE;

    private Long corretorId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    // Endereço
    @Embedded
    private Endereco endereco;

    // Endereço de Cobrança
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "cep", column = @Column(name = "cobranca_cep")),
        @AttributeOverride(name = "rua", column = @Column(name = "cobranca_rua")),
        @AttributeOverride(name = "numero", column = @Column(name = "cobranca_numero")),
        @AttributeOverride(name = "complemento", column = @Column(name = "cobranca_complemento")),
        @AttributeOverride(name = "bairro", column = @Column(name = "cobranca_bairro")),
        @AttributeOverride(name = "cidade", column = @Column(name = "cobranca_cidade")),
        @AttributeOverride(name = "estado", column = @Column(name = "cobranca_estado"))
    })
    private EnderecoCobranca enderecoCobranca;

    // Dados de Emprego
    private String empresaTrabalho;
    private String cargo;
    private Integer tempoEmprego;
    private BigDecimal rendaComplementar;
    private Integer numeroDependentes;

    // Dados do Cônjuge
    private String conjugeNome;
    private String conjugeCpf;
    private BigDecimal conjugeRenda;

    // Pessoa Jurídica (apenas se for CNPJ)
    private String razaoSocial;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private String nomeFantasia;
    private LocalDate dataFundacao;
    private BigDecimal faturamentoMensal;

    // Contato de Emergência
    private String contatoEmergenciaNome;
    private String contatoEmergenciaTelefone;
    private String contatoEmergenciaParentesco;

    // Preferências de Pagamento
    private Integer diaVencimentoPreferido;
    @Builder.Default
    private Boolean aceitaBoletoEmail = true;
    @Builder.Default
    private Boolean aceitaPixVencimento = false;
    private String formaPagamentoPreferida;

    // Informações Financeiras
    private BigDecimal rendaMensal;
    private String banco;
    private String agencia;
    private String conta;
    private String pix;
    private Integer scoreCredito;
    @Builder.Default
    private Boolean restricoesFinanceiras = false;
    @Column(columnDefinition = "TEXT")
    private String observacoesFinanceiras;

    // Preferências/Interesses
    @Embedded
    private Interesses interesses;

    // Observações
    @Column(columnDefinition = "TEXT")
    private String observacoes;
    @Column(columnDefinition = "TEXT")
    private String observacoesInternas;

    // Documentos
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DocumentoCliente> documentos = new ArrayList<>();
}
