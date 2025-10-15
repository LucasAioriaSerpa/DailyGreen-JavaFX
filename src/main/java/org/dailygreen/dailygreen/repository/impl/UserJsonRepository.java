package org.dailygreen.dailygreen.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.repository.IUserRepositoty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserJsonRepository implements IUserRepositoty {
    private static final Logger logger = Logger.getLogger(UserJsonRepository.class.getName());
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/users.json";
    private final ObjectMapper mapper = new ObjectMapper();

    private String extractEmail(User user) {
        if (user == null) return null;
        if (user.getAccountAdministrador() != null) return user.getAccountAdministrador().getEmail();
        if (user.getAccountParticipante() != null) return user.getAccountParticipante().getEmail();
        if (user.getAccountOrganizator() != null) return user.getAccountOrganizator().getEmail();
        return null;
    }

    private List<User> readAll() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            logger.warning("Arquivo JSON de usuários não encontrado. Criando novo...");
            criarArquivoVazio();
            return new ArrayList<>();
        }
        try {
            byte[] jsonData = Files.readAllBytes(file.toPath());
            if (jsonData.length == 0) {
                logger.warning("Arquivo JSON vazio, retornando lista vazia.");
                return new ArrayList<>();
            }
            return mapper.readValue(jsonData, new TypeReference<>() {});
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao ler o arquivo JSON de usuários.", e);
            return new ArrayList<>();
        }
    }

    private void saveAll(List<User> users) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, users); }
        catch (IOException e) { logger.log(Level.SEVERE, "Erro ao salvar o arquivo JSON de usuários.", e); }
    }

    private void criarArquivoVazio() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            mapper.writeValue(file, new ArrayList<User>()); }
        catch (IOException e) { logger.log(Level.SEVERE, "Erro ao criar arquivo JSON vazio de usuários.", e); }
    }

    public boolean checkOrCreateFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            criarArquivoVazio();
            return false;
        }
        return true;
    }

    @Override
    public List<User> findAll() { return readAll(); }

    @Override
    public User findByEmail(String email) { return readAll().stream().filter(u -> email.equalsIgnoreCase(extractEmail(u))).findFirst().orElse(null); }

    @Override
    public boolean save(User user) {
        List<User> users = readAll();
        String email = extractEmail(user);
        if (email == null) return false;
        boolean exists = users.stream().anyMatch(u -> email.equalsIgnoreCase(extractEmail(u)));
        if (exists) {
            logger.info("Usuário já existente, ignorando: " + email);
            return false;
        }
        try {
            users.add(user);
            saveAll(users);
            logger.info("Usuário salvo com sucesso: " + email);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao salvar o usuário JSON.", e);
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        List<User> users = readAll();
        String email = extractEmail(user);
        if (email == null) return false;
        boolean updated = false;
        for (int i = 0; i < users.size(); i++) {
            if (email.equalsIgnoreCase(extractEmail(users.get(i)))) {
                users.set(i, user);
                updated = true;
                break;
            }
        }
        if (updated) {
            saveAll(users);
            logger.info("Usuário atualizado: " + email);
            return true;
        } else {
            logger.warning("Usuário não encontrado para update: " + email);
            return false;
        }
    }

    @Override
    public boolean deleteByEmail(String email) {
        List<User> users = readAll();
        boolean removed = users.removeIf(u -> email.equalsIgnoreCase(extractEmail(u)));
        if (removed) {
            saveAll(users);
            logger.info("Usuário removido: " + email);
        } else {
            logger.warning("Nenhum usuário encontrado para remoção: " + email);
        }
        return removed;
    }

    @Override
    public boolean validateLogin(String email) {
        User user = findByEmail(email);
        return user != null && user.isLogged();
    }
}
