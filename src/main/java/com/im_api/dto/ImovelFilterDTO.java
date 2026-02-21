package com.im_api.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ImovelFilterDTO {
    private String status;
    private String tipo;
    private String finalidade;
    private BigDecimal precoMin;
    private BigDecimal precoMax;
    private String bairro;
    private String query;
    private Long corretorId;
}
