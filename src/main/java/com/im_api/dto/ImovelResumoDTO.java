package com.im_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImovelResumoDTO {
    private Long id;
    private String codigo;
    private String titulo;
    private Long clienteId;
    private Long corretorId;
    private String status;
}
