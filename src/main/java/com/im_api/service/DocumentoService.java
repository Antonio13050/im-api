package com.im_api.service;

import com.im_api.model.Documento;
import com.im_api.repository.DocumentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;

    public DocumentoService(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    public Documento saveDocument(Long processId, String documentName, String documentType,
                                  MultipartFile file) throws IOException {
        Documento document = new Documento();
        document.setProcessoId(processId);
        document.setDocumentName(documentName);
        document.setDocumentType(documentType);
        document.setFileContent(file.getBytes());
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setUploadDate(LocalDate.now());
        document.setStatus("enviado");

        return documentoRepository.save(document);
    }

    public List<Documento> getDocumentsByProcessId(Long processId) {
        return documentoRepository.findByProcessoId(processId);
    }

    public Documento getDocumentById(Long documentId) {
        return documentoRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    public void deleteDocument(Long documentId) {
        if (!documentoRepository.existsById(documentId)) {
            throw new RuntimeException("Document not found");
        }
        documentoRepository.deleteById(documentId);
    }
}
