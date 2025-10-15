package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

import com.google.gson.reflect.TypeToken;

/**
 * Implementação concreta da PersistenceFacade.
 * Esta classe centraliza todas as operações de persistência e utiliza o padrão Bridge
 * para abstrair as implementações específicas de armazenamento.
 */
public class PersistenceFacadeImpl implements PersistenceFacade {
    
    private static final Logger logger = Logger.getLogger(PersistenceFacadeImpl.class.getName());
    
    // Configurações de arquivos
    private static final String USERS_FILE = "src/main/resources/db_dailygreen/users.json";
    private static final String POSTS_FILE = "src/main/resources/db_dailygreen/posts.json";
    private static final String POSTS_ID_FILE = "src/main/resources/db_dailygreen/posts_last_id.txt";
    private static final String EVENTS_FILE = "src/main/resources/db_dailygreen/events.json";
    private static final String EVENTS_ID_FILE = "src/main/resources/db_dailygreen/events_last_id.txt";
    private static final String COMMENTS_FILE = "src/main/resources/db_dailygreen/comments.json";
    private static final String COMMENTS_ID_FILE = "src/main/resources/db_dailygreen/comments_last_id.txt";
    private static final String REACTIONS_FILE = "src/main/resources/db_dailygreen/reactions.json";
    private static final String REACTIONS_ID_FILE = "src/main/resources/db_dailygreen/reactions_last_id.txt";
    private static final String REPORTS_FILE = "src/main/resources/db_dailygreen/reports.json";
    private static final String REPORTS_ID_FILE = "src/main/resources/db_dailygreen/reports_last_id.txt";
    private static final String ADMINS_FILE = "src/main/resources/db_dailygreen/admins.json";
    private static final String ADMINS_ID_FILE = "src/main/resources/db_dailygreen/admins_last_id.txt";
    private static final String PARTICIPANTS_FILE = "src/main/resources/db_dailygreen/participants.json";
    private static final String PARTICIPANTS_ID_FILE = "src/main/resources/db_dailygreen/participants_last_id.txt";
    private static final String ORGANIZATORS_FILE = "src/main/resources/db_dailygreen/organizators.json";
    private static final String ORGANIZATORS_ID_FILE = "src/main/resources/db_dailygreen/organizators_last_id.txt";
    
    // Implementações Bridge para cada entidade
    private final UserPersistenceBridge userBridge;
    private final PostPersistenceBridge postBridge;
    private final EventPersistenceBridge eventBridge;
    private final CommentPersistenceBridge commentBridge;
    private final ReactionPersistenceBridge reactionBridge;
    private final ReportPersistenceBridge reportBridge;
    private final AdminPersistenceBridge adminBridge;
    private final ParticipantPersistenceBridge participantBridge;
    private final OrganizatorPersistenceBridge organizatorBridge;
    
    public PersistenceFacadeImpl() {
        // Inicializar implementações Bridge com JsonPersistenceImplementor
        this.userBridge = new UserPersistenceBridge(
            new JsonPersistenceImplementor<>(USERS_FILE, "", new TypeToken<List<User>>(){}.getType())
        );
        this.postBridge = new PostPersistenceBridge(
            new JsonPersistenceImplementor<>(POSTS_FILE, POSTS_ID_FILE, new TypeToken<List<Post>>(){}.getType())
        );
        this.eventBridge = new EventPersistenceBridge(
            new JsonPersistenceImplementor<>(EVENTS_FILE, EVENTS_ID_FILE, new TypeToken<List<Event>>(){}.getType())
        );
        this.commentBridge = new CommentPersistenceBridge(
            new JsonPersistenceImplementor<>(COMMENTS_FILE, COMMENTS_ID_FILE, new TypeToken<List<Comment>>(){}.getType())
        );
        this.reactionBridge = new ReactionPersistenceBridge(
            new JsonPersistenceImplementor<>(REACTIONS_FILE, REACTIONS_ID_FILE, new TypeToken<List<Reaction>>(){}.getType())
        );
        this.reportBridge = new ReportPersistenceBridge(
            new JsonPersistenceImplementor<>(REPORTS_FILE, REPORTS_ID_FILE, new TypeToken<List<Report>>(){}.getType())
        );
        this.adminBridge = new AdminPersistenceBridge(
            new JsonPersistenceImplementor<>(ADMINS_FILE, ADMINS_ID_FILE, new TypeToken<List<Administrator>>(){}.getType())
        );
        this.participantBridge = new ParticipantPersistenceBridge(
            new JsonPersistenceImplementor<>(PARTICIPANTS_FILE, PARTICIPANTS_ID_FILE, new TypeToken<List<Participant>>(){}.getType())
        );
        this.organizatorBridge = new OrganizatorPersistenceBridge(
            new JsonPersistenceImplementor<>(ORGANIZATORS_FILE, ORGANIZATORS_ID_FILE, new TypeToken<List<Organizator>>(){}.getType())
        );
    }
    
