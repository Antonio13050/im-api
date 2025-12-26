package com.im_api.model;

import com.im_api.model.enums.Perfil;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Perfil perfil = Perfil.CLIENTE;

    private Long corretorId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    // Endereço
    @Embedded
    private Endereco endereco;

    // Informações Financeiras
    private Double rendaMensal;
    private String banco;
    private String agencia;
    private String conta;
    private Integer scoreCredito;
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
    private List<DocumentoCliente> documentos = new ArrayList<>();

    public Cliente() {
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Long getCorretorId() {
        return corretorId;
    }

    public void setCorretorId(Long corretorId) {
        this.corretorId = corretorId;
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

    public List<DocumentoCliente> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoCliente> documentos) {
        this.documentos = documentos;
    }
}

