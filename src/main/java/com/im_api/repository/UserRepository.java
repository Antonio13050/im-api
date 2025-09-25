package com.im_api.repository;

import com.im_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNome(String nome);

    Optional<User> findByEmail(String email);

    List<User> findByRoles_NomeIn(List<String> roles);

    List<User> findByRoles_NomeAndGerenteId(String role, Long gerenteId);

    List<User> findByGerenteId(Long gerenteId);
}