    // Construtor alternativo para permitir diferentes implementações
    public PersistenceFacadeImpl(PersistenceImplementor<User> userImpl,
                                PersistenceImplementor<Post> postImpl,
                                PersistenceImplementor<Event> eventImpl,
                                PersistenceImplementor<Comment> commentImpl,
                                PersistenceImplementor<Reaction> reactionImpl,
                                PersistenceImplementor<Report> reportImpl,
                                PersistenceImplementor<Administrator> adminImpl,
                                PersistenceImplementor<Participant> participantImpl,
                                PersistenceImplementor<Organizator> organizatorImpl) {
        this.userBridge = new UserPersistenceBridge(userImpl);
        this.postBridge = new PostPersistenceBridge(postImpl);
        this.eventBridge = new EventPersistenceBridge(eventImpl);
        this.commentBridge = new CommentPersistenceBridge(commentImpl);
        this.reactionBridge = new ReactionPersistenceBridge(reactionImpl);
        this.reportBridge = new ReportPersistenceBridge(reportImpl);
        this.adminBridge = new AdminPersistenceBridge(adminImpl);
        this.participantBridge = new ParticipantPersistenceBridge(participantImpl);
        this.organizatorBridge = new OrganizatorPersistenceBridge(organizatorImpl);
    }
    
    // ========== IMPLEMENTAÇÃO DOS MÉTODOS DA FACADE ==========
    
    // ========== OPERAÇÕES DE USUÁRIO ==========
    
    @Override
    public List<User> findAllUsers() {
        return userBridge.findAll();
    }
    
    @Override
    public Optional<User> findUserByEmail(String email) {
        return userBridge.findByEmail(email);
    }
    
    @Override
    public boolean saveUser(User user) {
        return userBridge.save(user);
    }
    
    @Override
    public boolean updateUser(User user) {
        return userBridge.update(user);
    }
    
    @Override
    public boolean deleteUserByEmail(String email) {
        return userBridge.deleteByEmail(email);
    }
    
    @Override
    public boolean validateUserLogin(String email) {
        return userBridge.validateLogin(email);
    }
    
    // ========== OPERAÇÕES DE ADMINISTRADOR ==========
    
    @Override
    public List<Administrator> findAllAdmins() {
        return adminBridge.findAll();
    }
    
    @Override
    public Optional<Administrator> findAdminByEmail(String email) {
        return adminBridge.findByEmail(email);
    }
    
    @Override
    public boolean saveAdmin(Administrator admin) {
        return adminBridge.save(admin);
    }
    
    @Override
    public boolean deleteAdminByEmail(String email) {
        return adminBridge.deleteByEmail(email);
    }
    
    @Override
    public boolean validateAdminLogin(String email, String password) {
        return adminBridge.validateLogin(email, password);
    }
    
    // ========== OPERAÇÕES DE PARTICIPANTE ==========
    
    @Override
    public List<Participant> findAllParticipants() {
        return participantBridge.findAll();
    }
    
    @Override
    public Optional<Participant> findParticipantByEmail(String email) {
        return participantBridge.findByEmail(email);
    }
    
    @Override
    public boolean saveParticipant(Participant participant) {
        return participantBridge.save(participant);
    }
    
