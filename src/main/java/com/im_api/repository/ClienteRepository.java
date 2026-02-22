package com.im_api.repository;

import com.im_api.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {
    List<Cliente> findByCorretorId(Long corretorId);
    List<Cliente> findByCorretorIdIn(List<Long> corretorIds);
    
    Page<Cliente> findByCorretorId(Long corretorId, Pageable pageable);
    Page<Cliente> findByCorretorIdIn(List<Long> corretorIds, Pageable pageable);
}