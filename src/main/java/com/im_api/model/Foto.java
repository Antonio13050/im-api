package com.im_api.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fotos")
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    @Lob
    @Column(name = "dados", nullable = false)
    private byte[] dados;

    @Column(name = "tipo_conteudo")
    private String tipoConteudo;

    @ManyToOne
    @JoinColumn(name = "imovel_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Imovel imovel;

    public Foto(String nomeArquivo, byte[] dados, String tipoConteudo, Imovel imovel) {
        this.dados = dados;
        this.nomeArquivo = nomeArquivo;
        this.tipoConteudo = tipoConteudo;
        this.imovel = imovel;
    }
}
