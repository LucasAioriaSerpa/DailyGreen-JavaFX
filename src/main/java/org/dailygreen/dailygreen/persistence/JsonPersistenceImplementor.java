package org.dailygreen.dailygreen.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Implementação concreta do PersistenceImplementor usando arquivos JSON.
 * Esta é uma implementação específica do lado "Implementor" do padrão Bridge.
 * 
 * @param <T> Tipo da entidade a ser persistida
 */
public class JsonPersistenceImplementor<T> implements PersistenceImplementor<T> {
    private static final Logger logger = Logger.getLogger(JsonPersistenceImplementor.class.getName());
    
    private final String dataFilePath;
    private final String idFilePath;
    private final Gson gson;
    private final Type listType;
    private final AtomicLong lastId;

    public JsonPersistenceImplementor(String dataFilePath, String idFilePath, Type listType) {
        this.dataFilePath = dataFilePath;
        this.idFilePath = idFilePath;
        this.listType = listType;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.lastId = new AtomicLong(loadLastId());
    }

    @Override
    public List<T> readAll() {
        File file = new File(dataFilePath);
        if (!file.exists()) { return new ArrayList<>(); }
        try (Reader reader = new FileReader(file)) {
            List<T> data = gson.fromJson(reader, listType);
            return (data != null) ? data : new ArrayList<>();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Erro ao ler JSON: " + dataFilePath, e);
            return new ArrayList<>();
        }
    }

    @Override
    public void saveAll(List<T> list) {
        try {
            Files.createDirectories(Paths.get(dataFilePath).getParent());
            try (Writer writer = new FileWriter(dataFilePath)) { gson.toJson(list, writer); }
        } catch (IOException e) { logger.log(Level.SEVERE, "Erro ao salvar JSON: " + dataFilePath, e); }
    }

    @Override
    public boolean checkOrCreateFile() {
        String writeDataFile = "[]";
        String writeIdFile = "0";
        boolean exists = true;
        try {
            File dataFile = new File(dataFilePath);
            File idFile = new File(idFilePath);
            File dataDir = dataFile.getParentFile();
            if (dataDir != null && !dataDir.exists()) dataDir.mkdirs();
            File idDir = idFile.getParentFile();
            if (idDir != null && !idDir.exists()) idDir.mkdirs();
            if (!dataFile.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) { writer.write(writeDataFile); }
                exists = false;
                logger.info("Arquivo de dados criado: " + dataFilePath);
            }
            if (!idFile.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(idFile))) { writer.write(writeIdFile); }
                exists = false;
                logger.info("Arquivo de ID criado: " + idFilePath);
            }
        } catch (IOException e) { logger.log(Level.SEVERE, "Erro ao verificar/criar arquivos JSON", e); }
        return exists;
    }



    @Override
    public synchronized long generateNewId() {
        long newId = lastId.incrementAndGet();
        saveLastId();
        return newId;
    }

    private long loadLastId() {
        File file = new File(idFilePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) { return Long.parseLong(reader.readLine()); }
            catch (IOException | NumberFormatException ignored) {}
        }
        return 0L;
    }

    private void saveLastId() {
        try {
            Files.createDirectories(Paths.get(idFilePath).getParent());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(idFilePath))) { writer.write(Long.toString(lastId.get())); }
        } catch (IOException e) { logger.log(Level.SEVERE, "Erro ao salvar ID: " + idFilePath, e); }
    }
}
