package com.im_api.dto;

import com.im_api.model.Endereco;
import com.im_api.model.Imovel;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class ImovelDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String tipo;
    private String finalidade;
    private String status;
    private LocalDateTime createdDate;

    private Endereco endereco;

    private Double preco;
    private Double area;
    private Integer quartos;
    private Integer banheiros;
    private Integer vagas;

    private List<FotoDTO> fotos;
    private List<VideoDTO> videos;

    private Long clienteId;
    private Long corretorId;

    public ImovelDTO() {
    }

    public ImovelDTO(String titulo, List<FotoDTO> fotos) {
        this.titulo = titulo;
        this.fotos = fotos;
    }

    public ImovelDTO(Imovel imovel) {
        this.id = imovel.getId();
        this.titulo = imovel.getTitulo();
        this.descricao = imovel.getDescricao();
        this.tipo = imovel.getTipo();
        this.finalidade = imovel.getFinalidade();
        this.status = imovel.getStatus();
        this.createdDate = imovel.getCreatedDate();
        this.endereco = imovel.getEndereco();
        this.preco = imovel.getPreco();
        this.area = imovel.getArea();
        this.quartos = imovel.getQuartos();
        this.banheiros = imovel.getBanheiros();
        this.vagas = imovel.getVagas();
        this.clienteId = imovel.getClienteId();
        this.corretorId = imovel.getCorretorId();
        this.fotos = imovel.getFotos().stream()
                .map(foto -> new FotoDTO(
                        foto.getId(),
                        foto.getTipoConteudo(),
                        foto.getNomeArquivo(),
                        Base64.getEncoder().encodeToString(foto.getDados())
                ))
                .collect(Collectors.toList());
        this.videos = imovel.getVideos().stream()
                .map(video -> new VideoDTO(
                        video.getId(),
                        video.getTipoConteudo(),
                        video.getNomeArquivo(),
                        Base64.getEncoder().encodeToString(video.getDados()),
                        video.getTamanho()
                ))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
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

    public List<FotoDTO> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotoDTO> fotos) {
        this.fotos = fotos;
    }

    public List<VideoDTO> getVideos() {
        return videos;
    }
    public void setVideos(List<VideoDTO> videos) {
        this.videos = videos;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getCorretorId() {
        return corretorId;
    }

    public void setCorretorId(Long corretorId) {
        this.corretorId = corretorId;
    }
}
