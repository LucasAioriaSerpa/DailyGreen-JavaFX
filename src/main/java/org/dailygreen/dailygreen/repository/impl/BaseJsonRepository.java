package org.dailygreen.dailygreen.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseJsonRepository<T> {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected final Logger logger = Logger.getLogger(getClass().getName());
    private final String filePath;
    private final String idFilePath;
    private final Type listType;

    protected BaseJsonRepository(String filePath, String idFilePath, Type listType) {
        this.filePath = filePath;
        this.idFilePath = idFilePath;
        this.listType = listType;
        checkOrCreateFile();
    }

    protected List<T> readAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            logger.warning("Arquivo não encontrado: " + filePath + " — criando novo...");
            criarArquivoVazio();
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            if (file.length() == 0) return new ArrayList<>();
            List<T> list = gson.fromJson(reader, listType);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao ler arquivo JSON: " + filePath, e);
            return new ArrayList<>();
        }
    }

    public void saveAll(List<T> data) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) { gson.toJson(data, writer); }
        } catch (IOException e) { logger.log(Level.SEVERE, "Erro ao salvar JSON: " + filePath, e); }
    }

    protected void criarArquivoVazio() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) { gson.toJson(new ArrayList<>(), writer); }
            logger.info("Arquivo criado: " + filePath);
        } catch (IOException e) { logger.log(Level.SEVERE, "Erro ao criar arquivo JSON vazio: " + filePath, e); }
    }

    protected boolean checkOrCreateFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            criarArquivoVazio();
            return false;
        }
        return true;
    }

    protected synchronized int generateNewId() {
        File idFile = new File(idFilePath);
        idFile.getParentFile().mkdirs();
        try {
            int lastId = 0;
            if (idFile.exists()) {
                String content = Files.readString(idFile.toPath()).trim();
                if (!content.isEmpty()) lastId = Integer.parseInt(content);
            }
            int newId = lastId + 1;
            Files.writeString(idFile.toPath(), String.valueOf(newId));
            return newId;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao gerar novo ID: " + idFilePath, e);
            return 1;
        }
    }
}
