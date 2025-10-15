package org.dailygreen.dailygreen.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.dailygreen.dailygreen.Factory.UserFactory;
import org.dailygreen.dailygreen.model.user.AbstractUser;
import org.dailygreen.dailygreen.model.user.Role;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.model.user.types.Organizator;
import org.dailygreen.dailygreen.model.user.types.Participant;
import org.dailygreen.dailygreen.persistence.PersistenceFacade;
import org.dailygreen.dailygreen.persistence.PersistenceFacadeFactory;

/**
 * Serviço para operações relacionadas a usuários.
 * Agora utiliza a PersistenceFacade para acesso unificado aos dados.
 */
public class UserService {
    
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private final PersistenceFacade persistenceFacade;
    
    public UserService() {
        this.persistenceFacade = PersistenceFacadeFactory.createJsonPersistenceFacade();
    }
    
    public UserService(PersistenceFacade persistenceFacade) {
        this.persistenceFacade = persistenceFacade;
    }
    
    /**
     * Registra um novo usuário no sistema
     * @param nome Nome do usuário
     * @param email Email do usuário
     * @param password Senha do usuário
     * @param nomeOrganizador Nome da organização (se aplicável)
     * @param cnpj CNPJ da organização (se aplicável)
     * @param role Papel do usuário
     * @throws Exception Se houver erro no registro
     */
    public void registerNewUser(String nome, String email, String password, String nomeOrganizador, String cnpj, Role role) throws Exception {
        try {
            AbstractUser newUser = (AbstractUser) UserFactory.createUser(role, nome, email, password, nomeOrganizador, cnpj);
            
            // Criar objeto User para persistência
            User user = new User(role);
            if (newUser instanceof Participant) { user.setAccountParticipante((Participant) newUser); }
            else if (newUser instanceof Organizator) { user.setAccountOrganizator((Organizator) newUser); }
            boolean saved = persistenceFacade.saveUser(user);
            if (!saved) { throw new Exception("Falha ao salvar usuário no sistema"); }
            logger.info("Usuário registrado com sucesso: " + email);
        } catch (Exception e) {
            logger.severe("Erro ao registrar usuário: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Busca usuário por email
     * @param email Email do usuário
     * @return Usuário encontrado ou Optional.empty()
     */
    public Optional<User> findUserByEmail(String email) {
        return persistenceFacade.findUserByEmail(email);
    }
    
    /**
     * Valida login do usuário
     * @param email Email do usuário
     * @return true se login válido
     */
    public boolean validateLogin(String email) {
        return persistenceFacade.validateUserLogin(email);
    }
    
    /**
     * Lista todos os usuários
     * @return Lista de todos os usuários
     */
    public List<User> findAllUsers() {
        return persistenceFacade.findAllUsers();
    }
    
    /**
     * Atualiza dados do usuário
     * @param user Usuário com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean updateUser(User user) {
        return persistenceFacade.updateUser(user);
    }
    
    /**
     * Remove usuário por email
     * @param email Email do usuário a ser removido
     * @return true se removido com sucesso
     */
    public boolean deleteUser(String email) {
        return persistenceFacade.deleteUserByEmail(email);
    }
}