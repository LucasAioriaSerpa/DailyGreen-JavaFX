package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.dailygreen.dailygreen.model.event.Event;
import org.dailygreen.dailygreen.model.moderation.Report;
import org.dailygreen.dailygreen.model.post.Comment;
import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.model.post.Reaction;
import org.dailygreen.dailygreen.model.user.User;

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
    
    // Implementações Bridge para cada entidade
    private final UserPersistenceBridge userBridge;
    private final PostPersistenceBridge postBridge;
    private final EventPersistenceBridge eventBridge;
    private final CommentPersistenceBridge commentBridge;
    private final ReactionPersistenceBridge reactionBridge;
    private final ReportPersistenceBridge reportBridge;
    
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
    }
    
    // Construtor alternativo para permitir diferentes implementações
    public PersistenceFacadeImpl(PersistenceImplementor<User> userImpl,
                                PersistenceImplementor<Post> postImpl,
                                PersistenceImplementor<Event> eventImpl,
                                PersistenceImplementor<Comment> commentImpl,
                                PersistenceImplementor<Reaction> reactionImpl,
                                PersistenceImplementor<Report> reportImpl) {
        this.userBridge = new UserPersistenceBridge(userImpl);
        this.postBridge = new PostPersistenceBridge(postImpl);
        this.eventBridge = new EventPersistenceBridge(eventImpl);
        this.commentBridge = new CommentPersistenceBridge(commentImpl);
        this.reactionBridge = new ReactionPersistenceBridge(reactionImpl);
        this.reportBridge = new ReportPersistenceBridge(reportImpl);
    }
    
    // ========== IMPLEMENTAÇÃO DOS MÉTODOS DA FACADE ==========
    
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
    public List<Comment> findAllComments() {
        return commentBridge.findAll();
    }
    
    @Override
    public boolean saveComment(Comment comment) {
        return commentBridge.save(comment);
    }
    
    @Override
    public List<Reaction> findAllReactions() {
        return reactionBridge.findAll();
    }
    
    @Override
    public boolean saveReaction(Reaction reaction) {
        return reactionBridge.save(reaction);
    }
    
    @Override
    public List<Report> findAllReports() {
        return reportBridge.findAll();
    }
    
    @Override
    public boolean saveReport(Report report) {
        return reportBridge.save(report);
    }
    
    @Override
    public boolean initializePersistence() {
        try {
            userBridge.checkOrCreateFile();
            postBridge.checkOrCreateFile();
            eventBridge.checkOrCreateFile();
            commentBridge.checkOrCreateFile();
            reactionBridge.checkOrCreateFile();
            reportBridge.checkOrCreateFile();
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
