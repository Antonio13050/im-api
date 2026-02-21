package com.im_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private Integer quartos;
    private Integer banheiros;
    private Integer vagas;

    @Column(columnDefinition = "TEXT")
    private String observacoesPreferencias;
}
