package com.im_api.model;

import com.im_api.model.enums.Perfil;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpfCnpj;
    private LocalDate dataNascimento;
    private Long corretorId = 3L;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil = Perfil.CLIENTE;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @Embedded
    private Endereco endereco;

    @Embedded
    private Interesses interesses;

    @Column(length = 1000)
    private String observacoes;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String email, String telefone, String cpfCnpj, LocalDate dataNascimento, Long corretorId, Perfil perfil, LocalDateTime createdDate, Endereco endereco, Interesses interesses, String observacoes
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpfCnpj = cpfCnpj;
        this.dataNascimento = dataNascimento;
        this.corretorId = corretorId;
        this.perfil = perfil;
        this.createdDate = createdDate;
        this.endereco = endereco;
        this.interesses = interesses;
        this.observacoes = observacoes;
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
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
