package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.dailygreen.dailygreen.model.user.User;

/**
 * Bridge concreto para operações de persistência de usuários.
 * Implementa operações específicas de usuário usando o padrão Bridge.
 */
public class UserPersistenceBridge extends AbstractPersistenceBridge<User> {
    
    private static final Logger logger = Logger.getLogger(UserPersistenceBridge.class.getName());
    
    public UserPersistenceBridge(PersistenceImplementor<User> implementor) {
        super(implementor);
    }
    
    public List<User> findAll() {
        return readAll();
    }
    
    public Optional<User> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return readAll().stream()
                .filter(user -> email.equalsIgnoreCase(extractEmail(user)))
                .findFirst();
    }
    
    public boolean save(User user) {
        if (user == null) {
            logger.warning("Tentativa de salvar usuário nulo");
            return false;
        }
        
        String email = extractEmail(user);
        if (email == null) {
            logger.warning("Usuário sem email válido");
            return false;
        }
        
        List<User> users = readAll();
        boolean exists = users.stream()
                .anyMatch(u -> email.equalsIgnoreCase(extractEmail(u)));
        
        if (exists) {
            logger.info("Usuário já existente, ignorando: " + email);
            return false;
        }
        
        try {
            users.add(user);
            saveAll(users);
            logger.info("Usuário salvo com sucesso: " + email);
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar usuário: " + e.getMessage());
            return false;
        }
    }
    
    public boolean update(User user) {
        if (user == null) {
            logger.warning("Tentativa de atualizar usuário nulo");
            return false;
        }
        
        String email = extractEmail(user);
        if (email == null) {
            logger.warning("Usuário sem email válido para atualização");
            return false;
        }
        
        List<User> users = readAll();
        boolean updated = false;
        
        for (int i = 0; i < users.size(); i++) {
            if (email.equalsIgnoreCase(extractEmail(users.get(i)))) {
                users.set(i, user);
                updated = true;
                break;
            }
        }
        
        if (updated) {
            saveAll(users);
            logger.info("Usuário atualizado: " + email);
            return true;
        } else {
            logger.warning("Usuário não encontrado para atualização: " + email);
            return false;
        }
    }
    
    public boolean deleteByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.warning("Tentativa de deletar usuário com email nulo ou vazio");
            return false;
        }
        
        List<User> users = readAll();
        boolean removed = users.removeIf(u -> email.equalsIgnoreCase(extractEmail(u)));
        
        if (removed) {
            saveAll(users);
            logger.info("Usuário removido: " + email);
        } else {
            logger.warning("Nenhum usuário encontrado para remoção: " + email);
        }
        
        return removed;
    }
    
    public boolean validateLogin(String email) {
        Optional<User> user = findByEmail(email);
        return user.isPresent() && user.get().isLogged();
    }
    
    private String extractEmail(User user) {
        if (user == null) return null;
        if (user.getAccountAdministrador() != null) return user.getAccountAdministrador().getEmail();
        if (user.getAccountParticipante() != null) return user.getAccountParticipante().getEmail();
        if (user.getAccountOrganizator() != null) return user.getAccountOrganizator().getEmail();
        return null;
    }
}
