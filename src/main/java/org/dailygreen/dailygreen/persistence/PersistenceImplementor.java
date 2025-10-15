package org.dailygreen.dailygreen.persistence;

import java.util.List;

/**
 * Interface que define o contrato para implementações concretas de persistência.
 * Este é o lado "Implementor" do padrão Bridge.
 * 
 * @param <T> Tipo da entidade a ser persistida
 */
public interface PersistenceImplementor<T> {
    
    /**
     * Lê todos os registros do armazenamento
     * @return Lista de entidades
     */
    List<T> readAll();
    
    /**
     * Salva todos os registros no armazenamento
     * @param entities Lista de entidades para salvar
     */
    void saveAll(List<T> entities);
    
    /**
     * Verifica se o arquivo de dados existe, criando-o se necessário
     * @return true se o arquivo já existia, false se foi criado
     */
    boolean checkOrCreateFile();
    
    /**
     * Gera um novo ID único para a entidade
     * @return Novo ID gerado
     */
    long generateNewId();
}
