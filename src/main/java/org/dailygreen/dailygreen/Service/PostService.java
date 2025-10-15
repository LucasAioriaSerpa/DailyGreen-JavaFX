package org.dailygreen.dailygreen.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.persistence.PersistenceFacade;
import org.dailygreen.dailygreen.persistence.PersistenceFacadeFactory;

/**
 * Serviço para operações relacionadas a posts.
 * Utiliza a PersistenceFacade para acesso unificado aos dados.
 */
public class PostService {
    
    private static final Logger logger = Logger.getLogger(PostService.class.getName());
    private final PersistenceFacade persistenceFacade;
    
    public PostService() {
        this.persistenceFacade = PersistenceFacadeFactory.createJsonPersistenceFacade();
    }
    
    public PostService(PersistenceFacade persistenceFacade) {
        this.persistenceFacade = persistenceFacade;
    }
    
    /**
     * Cria um novo post
     * @param post Post a ser criado
     * @return true se criado com sucesso
     */
    public boolean createPost(Post post) {
        try {
            boolean saved = persistenceFacade.savePost(post);
            if (saved) {
                logger.info("Post criado com sucesso: ID " + post.getID());
            }
            return saved;
        } catch (Exception e) {
            logger.severe("Erro ao criar post: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Busca post por ID
     * @param id ID do post
     * @return Post encontrado ou Optional.empty()
     */
    public Optional<Post> findPostById(long id) {
        return persistenceFacade.findPostById(id);
    }
    
    /**
     * Lista todos os posts
     * @return Lista de todos os posts
     */
    public List<Post> findAllPosts() {
        return persistenceFacade.findAllPosts();
    }
    
    /**
     * Atualiza um post existente
     * @param post Post com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean updatePost(Post post) {
        try {
            boolean updated = persistenceFacade.updatePost(post);
            if (updated) {
                logger.info("Post atualizado com sucesso: ID " + post.getID());
            }
            return updated;
        } catch (Exception e) {
            logger.severe("Erro ao atualizar post: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Remove post por ID
     * @param id ID do post a ser removido
     * @return true se removido com sucesso
     */
    public boolean deletePost(long id) {
        try {
            boolean deleted = persistenceFacade.deletePostById(id);
            if (deleted) {
                logger.info("Post removido com sucesso: ID " + id);
            }
            return deleted;
        } catch (Exception e) {
            logger.severe("Erro ao remover post: " + e.getMessage());
            return false;
        }
    }
}
