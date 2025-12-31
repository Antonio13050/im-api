package com.im_api.dto;

import com.im_api.model.Endereco;
import com.im_api.model.Interesses;
import com.im_api.validation.CpfCnpj;
import com.im_api.validation.ValidPerfil;
import com.im_api.validation.ValidTelefone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequestDTO {

    // Dados Pessoais
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 255, message = "O nome deve ter entre 2 e 255 caracteres")
    private String nome;

    @Email(message = "Email inválido")
    @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
    private String email;

    @Email(message = "Email alternativo inválido")
    @Size(max = 255, message = "O email alternativo deve ter no máximo 255 caracteres")
    private String emailAlternativo;

    @ValidTelefone
    private String telefone;

    @ValidTelefone
    private String telefoneAlternativo;

    @CpfCnpj
    private String cpfCnpj;

    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    @Size(max = 50, message = "O estado civil deve ter no máximo 50 caracteres")
    private String estadoCivil;

    @Size(max = 100, message = "A profissão deve ter no máximo 100 caracteres")
    private String profissao;

    @Positive(message = "O ID do corretor deve ser positivo")
    private Long corretorId;

    @NotNull(message = "O perfil é obrigatório")
    @ValidPerfil
    private String perfil;

    // Endereço - Validação cascata usando @Valid
    @Valid
    @NotNull(message = "O endereço é obrigatório")
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
}
