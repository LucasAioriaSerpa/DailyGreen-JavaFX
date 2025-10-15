package org.dailygreen.dailygreen.persistence;

import java.util.List;
import java.util.logging.Logger;

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
            
            // Reaction não tem ID próprio, então apenas adicionamos
            reactions.add(reaction);
            saveAll(reactions);
            logger.info("Reação salva com sucesso para post ID: " + reaction.getIdPost());
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao salvar reação: " + e.getMessage());
            return false;
        }
    }
}
