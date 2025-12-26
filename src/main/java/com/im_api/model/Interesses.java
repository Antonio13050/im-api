package com.im_api.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Interesses {

    @ElementCollection
    @CollectionTable(name = "cliente_tipos_imovel", joinColumns = @JoinColumn(name = "cliente_id"))
    @Column(name = "tipo_imovel")
    private List<String> tiposImovel = new ArrayList<>();

    private Double faixaPrecoMin;
    private Double faixaPrecoMax;

    @ElementCollection
    @CollectionTable(name = "cliente_bairros_interesse", joinColumns = @JoinColumn(name = "cliente_id"))
    @Column(name = "bairro")
    private List<String> bairrosInteresse = new ArrayList<>();

    private String finalidade;

    // Novos campos adicionados
    private Integer quartos;
    private Integer banheiros;
    private Integer vagas;
    @Column(columnDefinition = "TEXT")
    private String observacoesPreferencias;

    public Interesses() {
    }

    // Getters e Setters
    public List<String> getTiposImovel() {
        return tiposImovel;
    }

    public void setTiposImovel(List<String> tiposImovel) {
        this.tiposImovel = tiposImovel;
    }

    public Double getFaixaPrecoMin() {
        return faixaPrecoMin;
    }

    public void setFaixaPrecoMin(Double faixaPrecoMin) {
        this.faixaPrecoMin = faixaPrecoMin;
    }

    public Double getFaixaPrecoMax() {
        return faixaPrecoMax;
    }

    public void setFaixaPrecoMax(Double faixaPrecoMax) {
        this.faixaPrecoMax = faixaPrecoMax;
    }

    public List<String> getBairrosInteresse() {
        return bairrosInteresse;
    }

    public void setBairrosInteresse(List<String> bairrosInteresse) {
        this.bairrosInteresse = bairrosInteresse;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public Integer getQuartos() {
        return quartos;
    }

    public void setQuartos(Integer quartos) {
        this.quartos = quartos;
    }

    public Integer getBanheiros() {
        return banheiros;
    }

    public void setBanheiros(Integer banheiros) {
        this.banheiros = banheiros;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public String getObservacoesPreferencias() {
        return observacoesPreferencias;
    }

    public void setObservacoesPreferencias(String observacoesPreferencias) {
        this.observacoesPreferencias = observacoesPreferencias;
    }
}

