package com.im_api.model;

import com.im_api.validation.CpfCnpj;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name", nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    private String senha;

    private String telefone;

    @CpfCnpj
    @Column(unique = true)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Builder.Default
    private boolean ativo = true;

    @Column(name = "gerente_id")
    private Long gerenteId;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @Column(unique = true)
    private String creci;

    @Column(name = "creci_uf", length = 2)
    private String creciUf;

    @Column(name = "creci_validade")
    private LocalDate creciValidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contratacao")
    private TipoContratacao tipoContratacao;

    @Column(name = "regiao_atuacao", columnDefinition = "TEXT")
    private String regiaoAtuacao;

    private String especialidade;

    @Column(name = "comissao_venda_padrao", precision = 5, scale = 2)
    private BigDecimal comissaoVendaPadrao;

    @Column(name = "comissao_locacao_padrao", precision = 5, scale = 2)
    private BigDecimal comissaoLocacaoPadrao;

    private String banco;

    private String agencia;

    private String conta;

    private String pix;
}
