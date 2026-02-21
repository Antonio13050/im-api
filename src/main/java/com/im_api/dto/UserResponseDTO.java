package com.im_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long userId;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private LocalDate dataNascimento;
    private boolean ativo;
    private Long gerenteId;
    private Set<String> roles;
    
    private String creci;
    private String creciUf;
    private LocalDate creciValidade;
    private String tipoContratacao;
    private String regiaoAtuacao;
    private String especialidade;
    private BigDecimal comissaoVendaPadrao;
    private BigDecimal comissaoLocacaoPadrao;
    private String banco;
    private String agencia;
    private String conta;
    private String pix;
    
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
