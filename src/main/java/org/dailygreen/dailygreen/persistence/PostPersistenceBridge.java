package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.dailygreen.dailygreen.model.post.Post;

/**
 * Bridge concreto para operações de persistência de posts.
 * Implementa operações específicas de post usando o padrão Bridge.
 */
public class PostPersistenceBridge extends AbstractPersistenceBridge<Post> {
    
    private static final Logger logger = Logger.getLogger(PostPersistenceBridge.class.getName());
    
    public PostPersistenceBridge(PersistenceImplementor<Post> implementor) {
        super(implementor);
    }
    
    public List<Post> findAll() {
        return readAll();
    }
    
    public Optional<Post> findById(long id) {
        return readAll().stream()
                .filter(post -> post.getID() == id)
                .findFirst();
    }
    
    public boolean save(Post post) {
        if (post == null) {
            logger.warning("Tentativa de salvar post nulo");
            return false;
        }
        
        try {
            List<Post> posts = readAll();
            
            // Gerar ID se necessário
            if (post.getID() == 0) {
                post.setID(generateNewId());
            }
            
            posts.add(post);
            saveAll(posts);
            logger.info("Post salvo com sucesso: ID " + post.getID());
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar post: " + e.getMessage());
            return false;
        }
    }
    
    public boolean update(Post post) {
        if (post == null) {
            logger.warning("Tentativa de atualizar post nulo");
            return false;
        }
        
        try {
            List<Post> posts = readAll();
            boolean updated = false;
            
            for (int i = 0; i < posts.size(); i++) {
                if (posts.get(i).getID() == post.getID()) {
                    posts.set(i, post);
                    updated = true;
                    break;
                }
            }
            
            if (updated) {
                saveAll(posts);
                logger.info("Post atualizado: ID " + post.getID());
                return true;
            } else {
                logger.warning("Post não encontrado para atualização: ID " + post.getID());
                return false;
            }
        } catch (Exception e) {
            logger.severe("Erro ao atualizar post: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteById(long id) {
        try {
            List<Post> posts = readAll();
            boolean removed = posts.removeIf(post -> post.getID() == id);
            
            if (removed) {
                saveAll(posts);
                logger.info("Post removido: ID " + id);
            } else {
                logger.warning("Post não encontrado para remoção: ID " + id);
            }
            
            return removed;
        } catch (Exception e) {
            logger.severe("Erro ao deletar post: " + e.getMessage());
            return false;
        }
    }
}
