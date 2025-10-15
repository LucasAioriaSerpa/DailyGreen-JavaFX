package org.dailygreen.dailygreen.persistence;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.dailygreen.dailygreen.model.post.Comment;

/**
 * Bridge concreto para operações de persistência de comentários.
 * Implementa operações específicas de comentário usando o padrão Bridge.
 */
public class CommentPersistenceBridge extends AbstractPersistenceBridge<Comment> {
    
    private static final Logger logger = Logger.getLogger(CommentPersistenceBridge.class.getName());
    
    public CommentPersistenceBridge(PersistenceImplementor<Comment> implementor) {
        super(implementor);
    }
    
    public List<Comment> findAll() {
        return readAll();
    }
    
    public boolean save(Comment comment) {
        if (comment == null) {
            logger.warning("Tentativa de salvar comentário nulo");
            return false;
        }
        
        try {
            List<Comment> comments = readAll();
            comments.add(comment);
            saveAll(comments);
            logger.info("Comentário salvo com sucesso para post ID: " + comment.getIdPost());
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar comentário: " + e.getMessage());
            return false;
        }
    }
    
    public boolean update(Comment comment) {
        if (comment == null) {
            logger.warning("Tentativa de atualizar comentário nulo");
            return false;
        }
        
        try {
            List<Comment> comments = readAll();
            boolean updated = false;
            
            for (int i = 0; i < comments.size(); i++) {
                Comment c = comments.get(i);
                if (c.getAutorEmail().equals(comment.getAutorEmail()) && c.getIdPost() == comment.getIdPost()) {
                    comments.set(i, comment);
                    updated = true;
                    break;
                }
            }
            
            if (updated) {
                saveAll(comments);
                logger.info("Comentário atualizado para post ID: " + comment.getIdPost());
                return true;
            } else {
                logger.warning("Comentário não encontrado para atualização");
                return false;
            }
        } catch (Exception e) {
            logger.severe("Erro ao atualizar comentário: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(Comment comment) {
        if (comment == null) {
            logger.warning("Tentativa de deletar comentário nulo");
            return false;
        }
        
        try {
            List<Comment> comments = readAll();
            boolean removed = comments.removeIf(c -> 
                c.getAutorEmail().equals(comment.getAutorEmail()) && 
                c.getIdPost() == comment.getIdPost()
            );
            
            if (removed) {
                saveAll(comments);
                logger.info("Comentário removido para post ID: " + comment.getIdPost());
            } else {
                logger.warning("Comentário não encontrado para remoção");
            }
            
            return removed;
        } catch (Exception e) {
            logger.severe("Erro ao deletar comentário: " + e.getMessage());
            return false;
        }
    }
    
    public Optional<Comment> findByPost(long postId) {
        List<Comment> comments = readAll();
        return comments.stream()
                .filter(c -> c.getIdPost() == postId)
                .max(Comparator.comparing(Comment::getData));
    }
    
    public List<Comment> findAllByPost(long postId) {
        return readAll().stream()
                .filter(c -> c.getIdPost() == postId)
                .collect(Collectors.toList());
    }
}
