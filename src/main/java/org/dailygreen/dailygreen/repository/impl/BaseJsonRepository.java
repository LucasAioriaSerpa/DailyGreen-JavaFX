package org.dailygreen.dailygreen.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseJsonRepository<T> {
    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected final String dataFilePath;
    protected final String idFilePath;
    protected final Gson gson;
    private final Type listType;
    private final AtomicLong lastId;

    protected BaseJsonRepository(String dataFilePath, String idFilePath, Type listType) {
        this.dataFilePath = dataFilePath;
        this.idFilePath = idFilePath;
        this.listType = listType;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.lastId = new AtomicLong(loadLastId());
    }

    public boolean checkOrCreateFile() {
        boolean exists = true;
        try {
            Files.createDirectories(Paths.get(dataFilePath).getParent());
            File dataFile = new File(dataFilePath);
            if (!dataFile.exists()) {
                if (dataFile.createNewFile()) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) { writer.write("[]"); }
                    exists = false;
                    logger.info("Arquivo de dados criado: " + dataFilePath);
                }
            }
            File idfile = new File(idFilePath);
            if (!idfile.exists()) {
                if (idfile.createNewFile()) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(idFilePath))) { writer.write("0"); }
                    exists = false;
                    logger.info("Arquivo de ID criado: " + idFilePath);
                }
            }
        } catch (IOException e) { logger.log(Level.SEVERE, "Erro ao verificar/criar arquivos JSON", e); }
        return exists;
    }

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

    public void saveAll(List<T> list) {
        try {
            Files.createDirectories(Paths.get(dataFilePath).getParent());
            try (Writer writer = new FileWriter(dataFilePath)) { gson.toJson(list, writer); }
        } catch (IOException e) { logger.log(Level.SEVERE, "Erro ao salvar JSON: " + dataFilePath, e); }
    }

    private long loadLastId() {
        File file = new File(idFilePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) { return Long.parseLong(reader.readLine()); }
            catch (IOException | NumberFormatException ignored) {}
        }
        return 0L;
    }

    protected synchronized long generateNewId() {
        long newId = lastId.incrementAndGet();
        saveLastId();
        return newId;
    }

    private void saveLastId() {
        try {
            Files.createDirectories(Paths.get(idFilePath).getParent());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(idFilePath))) { writer.write(Long.toString(lastId.get())); }
        } catch (IOException e) { logger.log(Level.SEVERE, "Erro ao salvar JSON: " + idFilePath, e);}
    }

}
