package org.dailygreen.dailygreen.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.dailygreen.dailygreen.model.user.types.Organizator;
import org.dailygreen.dailygreen.repository.IOrganizadorRepository;
import org.dailygreen.dailygreen.util.Cryptography;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;

public class OrganizadorJsonRepository extends BaseJsonRepository<Organizator> implements IOrganizadorRepository {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/organizadores.json";
    private static final String ID_FILE_PATH = "src/main/resources/db_dailygreen/organizadores_last_id.txt";
    private static final Type LIST_TYPE = new TypeToken<List<Organizator>>() {}.getType();

    public OrganizadorJsonRepository() { super(FILE_PATH, ID_FILE_PATH, LIST_TYPE); }

    @Override
    public List<Organizator> findAll() { return readAll(); }

    @Override
    public Organizator findByEmail(String email) { return readAll().stream().filter(o -> o.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null); }

    @Override
    public boolean save(Organizator organizator) {
        List<Organizator> organizators = readAll();
        if (organizators.stream().anyMatch(o -> o.getEmail().equalsIgnoreCase(organizator.getEmail()))) { return false; }
        try {
            String encrypted = Cryptography.criptografar(
                    organizator.getPassword(),
                    Cryptography.lerChaveDeArquivo(Cryptography.getARQUIVO_CHAVE())
            );
            organizator.setPassword(encrypted);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao salvar organizador.", e);
            return false;
        }
        organizator.setID(generateNewId());
        organizators.add(organizator);
        saveAll(organizators);
        return true;
    }

    @Override
    public boolean deleteByEmail(String email) {
        List<Organizator> organizators = readAll();
        boolean removed = organizators.removeIf(o -> o.getEmail().equalsIgnoreCase(email));
        if (removed) saveAll(organizators);
        return removed;
    }

    @Override
    public boolean validateLogin(String email, String password) {
        List<Organizator> organizators = readAll();
        return organizators.stream().anyMatch(o -> {
            try {
                String decrypted = Cryptography.descriptografar(
                        o.getPassword(),
                        Cryptography.lerChaveDeArquivo(Cryptography.getARQUIVO_CHAVE())
                );
                return o.getEmail().equalsIgnoreCase(email) && decrypted.equals(password);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro ao validar senha do organizador.", e);
                return false;
            }
        });
    }
}
