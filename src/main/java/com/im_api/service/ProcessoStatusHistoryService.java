package com.im_api.service;

import com.im_api.model.ProcessoStatusHistory;
import com.im_api.repository.ProcessoStatusHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessoStatusHistoryService {

    private final ProcessoStatusHistoryRepository historyRepository;

    public ProcessoStatusHistoryService(ProcessoStatusHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<ProcessoStatusHistory> listByProcessoId(Long processoId){
        return historyRepository.findByProcessoIdOrderByCreatedDateDesc(processoId);
    }
}
