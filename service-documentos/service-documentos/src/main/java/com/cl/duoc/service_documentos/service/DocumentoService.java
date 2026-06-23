package com.cl.duoc.service_documentos.service;

import com.cl.duoc.service_documentos.model.Documento;
import com.cl.duoc.service_documentos.repository.DocumentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;

    public DocumentoService(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    public List<Documento> getAllDocumentos() {
        return documentoRepository.findAll();
    }

    public Documento getDocumentoById(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Documento no encontrado"));
    }

    public Documento createDocumento(Documento documento) {
        documento.setId(null);
        return documentoRepository.save(documento);
    }

    public Documento updateDocumento(Long id, Documento documento) {
        Documento existente = getDocumentoById(id);
        existente.setRutFallecido(documento.getRutFallecido());
        existente.setTipoDocumento(documento.getTipoDocumento());
        existente.setNombreDocumento(documento.getNombreDocumento());
        existente.setFechaEmision(documento.getFechaEmision());
        return documentoRepository.save(existente);
    }

    public void deleteDocumento(Long id) {
        Documento existente = getDocumentoById(id);
        documentoRepository.delete(existente);
    }
}
