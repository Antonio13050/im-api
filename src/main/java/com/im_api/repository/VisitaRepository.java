package com.im_api.repository;

import com.im_api.model.Visita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitaRepository extends JpaRepository<Visita, Long> {
    List<Visita> findByCorretorId(Long corretorId);
}