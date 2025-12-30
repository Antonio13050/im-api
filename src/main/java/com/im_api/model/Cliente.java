package com.im_api.model;

import com.im_api.model.enums.Perfil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Perfil perfil = Perfil.CLIENTE;

    private Long corretorId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    // Endereço
    @Embedded
    private Endereco endereco;

    // Informações Financeiras
    private BigDecimal rendaMensal;
    private String banco;
    private String agencia;
    private String conta;
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

