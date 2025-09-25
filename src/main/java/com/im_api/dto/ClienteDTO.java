package com.im_api.dto;

import com.im_api.model.Cliente;
import com.im_api.model.Endereco;
import com.im_api.model.Interesses;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClienteDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpfCnpj;
    private LocalDate dataNascimento;
    private Long corretorId;
    private LocalDateTime createdDate;
    private Endereco endereco;
    private Interesses interesses;
    private String observacoes;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.telefone = cliente.getTelefone();
        this.cpfCnpj = cliente.getCpfCnpj();
        this.dataNascimento = cliente.getDataNascimento();
        this.corretorId = cliente.getCorretorId();
        this.createdDate = cliente.getCreatedDate();
        this.endereco = cliente.getEndereco();
        this.interesses = cliente.getInteresses();
        this.observacoes = cliente.getObservacoes();
    }

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
}