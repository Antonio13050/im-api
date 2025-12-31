package com.im_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidTelefoneValidator implements ConstraintValidator<ValidTelefone, String> {

    // Aceita formatos: (11) 98765-4321, (11) 9876-5432, 11987654321, etc.
    private static final Pattern TELEFONE_PATTERN = Pattern.compile(
        "^(\\(?\\d{2}\\)?\\s?)?(\\d{4,5}[-\\s]?\\d{4})$"
    );

    @Override
    public void initialize(ValidTelefone constraintAnnotation) {
        // Nada a inicializar
    }

    @Override
    public boolean isValid(String telefone, ConstraintValidatorContext context) {
        if (telefone == null || telefone.isBlank()) {
            return true; // Validação de obrigatoriedade deve ser feita com @NotNull ou @NotBlank
        }

        // Remove espaços e caracteres especiais para validação
        String digits = telefone.replaceAll("[^0-9]", "");
        
        // Telefone deve ter 10 ou 11 dígitos (com ou sem DDD)
        if (digits.length() < 8 || digits.length() > 11) {
            return false;
        }

        return TELEFONE_PATTERN.matcher(telefone).matches() || digits.length() >= 8;
    }
}

