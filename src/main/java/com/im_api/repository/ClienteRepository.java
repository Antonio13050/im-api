package com.im_api.repository;

import com.im_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByCorretorId(Long corretorId);
    List<Cliente> findByCorretorIdIn(List<Long> corretorIds);
}