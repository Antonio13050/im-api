package com.im_api.repository;

import com.im_api.model.ProcessoStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessoStatusHistoryRepository extends JpaRepository<ProcessoStatusHistory, Long> {
    List<ProcessoStatusHistory> findByProcessoIdOrderByCreatedDateDesc(Long processoId);
}
