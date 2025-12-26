package com.im_api.dto;

import com.im_api.model.Cliente;
import com.im_api.model.Endereco;
import com.im_api.model.Interesses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteDTO {

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
    private Double rendaMensal;
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

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.emailAlternativo = cliente.getEmailAlternativo();
        this.telefone = cliente.getTelefone();
        this.telefoneAlternativo = cliente.getTelefoneAlternativo();
        this.cpfCnpj = cliente.getCpfCnpj();
        this.dataNascimento = cliente.getDataNascimento();
        this.estadoCivil = cliente.getEstadoCivil();
        this.profissao = cliente.getProfissao();
        this.corretorId = cliente.getCorretorId();
        this.perfil = cliente.getPerfil() != null ? cliente.getPerfil().name() : null;
        this.createdDate = cliente.getCreatedDate();
        this.endereco = cliente.getEndereco();
        this.rendaMensal = cliente.getRendaMensal();
        this.banco = cliente.getBanco();
        this.agencia = cliente.getAgencia();
        this.conta = cliente.getConta();
        this.scoreCredito = cliente.getScoreCredito();
        this.restricoesFinanceiras = cliente.getRestricoesFinanceiras();
        this.observacoesFinanceiras = cliente.getObservacoesFinanceiras();
        this.interesses = cliente.getInteresses();
        this.observacoes = cliente.getObservacoes();
        this.observacoesInternas = cliente.getObservacoesInternas();

        // Mapear documentos
        this.documentos = cliente.getDocumentos().stream()
                .map(doc -> new DocumentoClienteDTO(
                        doc.getId(),
                        doc.getNomeArquivo(),
                        doc.getTipoDocumento(),
                        doc.getTipoConteudo(),
                        doc.getTamanho()
                ))
                .collect(Collectors.toList());
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailAlternativo() {
        return emailAlternativo;
    }

    public void setEmailAlternativo(String emailAlternativo) {
        this.emailAlternativo = emailAlternativo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefoneAlternativo() {
        return telefoneAlternativo;
    }

    public void setTelefoneAlternativo(String telefoneAlternativo) {
        this.telefoneAlternativo = telefoneAlternativo;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Long getCorretorId() {
        return corretorId;
    }

    public void setCorretorId(Long corretorId) {
        this.corretorId = corretorId;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Double getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(Double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public Integer getScoreCredito() {
        return scoreCredito;
    }

    public void setScoreCredito(Integer scoreCredito) {
        this.scoreCredito = scoreCredito;
    }

    public Boolean getRestricoesFinanceiras() {
        return restricoesFinanceiras;
    }

    public void setRestricoesFinanceiras(Boolean restricoesFinanceiras) {
        this.restricoesFinanceiras = restricoesFinanceiras;
    }

    public String getObservacoesFinanceiras() {
        return observacoesFinanceiras;
    }

    public void setObservacoesFinanceiras(String observacoesFinanceiras) {
        this.observacoesFinanceiras = observacoesFinanceiras;
    }

    public Interesses getInteresses() {
        return interesses;
    }

    public void setInteresses(Interesses interesses) {
        this.interesses = interesses;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getObservacoesInternas() {
        return observacoesInternas;
    }

    public void setObservacoesInternas(String observacoesInternas) {
        this.observacoesInternas = observacoesInternas;
    }

    public List<DocumentoClienteDTO> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoClienteDTO> documentos) {
        this.documentos = documentos;
    }
}

