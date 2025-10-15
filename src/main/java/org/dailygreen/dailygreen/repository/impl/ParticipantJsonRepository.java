package org.dailygreen.dailygreen.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.dailygreen.dailygreen.model.user.types.Participant;
import org.dailygreen.dailygreen.repository.IParticipantRepository;
import org.dailygreen.dailygreen.util.Cryptography;

import java.lang.reflect.Type;
import java.util.List;

public class ParticipantJsonRepository extends BaseJsonRepository<Participant> implements IParticipantRepository {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/participants.json";
    private static final String ID_FILE_PATH = "src/main/resources/db_dailygreen/participants_last_id.txt";
    private static final Type LIST_TYPE = new TypeToken<List<Participant>>() {}.getType();

    public ParticipantJsonRepository() { super(FILE_PATH, ID_FILE_PATH, LIST_TYPE); }

    @Override public List<Participant> findAll() { return readAll(); }

    @Override public Participant findByEmail(String email) { return readAll().stream().filter(p -> p.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null); }

    @Override
    public boolean save(Participant participant) {
        List<Participant> participants = readAll();
        if (participants.stream().anyMatch(p -> p.getEmail().equalsIgnoreCase(participant.getEmail()))) { return false; }
        try {
            String encrypted = Cryptography.criptografar(participant.getPassword(), Cryptography.lerChaveDeArquivo(Cryptography.getARQUIVO_CHAVE()));
            participant.setPassword(encrypted);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao criptografar a senha do participante: " + e.getMessage(), e);
            return false;
        }
        participant.setID(generateNewId());
        participants.add(participant);
        saveAll(participants);
        return true;
    }

    @Override
    public boolean deleteByEmail(String email) {
        List<Participant> participants = readAll();
        boolean removed = participants.removeIf(p -> p.getEmail().equalsIgnoreCase(email));
        if (removed) saveAll(participants);
        return removed;
    }

    @Override
    public boolean validateLogin(String email, String password) {
        List<Participant> participants = readAll();
        return participants.stream().anyMatch(p -> {
            try {
                String decrypted = Cryptography.descriptografar(p.getPassword(), Cryptography.lerChaveDeArquivo(Cryptography.getARQUIVO_CHAVE()));
                return p.getEmail().equalsIgnoreCase(email) && decrypted.equals(password);
            } catch (Exception e) {
                logger.log(java.util.logging.Level.SEVERE, "Erro ao validar senha do participante: " + e.getMessage(), e);
                return false;
            }
        });
    }

}
