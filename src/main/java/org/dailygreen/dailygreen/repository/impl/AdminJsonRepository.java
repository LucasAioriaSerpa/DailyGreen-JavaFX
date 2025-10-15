package org.dailygreen.dailygreen.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.dailygreen.dailygreen.model.user.types.Administrator;
import org.dailygreen.dailygreen.repository.IAdminRepository;
import org.dailygreen.dailygreen.util.Cryptography;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;

public class AdminJsonRepository extends BaseJsonRepository<Administrator> implements IAdminRepository {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/admins.json";
    private static final String ID_FILE_PATH = "src/main/resources/db_dailygreen/admins_last_id.txt";
    private static final Type LIST_TYPE = new TypeToken<List<Administrator>>() {}.getType();

    public AdminJsonRepository() { super(FILE_PATH, ID_FILE_PATH, LIST_TYPE); }

    @Override
    public List<Administrator> findAll() { return readAll(); }

    @Override
    public Administrator findByEmail(String email) { return readAll().stream().filter(a -> a.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null); }

    @Override
    public boolean save(Administrator admin) {
        List<Administrator> admins = readAll();
        if (admins.stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(admin.getEmail()))) { return false; }
        try {
            String encrypted = Cryptography.criptografar(
                    admin.getPassword(),
                    Cryptography.lerChaveDeArquivo(Cryptography.getARQUIVO_CHAVE())
            );
            admin.setPassword(encrypted);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao criptografar senha do administrador.", e);
            return false;
        }
        admin.setID(generateNewId());
        admins.add(admin);
        saveAll(admins);
        return true;
    }

    @Override
    public boolean deleteByEmail(String email) {
        List<Administrator> admins = readAll();
        boolean removed = admins.removeIf(a -> a.getEmail().equalsIgnoreCase(email));
        if (removed) saveAll(admins);
        return removed;
    }

    @Override
    public boolean validateLogin(String email, String password) {
        List<Administrator> admins = readAll();
        return admins.stream().anyMatch(admin -> {
            try {
                String decrypted = Cryptography.descriptografar(
                        admin.getPassword(),
                        Cryptography.lerChaveDeArquivo(Cryptography.getARQUIVO_CHAVE())
                );
                return admin.getEmail().equalsIgnoreCase(email) && decrypted.equals(password);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro ao validar senha do administrador.", e);
                return false;
            }
        });
    }
}
