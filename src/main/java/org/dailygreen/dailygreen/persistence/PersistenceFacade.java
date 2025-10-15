package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;

import org.dailygreen.dailygreen.model.event.Event;
import org.dailygreen.dailygreen.model.event.EventAttendance;
import org.dailygreen.dailygreen.model.moderation.Report;
import org.dailygreen.dailygreen.model.post.Comment;
import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.model.post.Reaction;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.model.user.types.Administrator;
import org.dailygreen.dailygreen.model.user.types.Organizator;
import org.dailygreen.dailygreen.model.user.types.Participant;

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
    
    // ========== OPERAÇÕES DE ADMINISTRADOR ==========
    
    /**
     * Busca todos os administradores
     * @return Lista de todos os administradores
     */
    List<Administrator> findAllAdmins();
    
    /**
     * Busca administrador por email
     * @param email Email do administrador
     * @return Administrador encontrado ou Optional.empty()
     */
    Optional<Administrator> findAdminByEmail(String email);
    
    /**
     * Salva um novo administrador
     * @param admin Administrador a ser salvo
     * @return true se salvo com sucesso, false caso contrário
     */
    boolean saveAdmin(Administrator admin);
    
    /**
     * Remove administrador por email
     * @param email Email do administrador a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    boolean deleteAdminByEmail(String email);
    
    /**
     * Valida login do administrador
     * @param email Email do administrador
     * @param password Senha do administrador
     * @return true se login válido, false caso contrário
     */
    boolean validateAdminLogin(String email, String password);
    
    // ========== OPERAÇÕES DE PARTICIPANTE ==========
    
    /**
     * Busca todos os participantes
     * @return Lista de todos os participantes
     */
    List<Participant> findAllParticipants();
    
    /**
     * Busca participante por email
     * @param email Email do participante
     * @return Participante encontrado ou Optional.empty()
     */
    Optional<Participant> findParticipantByEmail(String email);
    
    /**
     * Salva um novo participante
     * @param participant Participante a ser salvo
     * @return true se salvo com sucesso, false caso contrário
     */
    boolean saveParticipant(Participant participant);
    
    /**
     * Salva uma lista de participantes
     * @param participants Lista de participantes a serem salvos
     * @return true se salvos com sucesso, false caso contrário
     */
    boolean saveAllParticipants(List<Participant> participants);
    
    /**
     * Remove participante por email
     * @param email Email do participante a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    boolean deleteParticipantByEmail(String email);
    
    /**
     * Valida login do participante
     * @param email Email do participante
     * @param password Senha do participante
     * @return true se login válido, false caso contrário
     */
    boolean validateParticipantLogin(String email, String password);
    
    // ========== OPERAÇÕES DE ORGANIZADOR ==========
    
    /**
     * Busca todos os organizadores
     * @return Lista de todos os organizadores
     */
    List<Organizator> findAllOrganizators();
    
    /**
     * Busca organizador por email
     * @param email Email do organizador
     * @return Organizador encontrado ou Optional.empty()
     */
    Optional<Organizator> findOrganizatorByEmail(String email);
    
    /**
     * Salva um novo organizador
     * @param organizator Organizador a ser salvo
     * @return true se salvo com sucesso, false caso contrário
     */
    boolean saveOrganizator(Organizator organizator);
    
    /**
     * Remove organizador por email
     * @param email Email do organizador a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    boolean deleteOrganizatorByEmail(String email);
    
    /**
     * Valida login do organizador
     * @param email Email do organizador
     * @param password Senha do organizador
     * @return true se login válido, false caso contrário
     */
    boolean validateOrganizatorLogin(String email, String password);
    
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
    
    // ========== OPERAÇÕES DE EVENTATTENDANCE ==========
    
    /**
     * Busca todos os EventAttendance (conversão de Event)
     * @return Lista de EventAttendance convertidos dos Event
     */
    List<EventAttendance> findAllEventAttendances();
    
    // ========== OPERAÇÕES DE COMENTÁRIO ==========
    
    /**
     * Busca todos os comentários
     * @return Lista de todos os comentários
     */
    List<Comment> findAllComments();
    
    /**
     * Busca comentários por post
     * @param postId ID do post
     * @return Lista de comentários do post
     */
    List<Comment> findCommentsByPost(long postId);
    
    /**
     * Busca último comentário por post
     * @param postId ID do post
     * @return Último comentário do post ou Optional.empty()
     */
    Optional<Comment> findLastCommentByPost(long postId);
    
    /**
     * Salva um novo comentário
     * @param comment Comentário a ser salvo
     * @return true se salvo com sucesso, false caso contrário
     */
    boolean saveComment(Comment comment);
    
    /**
     * Atualiza um comentário existente
     * @param comment Comentário com dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    boolean updateComment(Comment comment);
    
    /**
     * Remove um comentário
     * @param comment Comentário a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    boolean deleteComment(Comment comment);
    
    // ========== OPERAÇÕES DE REAÇÃO ==========
    
    /**
     * Busca todas as reações
     * @return Lista de todas as reações
     */
    List<Reaction> findAllReactions();
    
    /**
     * Busca reações por post
     * @param postId ID do post
     * @return Lista de reações do post
     */
    List<Reaction> findReactionsByPost(long postId);
    
    /**
     * Conta reações por tipo em um post
     * @param postId ID do post
     * @param tipo Tipo da reação
     * @return Número de reações do tipo especificado
     */
    long countReactionsByType(long postId, String tipo);
    
    /**
     * Busca tipo de reação do usuário em um post
     * @param userEmail Email do usuário
     * @param postId ID do post
     * @return Tipo de reação do usuário ou Optional.empty()
     */
    Optional<String> findUserReactionType(String userEmail, long postId);
    
    /**
     * Salva uma nova reação
     * @param reaction Reação a ser salva
     * @return true se salva com sucesso, false caso contrário
     */
    boolean saveReaction(Reaction reaction);
    
    /**
     * Salva ou alterna uma reação (toggle)
     * @param reaction Reação a ser salva ou alternada
     * @return true se operação bem-sucedida, false caso contrário
     */
    boolean saveOrToggleReaction(Reaction reaction);
    
    /**
     * Remove reação do usuário em um post
     * @param userEmail Email do usuário
     * @param postId ID do post
     * @return true se removida com sucesso, false caso contrário
     */
    boolean deleteReaction(String userEmail, long postId);
    
    // ========== OPERAÇÕES DE DENÚNCIA ==========
    
    /**
     * Busca todas as denúncias
     * @return Lista de todas as denúncias
     */
    List<Report> findAllReports();
    
    /**
     * Busca denúncia por ID
     * @param id ID da denúncia
     * @return Denúncia encontrada ou Optional.empty()
     */
    Optional<Report> findReportById(Integer id);
    
    /**
     * Busca denúncias por filtro
     * @param tipo Tipo do filtro (titulo, motivo, participante, status, data, suspenso, banido)
     * @param termo Termo de busca
     * @return Lista de denúncias filtradas
     */
    List<Report> findReportsByFilter(String tipo, String termo);
    
    /**
     * Salva uma nova denúncia
     * @param report Denúncia a ser salva
     * @return true se salva com sucesso, false caso contrário
     */
    boolean saveReport(Report report);
    
    /**
     * Atualiza uma denúncia existente
     * @param report Denúncia com dados atualizados
     * @return true se atualizada com sucesso, false caso contrário
     */
    boolean updateReport(Report report);
    
    /**
     * Remove uma denúncia
     * @param report Denúncia a ser removida
     * @return true se removida com sucesso, false caso contrário
     */
    boolean deleteReport(Report report);
    
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
