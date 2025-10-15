package org.dailygreen.dailygreen.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * Implementação concreta do PersistenceImplementor usando armazenamento em memória.
 * Esta é uma implementação alternativa que demonstra a flexibilidade do padrão Bridge.
 * 
 * @param <T> Tipo da entidade a ser persistida
 */
public class InMemoryPersistenceImplementor<T> implements PersistenceImplementor<T> {
    
    private static final Logger logger = Logger.getLogger(InMemoryPersistenceImplementor.class.getName());
    
    private final ConcurrentHashMap<String, List<T>> storage;
    private final AtomicLong lastId;
    private final String storageKey;
    
    public InMemoryPersistenceImplementor(String storageKey) {
        this.storageKey = storageKey;
        this.storage = new ConcurrentHashMap<>();
        this.lastId = new AtomicLong(0);
        // Inicializar com lista vazia
        this.storage.put(storageKey, new ArrayList<>());
    }

    @Override
    public List<T> readAll() {
        return new ArrayList<>(storage.getOrDefault(storageKey, new ArrayList<>()));
    }

    @Override
    public void saveAll(List<T> entities) {
        storage.put(storageKey, new ArrayList<>(entities));
        logger.info("Dados salvos em memória para: " + storageKey);
    }

    @Override
    public boolean checkOrCreateFile() {
        // Em memória, sempre "existe"
        return true;
    }

    @Override
    public synchronized long generateNewId() {
        return lastId.incrementAndGet();
    }
    
    /**
     * Limpa todos os dados em memória
     */
    public void clear() {
        storage.put(storageKey, new ArrayList<>());
        lastId.set(0);
        logger.info("Dados limpos em memória para: " + storageKey);
    }
}
