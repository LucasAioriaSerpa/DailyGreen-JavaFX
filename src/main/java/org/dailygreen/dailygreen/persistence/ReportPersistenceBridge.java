package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.logging.Logger;

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
}
