package com.im_api.dto;

import com.im_api.model.Endereco;
import com.im_api.model.EnderecoCobranca;
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
    private String nacionalidade;
    private String rg;
    private Long corretorId;
    private String perfil;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // Endereço
    private Endereco endereco;

    // Endereço de Cobrança
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

    // Pessoa Jurídica
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
    private Boolean aceitaBoletoEmail;
    private Boolean aceitaPixVencimento;
    private String formaPagamentoPreferida;

    // Informações Financeiras
    private BigDecimal rendaMensal;
    private String banco;
    private String agencia;
    private String conta;
    private String pix;
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
