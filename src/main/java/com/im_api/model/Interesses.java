package com.im_api.model;

import jakarta.persistence.*;
import java.util.List;

@Embeddable
public class Interesses {

    @ElementCollection
    @CollectionTable(name = "cliente_tipos_interesse", joinColumns = @JoinColumn(name = "cliente_id"))
    @Column(name = "tipo_imovel")
    private List<String> tiposImovel;

    private Double faixaPrecoMin;
    private Double faixaPrecoMax;

    @ElementCollection
    @CollectionTable(name = "cliente_bairros_interesse", joinColumns = @JoinColumn(name = "cliente_id"))
    @Column(name = "bairro")
    private List<String> bairrosInteresse;

    private String finalidade;

    public Interesses() {}

    public Interesses(List<String> tiposImovel, Double faixaPrecoMin, Double faixaPrecoMax,
                      List<String> bairrosInteresse, String finalidade) {
        this.tiposImovel = tiposImovel;
        this.faixaPrecoMin = faixaPrecoMin;
        this.faixaPrecoMax = faixaPrecoMax;
        this.bairrosInteresse = bairrosInteresse;
        this.finalidade = finalidade;
    }

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
}
