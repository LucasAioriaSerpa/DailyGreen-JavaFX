package org.dailygreen.dailygreen.persistence;

import org.dailygreen.dailygreen.model.event.Event;
import org.dailygreen.dailygreen.model.moderation.Report;
import org.dailygreen.dailygreen.model.post.Comment;
import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.model.post.Reaction;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.model.user.types.Administrator;
import org.dailygreen.dailygreen.model.user.types.Organizator;
import org.dailygreen.dailygreen.model.user.types.Participant;

/**
 * Factory para criar instâncias da PersistenceFacade com diferentes configurações.
 * Permite escolher entre diferentes implementações de persistência (JSON, memória, etc.)
 * sem modificar o código cliente.
 */
public class PersistenceFacadeFactory {
    
    /**
     * Enum para definir tipos de persistência disponíveis
     */
    public enum PersistenceType {
        JSON_FILE,
        IN_MEMORY
    }
    
    /**
     * Cria uma instância da PersistenceFacade com persistência em arquivos JSON
     * @return PersistenceFacade configurada para JSON
     */
    public static PersistenceFacade createJsonPersistenceFacade() {
        return new PersistenceFacadeImpl();
    }
    
    /**
     * Cria uma instância da PersistenceFacade com persistência em memória
     * @return PersistenceFacade configurada para memória
     */
    public static PersistenceFacade createInMemoryPersistenceFacade() {
        return new PersistenceFacadeImpl(
            new InMemoryPersistenceImplementor<>("users"),
            new InMemoryPersistenceImplementor<>("posts"),
            new InMemoryPersistenceImplementor<>("events"),
            new InMemoryPersistenceImplementor<>("comments"),
            new InMemoryPersistenceImplementor<>("reactions"),
            new InMemoryPersistenceImplementor<>("reports"),
            new InMemoryPersistenceImplementor<>("admins"),
            new InMemoryPersistenceImplementor<>("participants"),
            new InMemoryPersistenceImplementor<>("organizators")
        );
    }
    
    /**
     * Cria uma instância da PersistenceFacade baseada no tipo especificado
     * @param type Tipo de persistência desejado
     * @return PersistenceFacade configurada conforme o tipo
     */
    public static PersistenceFacade createPersistenceFacade(PersistenceType type) {
        switch (type) {
            case JSON_FILE:
                return createJsonPersistenceFacade();
            case IN_MEMORY:
                return createInMemoryPersistenceFacade();
            default:
                throw new IllegalArgumentException("Tipo de persistência não suportado: " + type);
        }
    }
    
    /**
     * Cria uma instância da PersistenceFacade com configuração personalizada
     * @param userImpl Implementação para usuários
     * @param postImpl Implementação para posts
     * @param eventImpl Implementação para eventos
     * @param commentImpl Implementação para comentários
     * @param reactionImpl Implementação para reações
     * @param reportImpl Implementação para denúncias
     * @param adminImpl Implementação para administradores
     * @param participantImpl Implementação para participantes
     * @param organizatorImpl Implementação para organizadores
     * @return PersistenceFacade com implementações personalizadas
     */
    public static PersistenceFacade createCustomPersistenceFacade(
            PersistenceImplementor<User> userImpl,
            PersistenceImplementor<Post> postImpl,
            PersistenceImplementor<Event> eventImpl,
            PersistenceImplementor<Comment> commentImpl,
            PersistenceImplementor<Reaction> reactionImpl,
            PersistenceImplementor<Report> reportImpl,
            PersistenceImplementor<Administrator> adminImpl,
            PersistenceImplementor<Participant> participantImpl,
            PersistenceImplementor<Organizator> organizatorImpl) {
        return new PersistenceFacadeImpl(userImpl, postImpl, eventImpl, commentImpl, reactionImpl, reportImpl, adminImpl, participantImpl, organizatorImpl);
    }
}
