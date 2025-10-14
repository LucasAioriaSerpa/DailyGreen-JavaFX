package org.dailygreen.dailygreen.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.dailygreen.dailygreen.model.post.Reaction;
import org.dailygreen.dailygreen.repository.IReactionRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.logging.Level;

public class ReactionJsonRepository extends BaseJsonRepository<Reaction> implements IReactionRepository {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/reactions.json";
    private static final String ID_FILE_PATH = "src/main/resources/db_dailygreen/reactions_last_id.txt";
    private static final Type LIST_TYPE = new TypeToken<List<Reaction>>() {}.getType();

    public ReactionJsonRepository() {
        super(FILE_PATH, ID_FILE_PATH, LIST_TYPE);
        if (!checkOrCreateFile()) { logger.info("Arquivos JSON de reações criados automaticamente!"); }
        else { logger.info("Arquivo JSON de reações carregado com sucesso!"); }
    }

    @Override
    public List<Reaction> findAll() { return readAll(); }

    @Override
    public void saveOrToggle(Reaction reaction) {
        List<Reaction> reactions = readAll();
        boolean sameReactionExists = reactions.removeIf(r -> r.getIdPost() == reaction.getIdPost() && r.getAutorEmail().equals(reaction.getAutorEmail()) && r.getTipo().equalsIgnoreCase(reaction.getTipo()));
        if (sameReactionExists) { saveAll(reactions); return; }
        reactions.removeIf(r -> r.getIdPost() == reaction.getIdPost() && r.getAutorEmail().equals(reaction.getAutorEmail()));
        reactions.add(reaction);
        try { saveAll(reactions); }
        catch (Exception e) { logger.log(Level.SEVERE, "Erro ao salvar ou atualizar reação", e); }
    }

    @Override
    public void delete(String autorEmail, long idPost) {
        List<Reaction> reactions = readAll();
        reactions.removeIf(r -> r.getAutorEmail().equals(autorEmail) && r.getIdPost() == idPost);
        saveAll(reactions);
    }

    @Override
    public List<Reaction> findByPost(long idPost) { return readAll().stream().filter(r -> r.getIdPost() == idPost).collect(Collectors.toList()); }

    @Override
    public long countByTarget(long idPost, String tipo) { return readAll().stream().filter(r -> r.getIdPost() == idPost && r.getTipo().equalsIgnoreCase(tipo)).count(); }

    @Override
    public Optional<String> findUserReactionType(String autorEmail, long idPost) { return readAll().stream().filter(r -> r.getAutorEmail().equals(autorEmail) && r.getIdPost() == idPost).map(Reaction::getTipo).findFirst(); }

}
