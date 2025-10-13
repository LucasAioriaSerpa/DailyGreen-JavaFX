package org.dailygreen.dailygreen.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.dailygreen.dailygreen.model.user.AbstractUser;
import org.dailygreen.dailygreen.repository.IUserRepositoty;

import java.lang.reflect.Type;
import java.util.List;

public class UserJsonRepoitory extends BaseJsonRepository<AbstractUser> implements IUserRepositoty {
    private static final String FILE_PATH = "src/main/reources/data/users.json";
    private static final String ID_FILE_PATH = "src/main/reources/data/users_last_id.txt";
    private static final Type LIST_TYPE = new TypeToken<List<AbstractUser>>() {}.getType();

    public UserJsonRepoitory() {
        super(FILE_PATH, ID_FILE_PATH, LIST_TYPE);
        if (!checkOrCreateFile()) { logger.info("Arquivos JSON de usuarios criados automaticamente!"); }
        else { logger.info("Arquivos JSON de usuarios criado com sucesso!"); }
    }

    @Override public List<AbstractUser> findAll() { return readAll(); }

    @Override public AbstractUser findByEmail(String email) {
        return readAll().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    @Override
    public boolean save(AbstractUser abstractUser) {
        List<AbstractUser> abstractUsers = readAll();
        if (abstractUsers.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(abstractUser.getEmail()))) return false;
        abstractUser.setID(generateNewId());
        abstractUsers.add(abstractUser);
        saveAll(abstractUsers);
        return true;
    }

    @Override
    public boolean deleteByEmail(String email) {
        List<AbstractUser> abstractUsers = readAll();
        boolean removed = abstractUsers.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
        if (removed) saveAll(abstractUsers);
        return removed;
    }
}