    @Override
    public boolean saveAllParticipants(List<Participant> participants) {
        try {
            participantBridge.saveAll(participants);
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar lista de participantes: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean deleteParticipantByEmail(String email) {
        return participantBridge.deleteByEmail(email);
    }
    
    @Override
    public boolean validateParticipantLogin(String email, String password) {
        return participantBridge.validateLogin(email, password);
    }
    
    // ========== OPERAÇÕES DE ORGANIZADOR ==========
    
    @Override
    public List<Organizator> findAllOrganizators() {
        return organizatorBridge.findAll();
    }
    
    @Override
    public Optional<Organizator> findOrganizatorByEmail(String email) {
        return organizatorBridge.findByEmail(email);
    }
    
    @Override
    public boolean saveOrganizator(Organizator organizator) {
        return organizatorBridge.save(organizator);
    }
    
    @Override
    public boolean deleteOrganizatorByEmail(String email) {
        return organizatorBridge.deleteByEmail(email);
    }
    
    @Override
    public boolean validateOrganizatorLogin(String email, String password) {
        return organizatorBridge.validateLogin(email, password);
    }
    
    // ========== OPERAÇÕES DE POST ==========
    
    @Override
    public List<Post> findAllPosts() {
        return postBridge.findAll();
    }
    
    @Override
    public Optional<Post> findPostById(long id) {
        return postBridge.findById(id);
    }
    
    @Override
    public boolean savePost(Post post) {
        return postBridge.save(post);
    }
    
    @Override
    public boolean updatePost(Post post) {
        return postBridge.update(post);
    }
    
    @Override
    public boolean deletePostById(long id) {
        return postBridge.deleteById(id);
    }
    
    // ========== OPERAÇÕES DE EVENTO ==========
    
    @Override
    public List<Event> findAllEvents() {
        return eventBridge.findAll();
    }
    
    @Override
    public Optional<Event> findEventById(long id) {
        return eventBridge.findById(id);
    }
    
    @Override
    public boolean saveEvent(Event event) {
        return eventBridge.save(event);
    }
    
    @Override
    public boolean deleteEventById(long id) {
        return eventBridge.deleteById(id);
    }
    
    @Override
    public List<EventAttendance> findAllEventAttendances() {
        return eventBridge.findAll().stream()
                .map(event -> new EventAttendance(
                    event.getTitulo(),
                    event.getDescricao(),
                    event.getDataHoraInicio() != null ? event.getDataHoraInicio().toString() : "Data não definida"
                ))
                .collect(Collectors.toList());
    }
    
    // ========== OPERAÇÕES DE COMENTÁRIO ==========
    
    @Override
    public List<Comment> findAllComments() {
        return commentBridge.findAll();
    }
    
    @Override
    public List<Comment> findCommentsByPost(long postId) {
        return commentBridge.findAllByPost(postId);
    }
    
    @Override
    public Optional<Comment> findLastCommentByPost(long postId) {
        return commentBridge.findByPost(postId);
    }
    
    @Override
    public boolean saveComment(Comment comment) {
        return commentBridge.save(comment);
    }
    
    @Override
    public boolean updateComment(Comment comment) {
        return commentBridge.update(comment);
    }
    
    @Override
    public boolean deleteComment(Comment comment) {
        return commentBridge.delete(comment);
    }
    
    // ========== OPERAÇÕES DE REAÇÃO ==========
    
    @Override
    public List<Reaction> findAllReactions() {
        return reactionBridge.findAll();
    }
    
    @Override
    public List<Reaction> findReactionsByPost(long postId) {
        return reactionBridge.findByPost(postId);
    }
    
    @Override
    public long countReactionsByType(long postId, String tipo) {
        return reactionBridge.countByTarget(postId, tipo);
    }
    
    @Override
    public Optional<String> findUserReactionType(String userEmail, long postId) {
        return reactionBridge.findUserReactionType(userEmail, postId);
    }
    
    @Override
    public boolean saveReaction(Reaction reaction) {
        return reactionBridge.save(reaction);
    }
    
    @Override
    public boolean saveOrToggleReaction(Reaction reaction) {
        return reactionBridge.saveOrToggle(reaction);
    }
    
    @Override
    public boolean deleteReaction(String userEmail, long postId) {
        return reactionBridge.delete(userEmail, postId);
    }
    
    // ========== OPERAÇÕES DE DENÚNCIA ==========
    
    @Override
    public List<Report> findAllReports() {
        return reportBridge.findAll();
    }
    
    @Override
    public Optional<Report> findReportById(Integer id) {
        return reportBridge.findById(id);
    }
    
    @Override
    public List<Report> findReportsByFilter(String tipo, String termo) {
        return reportBridge.findByFilter(tipo, termo);
    }
    
    @Override
    public boolean saveReport(Report report) {
        return reportBridge.save(report);
    }
    
    @Override
    public boolean updateReport(Report report) {
        return reportBridge.update(report);
    }
    
    @Override
    public boolean deleteReport(Report report) {
        return reportBridge.delete(report);
    }
    
    // ========== OPERAÇÕES DE MANUTENÇÃO ==========
    
    @Override
    public boolean initializePersistence() {
        try {
            userBridge.checkOrCreateFile();
            postBridge.checkOrCreateFile();
            eventBridge.checkOrCreateFile();
            commentBridge.checkOrCreateFile();
            reactionBridge.checkOrCreateFile();
            reportBridge.checkOrCreateFile();
            adminBridge.checkOrCreateFile();
            participantBridge.checkOrCreateFile();
            organizatorBridge.checkOrCreateFile();
            logger.info("Persistência inicializada com sucesso");
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao inicializar persistência: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public void clearAllData() {
        // Implementação para limpar todos os dados (útil para testes)
        logger.info("Limpando todos os dados de persistência");
        // Aqui você pode implementar a lógica para limpar todos os dados
        // ou usar implementações em memória para testes
    }
}
