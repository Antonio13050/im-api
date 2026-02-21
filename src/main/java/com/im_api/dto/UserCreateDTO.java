package com.im_api.dto;

import com.im_api.validation.CpfCnpj;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 255, message = "O nome deve ter entre 2 e 255 caracteres")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    private String telefone;

    @CpfCnpj
    private String cpf;

    private LocalDate dataNascimento;

    @Builder.Default
    private boolean ativo = true;

    private Long gerenteId;

    @NotBlank(message = "O papel (role) é obrigatório")
    @Pattern(regexp = "^(ADMIN|GERENTE|CORRETOR|SECRETARIO)$", message = "Role deve ser ADMIN, GERENTE, CORRETOR ou SECRETARIO")
    private String role;

    private String creci;

    private String creciUf;

    private LocalDate creciValidade;

    private String tipoContratacao;

    private String regiaoAtuacao;

    private String especialidade;

    @DecimalMin(value = "0.00", message = "Comissão deve ser maior ou igual a zero")
    @DecimalMax(value = "100.00", message = "Comissão não pode exceder 100%")
    private BigDecimal comissaoVendaPadrao;

    @DecimalMin(value = "0.00", message = "Comissão deve ser maior ou igual a zero")
    @DecimalMax(value = "100.00", message = "Comissão não pode exceder 100%")
    private BigDecimal comissaoLocacaoPadrao;

    private String banco;

    private String agencia;

    private String conta;

    private String pix;
}
