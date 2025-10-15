package org.dailygreen.dailygreen.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.dailygreen.dailygreen.model.event.Event;
import org.dailygreen.dailygreen.persistence.PersistenceFacade;
import org.dailygreen.dailygreen.persistence.PersistenceFacadeFactory;

/**
 * Serviço para operações relacionadas a eventos.
 * Utiliza a PersistenceFacade para acesso unificado aos dados.
 */
public class EventService {
    
    private static final Logger logger = Logger.getLogger(EventService.class.getName());
    private final PersistenceFacade persistenceFacade;
    
    public EventService() {
        this.persistenceFacade = PersistenceFacadeFactory.createJsonPersistenceFacade();
    }
    
    public EventService(PersistenceFacade persistenceFacade) {
        this.persistenceFacade = persistenceFacade;
    }
    
    /**
     * Cria um novo evento
     * @param event Evento a ser criado
     * @return true se criado com sucesso
     */
    public boolean createEvent(Event event) {
        try {
            boolean saved = persistenceFacade.saveEvent(event);
            if (saved) {
                logger.info("Evento criado com sucesso: ID " + event.getID());
            }
            return saved;
        } catch (Exception e) {
            logger.severe("Erro ao criar evento: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Busca evento por ID
     * @param id ID do evento
     * @return Evento encontrado ou Optional.empty()
     */
    public Optional<Event> findEventById(long id) {
        return persistenceFacade.findEventById(id);
    }
    
    /**
     * Lista todos os eventos
     * @return Lista de todos os eventos
     */
    public List<Event> findAllEvents() {
        return persistenceFacade.findAllEvents();
    }
    
    /**
     * Remove evento por ID
     * @param id ID do evento a ser removido
     * @return true se removido com sucesso
     */
    public boolean deleteEvent(long id) {
        try {
            boolean deleted = persistenceFacade.deleteEventById(id);
            if (deleted) {
                logger.info("Evento removido com sucesso: ID " + id);
            }
            return deleted;
        } catch (Exception e) {
            logger.severe("Erro ao remover evento: " + e.getMessage());
            return false;
        }
    }
}
