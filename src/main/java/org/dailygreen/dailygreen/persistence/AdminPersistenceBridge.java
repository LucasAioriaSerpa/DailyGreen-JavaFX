package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.dailygreen.dailygreen.model.user.types.Administrator;

/**
 * Bridge concreto para operações de persistência de administradores.
 * Implementa operações específicas de administrador usando o padrão Bridge.
 */
public class AdminPersistenceBridge extends AbstractPersistenceBridge<Administrator> {
    
    private static final Logger logger = Logger.getLogger(AdminPersistenceBridge.class.getName());
    
    public AdminPersistenceBridge(PersistenceImplementor<Administrator> implementor) {
        super(implementor);
    }
    
    public List<Administrator> findAll() {
        return readAll();
    }
    
    public Optional<Administrator> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.warning("Tentativa de buscar administrador com email nulo ou vazio");
            return Optional.empty();
        }
        
        return readAll().stream()
                .filter(admin -> email.equalsIgnoreCase(admin.getEmail()))
                .findFirst();
    }
    
    public boolean save(Administrator admin) {
        if (admin == null) {
            logger.warning("Tentativa de salvar administrador nulo");
            return false;
        }
        
        if (admin.getEmail() == null || admin.getEmail().trim().isEmpty()) {
            logger.warning("Administrador sem email válido");
            return false;
        }
        
        try {
            List<Administrator> admins = readAll();
            boolean exists = admins.stream()
                    .anyMatch(a -> admin.getEmail().equalsIgnoreCase(a.getEmail()));
            
            if (exists) {
                logger.info("Administrador já existente, ignorando: " + admin.getEmail());
                return false;
            }
            
            admins.add(admin);
            saveAll(admins);
            logger.info("Administrador salvo com sucesso: " + admin.getEmail());
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar administrador: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.warning("Tentativa de deletar administrador com email nulo ou vazio");
            return false;
        }
        
        try {
            List<Administrator> admins = readAll();
            boolean removed = admins.removeIf(a -> email.equalsIgnoreCase(a.getEmail()));
            
            if (removed) {
                saveAll(admins);
                logger.info("Administrador removido: " + email);
            } else {
                logger.warning("Administrador não encontrado para remoção: " + email);
            }
            
            return removed;
        } catch (Exception e) {
            logger.severe("Erro ao deletar administrador: " + e.getMessage());
            return false;
        }
    }
    
    public boolean validateLogin(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            logger.warning("Tentativa de validar login com email nulo ou vazio");
            return false;
        }
        
        Optional<Administrator> admin = findByEmail(email);
        if (admin.isPresent()) {
            boolean valid = admin.get().getPassword().equals(password);
            if (valid) {
                logger.info("Login de administrador válido: " + email);
            } else {
                logger.warning("Senha incorreta para administrador: " + email);
            }
            return valid;
        } else {
            logger.warning("Administrador não encontrado para login: " + email);
            return false;
        }
    }
}
