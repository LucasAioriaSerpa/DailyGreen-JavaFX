package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.dailygreen.dailygreen.model.user.types.Participant;

/**
 * Bridge concreto para operações de persistência de participantes.
 * Implementa operações específicas de participante usando o padrão Bridge.
 */
public class ParticipantPersistenceBridge extends AbstractPersistenceBridge<Participant> {
    
    private static final Logger logger = Logger.getLogger(ParticipantPersistenceBridge.class.getName());
    
    public ParticipantPersistenceBridge(PersistenceImplementor<Participant> implementor) {
        super(implementor);
    }
    
    public List<Participant> findAll() {
        return readAll();
    }
    
    public Optional<Participant> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.warning("Tentativa de buscar participante com email nulo ou vazio");
            return Optional.empty();
        }
        
        return readAll().stream()
                .filter(participant -> email.equalsIgnoreCase(participant.getEmail()))
                .findFirst();
    }
    
    public boolean save(Participant participant) {
        if (participant == null) {
            logger.warning("Tentativa de salvar participante nulo");
            return false;
        }
        
        if (participant.getEmail() == null || participant.getEmail().trim().isEmpty()) {
            logger.warning("Participante sem email válido");
            return false;
        }
        
        try {
            List<Participant> participants = readAll();
            boolean exists = participants.stream()
                    .anyMatch(p -> participant.getEmail().equalsIgnoreCase(p.getEmail()));
            
            if (exists) {
                logger.info("Participante já existente, ignorando: " + participant.getEmail());
                return false;
            }
            
            participants.add(participant);
            saveAll(participants);
            logger.info("Participante salvo com sucesso: " + participant.getEmail());
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar participante: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.warning("Tentativa de deletar participante com email nulo ou vazio");
            return false;
        }
        
        try {
            List<Participant> participants = readAll();
            boolean removed = participants.removeIf(p -> email.equalsIgnoreCase(p.getEmail()));
            
            if (removed) {
                saveAll(participants);
                logger.info("Participante removido: " + email);
            } else {
                logger.warning("Participante não encontrado para remoção: " + email);
            }
            
            return removed;
        } catch (Exception e) {
            logger.severe("Erro ao deletar participante: " + e.getMessage());
            return false;
        }
    }
    
    public boolean validateLogin(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            logger.warning("Tentativa de validar login com email nulo ou vazio");
            return false;
        }
        
        Optional<Participant> participant = findByEmail(email);
        if (participant.isPresent()) {
            boolean valid = participant.get().getPassword().equals(password);
            if (valid) {
                logger.info("Login de participante válido: " + email);
            } else {
                logger.warning("Senha incorreta para participante: " + email);
            }
            return valid;
        } else {
            logger.warning("Participante não encontrado para login: " + email);
            return false;
        }
    }
}
