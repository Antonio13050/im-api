package com.im_api.repository.spec;

import com.im_api.dto.ImovelFilterDTO;
import com.im_api.model.Imovel;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ImovelSpecification {
    public static Specification<Imovel> withFilters(ImovelFilterDTO filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.getStatus() != null && !filters.getStatus().isEmpty() && !filters.getStatus().equals("todos")) {
                predicates.add(cb.equal(root.get("status"), filters.getStatus()));
            }
            if (filters.getTipo() != null && !filters.getTipo().isEmpty() && !filters.getTipo().equals("todos")) {
                predicates.add(cb.equal(root.get("tipo"), filters.getTipo()));
            }
            if (filters.getFinalidade() != null && !filters.getFinalidade().isEmpty() && !filters.getFinalidade().equals("todos")) {
                predicates.add(cb.equal(root.get("finalidade"), filters.getFinalidade()));
            }

            // Price filtering logic
            if (filters.getPrecoMin() != null || filters.getPrecoMax() != null) {
                Predicate pricePredicate = null;
                // If finalidade is specific, check that price
                if ("venda".equals(filters.getFinalidade())) {
                     if (filters.getPrecoMin() != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("precoVenda"), filters.getPrecoMin()));
                     }
                     if (filters.getPrecoMax() != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("precoVenda"), filters.getPrecoMax()));
                     }
                } else if ("aluguel".equals(filters.getFinalidade())) {
                     if (filters.getPrecoMin() != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("precoAluguel"), filters.getPrecoMin()));
                     }
                     if (filters.getPrecoMax() != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("precoAluguel"), filters.getPrecoMax()));
                     }
                } else {
                    // Generic price search: Check if ANY price matches range?
                    // Or prioritize precoVenda? 
                    // Let's implement reasonable defaults: check ALL non-null prices if they fall in range?
                    // This is complex. For now, if no finalidade, maybe ignore price or assume Venda?
                    // Let's assume Venda if not specified, OR check if any fits.
                    
                    // Simple approach: Check precoVenda by default if mixed.
                     if (filters.getPrecoMin() != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("precoVenda"), filters.getPrecoMin()));
                     }
                     if (filters.getPrecoMax() != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("precoVenda"), filters.getPrecoMax()));
                     }
                }
            }

            if (filters.getBairro() != null && !filters.getBairro().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("endereco").get("bairro")), "%" + filters.getBairro().toLowerCase() + "%"));
            }
            
            if (filters.getQuery() != null && !filters.getQuery().isEmpty()) {
                String q = "%" + filters.getQuery().toLowerCase() + "%";
                Predicate titulo = cb.like(cb.lower(root.get("titulo")), q);
                Predicate bairro = cb.like(cb.lower(root.get("endereco").get("bairro")), q);
                Predicate cidade = cb.like(cb.lower(root.get("endereco").get("cidade")), q);
                predicates.add(cb.or(titulo, bairro, cidade));
            }
            
            if (filters.getCorretorId() != null) {
                 predicates.add(cb.equal(root.get("corretorId"), filters.getCorretorId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
