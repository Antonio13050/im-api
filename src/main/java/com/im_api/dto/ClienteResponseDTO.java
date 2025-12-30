package com.im_api.dto;

import com.im_api.model.Endereco;
import com.im_api.model.Interesses;
import lombok.*;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponseDTO {

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
    private Long corretorId;
    private String perfil;

    private LocalDateTime createdDate;

    // Endereço
    private Endereco endereco;

    // Informações Financeiras
    private BigDecimal rendaMensal;
    private String banco;
    private String agencia;
    private String conta;
    private Integer scoreCredito;
    private Boolean restricoesFinanceiras;
    private String observacoesFinanceiras;

    // Preferências/Interesses
    private Interesses interesses;

    // Observações
    private String observacoes;
    private String observacoesInternas;

    // Documentos
    private List<DocumentoClienteDTO> documentos;
}
