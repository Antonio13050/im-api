package com.im_api.dto;

import com.im_api.model.Endereco;
import com.im_api.model.EnderecoCobranca;
import com.im_api.model.Interesses;
import com.im_api.validation.CpfCnpj;
import com.im_api.validation.ValidPerfil;
import com.im_api.validation.ValidTelefone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.math.BigDecimal;

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

    @Size(max = 50, message = "A nacionalidade deve ter no máximo 50 caracteres")
    private String nacionalidade;

    @Size(max = 20, message = "O RG deve ter no máximo 20 caracteres")
    private String rg;

    @Positive(message = "O ID do corretor deve ser positivo")
    private Long corretorId;

    @NotNull(message = "O perfil é obrigatório")
    @ValidPerfil
    private String perfil;

    // Endereço - Validação cascata usando @Valid
    @Valid
    @NotNull(message = "O endereço é obrigatório")
    private Endereco endereco;

    // Endereço de Cobrança
    @Valid
    private EnderecoCobranca enderecoCobranca;

    // Dados de Emprego
    @Size(max = 255, message = "A empresa deve ter no máximo 255 caracteres")
    private String empresaTrabalho;

    @Size(max = 100, message = "O cargo deve ter no máximo 100 caracteres")
    private String cargo;

    @Min(value = 0, message = "O tempo de emprego deve ser positivo")
    private Integer tempoEmprego;

    @PositiveOrZero(message = "A renda complementar deve ser positiva")
    private BigDecimal rendaComplementar;

    @Min(value = 0, message = "O número de dependentes deve ser positivo")
    private Integer numeroDependentes;

    // Dados do Cônjuge
    @Size(max = 255, message = "O nome do cônjuge deve ter no máximo 255 caracteres")
    private String conjugeNome;

    @Size(max = 20, message = "O CPF do cônjuge deve ter no máximo 20 caracteres")
    private String conjugeCpf;

    @PositiveOrZero(message = "A renda do cônjuge deve ser positiva")
    private BigDecimal conjugeRenda;

    // Pessoa Jurídica (apenas se for CNPJ)
    @Size(max = 255, message = "A razão social deve ter no máximo 255 caracteres")
    private String razaoSocial;

    @Size(max = 50, message = "A inscrição estadual deve ter no máximo 50 caracteres")
    private String inscricaoEstadual;

    @Size(max = 50, message = "A inscrição municipal deve ter no máximo 50 caracteres")
    private String inscricaoMunicipal;

    @Size(max = 255, message = "O nome fantasia deve ter no máximo 255 caracteres")
    private String nomeFantasia;

    @Past(message = "A data de fundação deve ser no passado")
    private LocalDate dataFundacao;

    @PositiveOrZero(message = "O faturamento mensal deve ser positivo")
    private BigDecimal faturamentoMensal;

    // Contato de Emergência
    @Size(max = 255, message = "O nome do contato deve ter no máximo 255 caracteres")
    private String contatoEmergenciaNome;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String contatoEmergenciaTelefone;

    @Size(max = 50, message = "O parentesco deve ter no máximo 50 caracteres")
    private String contatoEmergenciaParentesco;

    // Preferências de Pagamento
    @Min(value = 1, message = "O dia de vencimento deve ser entre 1 e 31")
    @Max(value = 31, message = "O dia de vencimento deve ser entre 1 e 31")
    private Integer diaVencimentoPreferido;

    private Boolean aceitaBoletoEmail;
    private Boolean aceitaPixVencimento;

    @Size(max = 30, message = "A forma de pagamento deve ter no máximo 30 caracteres")
    private String formaPagamentoPreferida;

    // Informações Financeiras
    @PositiveOrZero(message = "Renda mensal deve ser positiva")
    private BigDecimal rendaMensal;
    private String banco;
    private String agencia;
    private String conta;
    @Size(max = 100, message = "O PIX deve ter no máximo 100 caracteres")
    private String pix;
    private Integer scoreCredito;
    private Boolean restricoesFinanceiras;
    private String observacoesFinanceiras;

    // Preferências/Interesses
    private Interesses interesses;

    // Observações
    private String observacoes;
    private String observacoesInternas;
}
