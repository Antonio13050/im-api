package com.im_api.repository;

import com.im_api.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    List<Processo> findByCorretorId(Long corretorId);
    List<Processo> findByCorretorIdIn(List<Long> teamIds);
}
