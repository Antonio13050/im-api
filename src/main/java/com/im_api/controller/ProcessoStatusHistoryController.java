package com.im_api.controller;

import com.im_api.model.ProcessoStatusHistory;
import com.im_api.service.ProcessoStatusHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/processo-status-history")
public class ProcessoStatusHistoryController {

    private final ProcessoStatusHistoryService historyService;

    public ProcessoStatusHistoryController(ProcessoStatusHistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/processo/{processoId}")
    public List<ProcessoStatusHistory> listByProcess(@PathVariable Long processoId) {
        return historyService.listByProcessoId(processoId);
    }
}
