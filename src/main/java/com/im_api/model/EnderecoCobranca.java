package com.im_api.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EnderecoCobranca {

    @Size(max = 10, message = "O CEP deve ter no máximo 10 caracteres")
    private String cep;

    @Size(max = 255, message = "A rua deve ter no máximo 255 caracteres")
    private String rua;

    @Size(max = 20, message = "O número deve ter no máximo 20 caracteres")
    private String numero;

    @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres")
    private String complemento;

    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
    private String bairro;

    private String andar;

    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @Size(max = 2, message = "O estado deve ter 2 caracteres (UF)")
    private String estado;
}
