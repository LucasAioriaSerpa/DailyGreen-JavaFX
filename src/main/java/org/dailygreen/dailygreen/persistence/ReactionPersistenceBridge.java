package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.dailygreen.dailygreen.model.post.Reaction;

/**
 * Bridge concreto para operações de persistência de reações.
 * Implementa operações específicas de reação usando o padrão Bridge.
 */
public class ReactionPersistenceBridge extends AbstractPersistenceBridge<Reaction> {
    
    private static final Logger logger = Logger.getLogger(ReactionPersistenceBridge.class.getName());
    
    public ReactionPersistenceBridge(PersistenceImplementor<Reaction> implementor) {
        super(implementor);
    }
    
    public List<Reaction> findAll() {
        return readAll();
    }
    
    public boolean save(Reaction reaction) {
        if (reaction == null) {
            logger.warning("Tentativa de salvar reação nula");
            return false;
        }
        
        try {
            List<Reaction> reactions = readAll();
            reactions.add(reaction);
            saveAll(reactions);
            logger.info("Reação salva com sucesso para post ID: " + reaction.getIdPost());
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar reação: " + e.getMessage());
            return false;
        }
    }
    
    public boolean saveOrToggle(Reaction reaction) {
        if (reaction == null) {
            logger.warning("Tentativa de salvar/toggle reação nula");
            return false;
        }
        
        try {
            List<Reaction> reactions = readAll();
            
            // Remove reação existente do mesmo tipo do mesmo usuário no mesmo post
            boolean sameReactionExists = reactions.removeIf(r -> 
                r.getIdPost() == reaction.getIdPost() && 
                r.getAutorEmail().equals(reaction.getAutorEmail()) && 
                r.getTipo().equalsIgnoreCase(reaction.getTipo())
            );
            
            if (sameReactionExists) {
                saveAll(reactions);
                logger.info("Reação removida (toggle): " + reaction.getTipo() + " para post ID: " + reaction.getIdPost());
                return true;
            }
            
            // Remove qualquer outra reação do mesmo usuário no mesmo post
            reactions.removeIf(r -> 
                r.getIdPost() == reaction.getIdPost() && 
                r.getAutorEmail().equals(reaction.getAutorEmail())
            );
            
            // Adiciona a nova reação
            reactions.add(reaction);
            saveAll(reactions);
            logger.info("Reação salva: " + reaction.getTipo() + " para post ID: " + reaction.getIdPost());
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar/toggle reação: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(String autorEmail, long idPost) {
        if (autorEmail == null || autorEmail.trim().isEmpty()) {
            logger.warning("Tentativa de deletar reação com email nulo ou vazio");
            return false;
        }
        
        try {
            List<Reaction> reactions = readAll();
            boolean removed = reactions.removeIf(r -> 
                r.getAutorEmail().equals(autorEmail) && r.getIdPost() == idPost
            );
            
            if (removed) {
                saveAll(reactions);
                logger.info("Reação removida para post ID: " + idPost);
            } else {
                logger.warning("Reação não encontrada para remoção");
            }
            
            return removed;
        } catch (Exception e) {
            logger.severe("Erro ao deletar reação: " + e.getMessage());
            return false;
        }
    }
    
    public List<Reaction> findByPost(long idPost) {
        return readAll().stream()
                .filter(r -> r.getIdPost() == idPost)
                .collect(Collectors.toList());
    }
    
    public long countByTarget(long idPost, String tipo) {
        return readAll().stream()
                .filter(r -> r.getIdPost() == idPost && r.getTipo().equalsIgnoreCase(tipo))
                .count();
    }
    
    public Optional<String> findUserReactionType(String autorEmail, long idPost) {
        return readAll().stream()
                .filter(r -> r.getAutorEmail().equals(autorEmail) && r.getIdPost() == idPost)
                .map(Reaction::getTipo)
                .findFirst();
    }
}
