package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.dailygreen.dailygreen.model.event.Event;

/**
 * Bridge concreto para operações de persistência de eventos.
 * Implementa operações específicas de evento usando o padrão Bridge.
 */
public class EventPersistenceBridge extends AbstractPersistenceBridge<Event> {
    
    private static final Logger logger = Logger.getLogger(EventPersistenceBridge.class.getName());
    
    public EventPersistenceBridge(PersistenceImplementor<Event> implementor) {
        super(implementor);
    }
    
    public List<Event> findAll() {
        return readAll();
    }
    
    public Optional<Event> findById(long id) {
        return readAll().stream()
                .filter(event -> event.getID() == id)
                .findFirst();
    }
    
    public boolean save(Event event) {
        if (event == null) {
            logger.warning("Tentativa de salvar evento nulo");
            return false;
        }
        
        try {
            List<Event> events = readAll();
            
            // Gerar ID se necessário
            if (event.getID() == 0) {
                // Event não tem setId, então vamos usar o ID do Post pai
                // Como Event herda de Post, vamos usar o método do Post
                // Por enquanto, vamos assumir que o ID será definido externamente
            }
            
            events.add(event);
            saveAll(events);
            logger.info("Evento salvo com sucesso: ID " + event.getID());
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar evento: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteById(long id) {
        try {
            List<Event> events = readAll();
            boolean removed = events.removeIf(event -> event.getID() == id);
            
            if (removed) {
                saveAll(events);
                logger.info("Evento removido: ID " + id);
            } else {
                logger.warning("Evento não encontrado para remoção: ID " + id);
            }
            
            return removed;
        } catch (Exception e) {
            logger.severe("Erro ao deletar evento: " + e.getMessage());
            return false;
        }
    }
}
