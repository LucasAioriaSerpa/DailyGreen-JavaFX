package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.dailygreen.dailygreen.model.moderation.Report;

/**
 * Bridge concreto para operações de persistência de denúncias.
 * Implementa operações específicas de denúncia usando o padrão Bridge.
 */
public class ReportPersistenceBridge extends AbstractPersistenceBridge<Report> {
    
    private static final Logger logger = Logger.getLogger(ReportPersistenceBridge.class.getName());
    
    public ReportPersistenceBridge(PersistenceImplementor<Report> implementor) {
        super(implementor);
    }
    
    public List<Report> findAll() {
        return readAll();
    }
    
    public boolean save(Report report) {
        if (report == null) {
            logger.warning("Tentativa de salvar denúncia nula");
            return false;
        }
        
        try {
            List<Report> reports = readAll();
            
            // Gerar ID se necessário
            if (report.getId() == null) {
                report.setId((int) generateNewId());
            }
            
            reports.add(report);
            saveAll(reports);
            logger.info("Denúncia salva com sucesso: ID " + report.getId());
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar denúncia: " + e.getMessage());
            return false;
        }
    }
    
    public boolean update(Report report) {
        if (report == null) {
            logger.warning("Tentativa de atualizar denúncia nula");
            return false;
        }
        
        try {
            List<Report> reports = readAll();
            boolean updated = false;
            
            for (int i = 0; i < reports.size(); i++) {
                if (reports.get(i).getId().equals(report.getId())) {
                    reports.set(i, report);
                    updated = true;
                    break;
                }
            }
            
            if (updated) {
                saveAll(reports);
                logger.info("Denúncia atualizada: ID " + report.getId());
                return true;
            } else {
                logger.warning("Denúncia não encontrada para atualização: ID " + report.getId());
                return false;
            }
        } catch (Exception e) {
            logger.severe("Erro ao atualizar denúncia: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(Report report) {
        if (report == null) {
            logger.warning("Tentativa de deletar denúncia nula");
            return false;
        }
        
        try {
            List<Report> reports = readAll();
            boolean removed = reports.removeIf(r -> r.getId().equals(report.getId()));
            
            if (removed) {
                saveAll(reports);
                logger.info("Denúncia removida: ID " + report.getId());
            } else {
                logger.warning("Denúncia não encontrada para remoção: ID " + report.getId());
            }
            
            return removed;
        } catch (Exception e) {
            logger.severe("Erro ao deletar denúncia: " + e.getMessage());
            return false;
        }
    }
    
    public Optional<Report> findById(Integer id) {
        if (id == null) {
            logger.warning("Tentativa de buscar denúncia com ID nulo");
            return Optional.empty();
        }
        
        return readAll().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }
    
    public List<Report> findByFilter(String tipo, String termo) {
        if (tipo == null || termo == null || tipo.isEmpty() || termo.isEmpty()) {
            return findAll();
        }
        
        String tipoLower = tipo.toLowerCase();
        String termoLower = termo.toLowerCase();
        
        return readAll().stream()
                .filter(report -> {
                    switch (tipoLower) {
                        case "titulo":
                            return report.getTitulo() != null && report.getTitulo().toLowerCase().contains(termoLower);
                        case "motivo":
                            return report.getMotivo() != null && report.getMotivo().toLowerCase().contains(termoLower);
                        case "participante":
                            return report.getParticipante() != null && report.getParticipante().toLowerCase().contains(termoLower);
                        case "status":
                            return report.getStatus() != null && report.getStatus().toLowerCase().contains(termoLower);
                        case "data":
                            return report.getData() != null && report.getData().toString().toLowerCase().contains(termoLower);
                        case "suspenso":
                            return Boolean.toString(report.getSuspenso()).toLowerCase().contains(termoLower);
                        case "banido":
                            return Boolean.toString(report.getBanido()).toLowerCase().contains(termoLower);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
    }
}
