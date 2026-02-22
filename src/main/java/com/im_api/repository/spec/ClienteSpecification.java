package com.im_api.repository.spec;

import com.im_api.dto.ClienteFilterDTO;
import com.im_api.model.Cliente;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ClienteSpecification {
    public static Specification<Cliente> withFilters(ClienteFilterDTO filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.getQuery() != null && !filters.getQuery().isEmpty()) {
                String q = "%" + filters.getQuery().toLowerCase() + "%";
                Predicate nome = cb.like(cb.lower(root.get("nome")), q);
                Predicate email = cb.like(cb.lower(root.get("email")), q);
                Predicate telefone = cb.like(root.get("telefone"), q);
                predicates.add(cb.or(nome, email, telefone));
            }

            if (filters.getPerfil() != null && !filters.getPerfil().isEmpty() && !filters.getPerfil().equals("todos")) {
                predicates.add(cb.equal(root.get("perfil"), filters.getPerfil()));
            }

            if (filters.getCorretorId() != null) {
                predicates.add(cb.equal(root.get("corretorId"), filters.getCorretorId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
