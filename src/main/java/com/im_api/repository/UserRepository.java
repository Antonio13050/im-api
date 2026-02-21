package com.im_api.repository;

import com.im_api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNome(String nome);

    Optional<User> findByEmail(String email);

    Optional<User> findByCpf(String cpf);

    Optional<User> findByCreci(String creci);

    List<User> findByRoles_NomeIn(List<String> roles);

    Page<User> findByRoles_Nome(String role, Pageable pageable);

    List<User> findByRoles_NomeAndGerenteId(String role, Long gerenteId);

    List<User> findByGerenteId(Long gerenteId);

    boolean existsByCpf(String cpf);

    boolean existsByCreci(String creci);
}
