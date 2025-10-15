package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.dailygreen.dailygreen.model.user.types.Organizator;

/**
 * Bridge concreto para operações de persistência de organizadores.
 * Implementa operações específicas de organizador usando o padrão Bridge.
 */
public class OrganizatorPersistenceBridge extends AbstractPersistenceBridge<Organizator> {
    
    private static final Logger logger = Logger.getLogger(OrganizatorPersistenceBridge.class.getName());
    
    public OrganizatorPersistenceBridge(PersistenceImplementor<Organizator> implementor) {
        super(implementor);
    }
    
    public List<Organizator> findAll() {
        return readAll();
    }
    
    public Optional<Organizator> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.warning("Tentativa de buscar organizador com email nulo ou vazio");
            return Optional.empty();
        }
        
        return readAll().stream()
                .filter(organizator -> email.equalsIgnoreCase(organizator.getEmail()))
                .findFirst();
    }
    
    public boolean save(Organizator organizator) {
        if (organizator == null) {
            logger.warning("Tentativa de salvar organizador nulo");
            return false;
        }
        
        if (organizator.getEmail() == null || organizator.getEmail().trim().isEmpty()) {
            logger.warning("Organizador sem email válido");
            return false;
        }
        
        try {
            List<Organizator> organizators = readAll();
            boolean exists = organizators.stream()
                    .anyMatch(o -> organizator.getEmail().equalsIgnoreCase(o.getEmail()));
            
            if (exists) {
                logger.info("Organizador já existente, ignorando: " + organizator.getEmail());
                return false;
            }
            
            organizators.add(organizator);
            saveAll(organizators);
            logger.info("Organizador salvo com sucesso: " + organizator.getEmail());
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar organizador: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.warning("Tentativa de deletar organizador com email nulo ou vazio");
            return false;
        }
        
        try {
            List<Organizator> organizators = readAll();
            boolean removed = organizators.removeIf(o -> email.equalsIgnoreCase(o.getEmail()));
            
            if (removed) {
                saveAll(organizators);
                logger.info("Organizador removido: " + email);
            } else {
                logger.warning("Organizador não encontrado para remoção: " + email);
            }
            
            return removed;
        } catch (Exception e) {
            logger.severe("Erro ao deletar organizador: " + e.getMessage());
            return false;
        }
    }
    
    public boolean validateLogin(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            logger.warning("Tentativa de validar login com email nulo ou vazio");
            return false;
        }
        
        Optional<Organizator> organizator = findByEmail(email);
        if (organizator.isPresent()) {
            boolean valid = organizator.get().getPassword().equals(password);
            if (valid) {
                logger.info("Login de organizador válido: " + email);
            } else {
                logger.warning("Senha incorreta para organizador: " + email);
            }
            return valid;
        } else {
            logger.warning("Organizador não encontrado para login: " + email);
            return false;
        }
    }
}
