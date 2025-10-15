package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;

import org.dailygreen.dailygreen.model.event.Event;
import org.dailygreen.dailygreen.model.moderation.Report;
import org.dailygreen.dailygreen.model.post.Comment;
import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.model.post.Reaction;
import org.dailygreen.dailygreen.model.user.User;

/**
 * Interface Facade que centraliza e simplifica o acesso às operações de persistência.
 * Esta fachada esconde a complexidade dos repositórios individuais e fornece uma
 * interface única e consistente para toda a aplicação.
 */
public interface PersistenceFacade {
    
    // ========== OPERAÇÕES DE USUÁRIO ==========
    
    /**
     * Busca todos os usuários
     * @return Lista de todos os usuários
     */
    List<User> findAllUsers();
    
    /**
     * Busca usuário por email
     * @param email Email do usuário
     * @return Usuário encontrado ou Optional.empty()
     */
    Optional<User> findUserByEmail(String email);
    
    /**
     * Salva um novo usuário
     * @param user Usuário a ser salvo
     * @return true se salvo com sucesso, false caso contrário
     */
    boolean saveUser(User user);
    
    /**
     * Atualiza um usuário existente
     * @param user Usuário com dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    boolean updateUser(User user);
    
    /**
     * Remove usuário por email
     * @param email Email do usuário a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    boolean deleteUserByEmail(String email);
    
    /**
     * Valida login do usuário
     * @param email Email do usuário
     * @return true se login válido, false caso contrário
     */
    boolean validateUserLogin(String email);
    
    // ========== OPERAÇÕES DE POST ==========
    
    /**
     * Busca todos os posts
     * @return Lista de todos os posts
     */
    List<Post> findAllPosts();
    
    /**
     * Busca post por ID
     * @param id ID do post
     * @return Post encontrado ou Optional.empty()
     */
    Optional<Post> findPostById(long id);
    
    /**
     * Salva um novo post
     * @param post Post a ser salvo
     * @return true se salvo com sucesso, false caso contrário
     */
    boolean savePost(Post post);
    
    /**
     * Atualiza um post existente
     * @param post Post com dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    boolean updatePost(Post post);
    
    /**
     * Remove post por ID
     * @param id ID do post a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    boolean deletePostById(long id);
    
    // ========== OPERAÇÕES DE EVENTO ==========
    
    /**
     * Busca todos os eventos
     * @return Lista de todos os eventos
     */
    List<Event> findAllEvents();
    
    /**
     * Busca evento por ID
     * @param id ID do evento
     * @return Evento encontrado ou Optional.empty()
     */
    Optional<Event> findEventById(long id);
    
    /**
     * Salva um novo evento
     * @param event Evento a ser salvo
     * @return true se salvo com sucesso, false caso contrário
     */
    boolean saveEvent(Event event);
    
    /**
     * Remove evento por ID
     * @param id ID do evento a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    boolean deleteEventById(long id);
    
    // ========== OPERAÇÕES DE COMENTÁRIO ==========
    
    /**
     * Busca todos os comentários
     * @return Lista de todos os comentários
     */
    List<Comment> findAllComments();
    
    /**
     * Salva um novo comentário
     * @param comment Comentário a ser salvo
     * @return true se salvo com sucesso, false caso contrário
     */
    boolean saveComment(Comment comment);
    
    // ========== OPERAÇÕES DE REAÇÃO ==========
    
    /**
     * Busca todas as reações
     * @return Lista de todas as reações
     */
    List<Reaction> findAllReactions();
    
    /**
     * Salva uma nova reação
     * @param reaction Reação a ser salva
     * @return true se salva com sucesso, false caso contrário
     */
    boolean saveReaction(Reaction reaction);
    
    // ========== OPERAÇÕES DE DENÚNCIA ==========
    
    /**
     * Busca todas as denúncias
     * @return Lista de todas as denúncias
     */
    List<Report> findAllReports();
    
    /**
     * Salva uma nova denúncia
     * @param report Denúncia a ser salva
     * @return true se salva com sucesso, false caso contrário
     */
    boolean saveReport(Report report);
    
    // ========== OPERAÇÕES DE MANUTENÇÃO ==========
    
    /**
     * Inicializa todos os arquivos de persistência
     * @return true se inicialização bem-sucedida
     */
    boolean initializePersistence();
    
    /**
     * Limpa todos os dados (útil para testes)
     */
    void clearAllData();
}
