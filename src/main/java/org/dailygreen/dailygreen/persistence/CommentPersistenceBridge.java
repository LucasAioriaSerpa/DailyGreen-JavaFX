package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.logging.Logger;

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
            
            // Comment não tem ID próprio, então apenas adicionamos
            comments.add(comment);
            saveAll(comments);
            logger.info("Comentário salvo com sucesso para post ID: " + comment.getIdPost());
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar comentário: " + e.getMessage());
            return false;
        }
    }
}
