package org.dailygreen.dailygreen.persistence;

import java.util.List;

/**
 * Classe abstrata que define operações básicas de persistência.
 * Este é o lado "Abstraction" do padrão Bridge.
 * 
 * @param <T> Tipo da entidade a ser persistida
 */
public abstract class AbstractPersistenceBridge<T> {
    protected final PersistenceImplementor<T> implementor;
    protected AbstractPersistenceBridge(PersistenceImplementor<T> implementor) {
        this.implementor = implementor;
    }
    
    /**
     * Lê todos os registros
     * @return Lista de entidades
     */
    protected List<T> readAll() {
        return implementor.readAll();
    }
    
    /**
     * Salva todos os registros
     * @param entities Lista de entidades para salvar
     */
    protected void saveAll(List<T> entities) {
        implementor.saveAll(entities);
    }
    
    /**
     * Verifica se o arquivo existe, criando-o se necessário
     * @return true se o arquivo já existia, false se foi criado
     */
    protected boolean checkOrCreateFile() { return implementor.checkOrCreateFile(); }
    
    /**
     * Gera um novo ID único
     * @return Novo ID gerado
     */
    protected long generateNewId() {
        return implementor.generateNewId();
    }
}
