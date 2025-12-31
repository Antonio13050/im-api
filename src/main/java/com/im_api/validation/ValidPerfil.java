package com.im_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPerfilValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPerfil {
    String message() default "Perfil inválido. Valores aceitos: CLIENTE, PROPRIETARIO, LOCATARIO, FIADOR, CORRETOR_PARCEIRO";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

