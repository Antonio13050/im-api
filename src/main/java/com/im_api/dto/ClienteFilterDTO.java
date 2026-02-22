package com.im_api.dto;

import lombok.Data;

@Data
public class ClienteFilterDTO {
    private String query;
    private String perfil;
    private Long corretorId;
}
