package com.im_api.validation;

import com.im_api.model.enums.Perfil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPerfilValidator implements ConstraintValidator<ValidPerfil, String> {

    @Override
    public void initialize(ValidPerfil constraintAnnotation) {
        // Nada a inicializar
    }

    @Override
    public boolean isValid(String perfil, ConstraintValidatorContext context) {
        if (perfil == null || perfil.isBlank()) {
            return true; // Validação de obrigatoriedade deve ser feita com @NotNull
        }

        try {
            Perfil.valueOf(perfil.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

