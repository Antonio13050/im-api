package com.im_api.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    private boolean isProduction() {
        return "prod".equalsIgnoreCase(activeProfile) || "production".equalsIgnoreCase(activeProfile);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        log.warn("Recurso não encontrado: {} - Path: {}", ex.getMessage(), request.getRequestURI());
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("Requisição inválida: {} - Path: {}", ex.getMessage(), request.getRequestURI());
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Erro desconhecido"
                ));
        log.warn("Erro de validação: {} campos inválidos - Path: {}", errors.size(), request.getRequestURI());
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Erro de validação", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        log.warn("Acesso negado - Path: {} - User: {}", request.getRequestURI(), 
                request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous");
        ApiError error = new ApiError(HttpStatus.FORBIDDEN, "Acesso negado");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
        // Log completo apenas no servidor (com stack trace)
        log.error("Erro interno não tratado - Path: {} - Method: {}", 
                request.getRequestURI(), request.getMethod(), ex);

        // Mensagem segura para o cliente
        String message;
        Map<String, String> details = new HashMap<>();
        
        if (isProduction()) {
            // Em produção: mensagem genérica sem detalhes sensíveis
            message = "Erro interno do servidor. Por favor, contate o suporte se o problema persistir.";
            details.put("errorId", generateErrorId());
        } else {
            // Em desenvolvimento: permite mais detalhes para debugging
            message = "Erro interno do servidor: " + ex.getClass().getSimpleName();
            if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
                details.put("detail", sanitizeMessage(ex.getMessage()));
            }
        }

        details.put("timestamp", Instant.now().toString());
        details.put("path", request.getRequestURI());

        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, details);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Gera um ID único para rastreamento de erros em produção
     */
    private String generateErrorId() {
        return "ERR-" + System.currentTimeMillis() + "-" + 
               Long.toHexString(Thread.currentThread().getId());
    }

    /**
     * Remove informações sensíveis da mensagem de erro
     * (paths de arquivos, senhas, tokens, etc.)
     */
    private String sanitizeMessage(String message) {
        if (message == null) {
            return null;
        }
        
        // Remove paths absolutos que possam expor estrutura do servidor
        String sanitized = message.replaceAll(
            "(?i)([A-Z]:\\\\|/home/|/var/|/usr/)[^\\s]*", 
            "[PATH_REMOVED]"
        );
        
        // Remove possíveis tokens/senhas
        sanitized = sanitized.replaceAll(
            "(?i)(password|senha|token|secret|key)\\s*[:=]\\s*[^\\s]+", 
            "$1=[REDACTED]"
        );
        
        return sanitized;
    }
}
