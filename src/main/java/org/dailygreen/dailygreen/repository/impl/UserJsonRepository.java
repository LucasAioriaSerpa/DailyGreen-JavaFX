package org.dailygreen.dailygreen.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.repository.IUserRepositoty;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;

public class UserJsonRepository extends BaseJsonRepository<User> implements IUserRepositoty {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/users.json";
    private static final String ID_FILE_PATH = "src/main/resources/db_dailygreen/users_last_id.txt";
    private static final Type LIST_TYPE = new TypeToken<List<User>>() {}.getType();

    public UserJsonRepository() {
        super(FILE_PATH, ID_FILE_PATH, LIST_TYPE);
        if (!checkOrCreateFile()) { logger.info("Arquivos JSON de usuários criados automaticamente!"); }
        else { logger.info("Arquivos JSON de usuários criados com sucesso!"); }
    }

    @Override
    public List<User> findAll() { return readAll(); }

    @Override
    public User findByEmail(String email) {
        if (email == null) return null;
        return readAll().stream().filter(u -> email.equals(extractEmail(u))).findFirst().orElse(null);
    }

    @Override
    public boolean save(User user) {
        List<User> users = readAll();
        String email = extractEmail(user);
        if (email == null) return false;
        boolean exists = users.stream().anyMatch(u -> email.equals(extractEmail(u)));
        if (exists) return false;
        try {
            users.add(user);
            saveAll(users);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao salvar o usuário JSON", e);
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        List<User> users = readAll();
        String targetEmail = extractEmail(user);
        if (targetEmail == null) return false;
        for (int i = 0; i < users.size(); i++) {
            if (targetEmail.equals(extractEmail(users.get(i)))) {
                users.set(i, user);
                saveAll(users);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteByEmail(String email) {
        if (email == null) return false;
        List<User> users = readAll();
        boolean removed = users.removeIf(u -> email.equals(extractEmail(u)));
        if (removed) saveAll(users);
        return removed;
    }

    @Override
    public boolean validateLogin(String email) {
        User user = findByEmail(email);
        return user != null && user.isLogged();
    }

    private String extractEmail(User user) {
        if (user == null)                           return null;
        if (user.getAccountAdministrador() != null) return user.getAccountAdministrador().getEmail();
        if (user.getAccountParticipante() != null)  return user.getAccountParticipante().getEmail();
        if (user.getAccountOrganizator() != null)   return user.getAccountOrganizator().getEmail();
        return null;
    }
}
