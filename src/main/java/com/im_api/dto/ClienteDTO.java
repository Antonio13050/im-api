package com.im_api.dto;

import com.im_api.model.Cliente;
import com.im_api.model.Endereco;
import com.im_api.model.Interesses;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {

    private Long id;

    // Dados Pessoais
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 255, message = "O nome deve ter entre 2 e 255 caracteres")
    private String nome;

    @Email(message = "Email inválido")
    private String email;

    @Email(message = "Email alternativo inválido")
    private String emailAlternativo;

    private String telefone;
    private String telefoneAlternativo;

    //@Pattern(regexp = "^\\d{11}$|^\\d{14}$", message = "CPF/CNPJ deve conter 11 ou 14 dígitos numéricos")
    private String cpfCnpj;

    private LocalDate dataNascimento;
    private String estadoCivil;
    private String profissao;
    private Long corretorId;

    @NotNull(message = "O perfil é obrigatório")
    private String perfil;

    private LocalDateTime createdDate;

    // Endereço
    private Endereco endereco;

    // Informações Financeiras
    @PositiveOrZero(message = "Renda mensal deve ser positiva")
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
