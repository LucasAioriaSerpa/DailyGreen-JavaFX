package org.dailygreen.dailygreen.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.dailygreen.dailygreen.model.moderation.Report;
import org.dailygreen.dailygreen.repository.IReportRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ReportJsonRepository implements IReportRepository {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/report.json";
    private static final Logger logger = Logger.getLogger(ReportJsonRepository.class.getName());
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<Report>>() {}.getType();

    private List<Report> loadReports() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists() || file.length() == 0) {
                logger.info("Arquivo de denúncias não encontrado ou vazio. Criando novo arquivo...");
                saveReports(new ArrayList<>());
                return new ArrayList<>();
            }
            try (FileReader reader = new FileReader(file)) {
                List<Report> reports = gson.fromJson(reader, LIST_TYPE);
                return (reports != null) ? reports : new ArrayList<>();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao carregar denúncias: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private void saveReports(List<Report> reports) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) { gson.toJson(reports, writer); }
        catch (Exception e) { logger.log(Level.SEVERE, "Erro ao salvar denúncias: " + e.getMessage(), e); }
    }

    private int nextId(List<Report> reports) { return reports.stream().map(Report::getId).filter(Objects::nonNull).max(Integer::compareTo).orElse(0) + 1; }

    @Override
    public void save(Report report) {
        List<Report> reports = loadReports();
        if (report.getId() == null) { report.setId(nextId(reports)); }
        reports.add(report);
        saveReports(reports);
        logger.info("Denúncia salva com sucesso (ID: " + report.getId() + ")");
    }

    @Override
    public void update(Report report) {
        List<Report> reports = loadReports();
        boolean updated = false;
        for (int i = 0; i < reports.size(); i++) {
            if (Objects.equals(reports.get(i).getId(), report.getId())) {
                reports.set(i, report);
                updated = true;
                break;
            }
        }
        if (updated) {
            saveReports(reports);
            logger.info("Denúncia atualizada com sucesso (ID: " + report.getId() + ")");
        } else { logger.warning("Tentativa de atualizar denúncia inexistente (ID: " + report.getId() + ")"); }
    }

    @Override
    public void delete(Report report) {
        List<Report> reports = loadReports();
        boolean removed = reports.removeIf(r -> Objects.equals(r.getId(), report.getId()));
        if (removed) {
            saveReports(reports);
            logger.info("Denúncia removida com sucesso (ID: " + report.getId() + ")");
        } else { logger.warning("Tentativa de remover denúncia inexistente (ID: " + report.getId() + ")"); }
    }

    @Override
    public Optional<Report> findById(Integer id) { return loadReports().stream().filter(r -> Objects.equals(r.getId(), id)).findFirst(); }

    @Override
    public List<Report> findAll() { return loadReports(); }

    @Override
    public List<Report> findByFilter(String tipo, String termo) {
        if (tipo == null || termo == null || tipo.isEmpty() || termo.isEmpty()) return findAll();
        String tipoLower = tipo.toLowerCase();String termoLower = termo.toLowerCase();
        return loadReports().stream().filter(report -> switch (tipoLower) {
            case "titulo" ->        report.getTitulo() != null && report.getTitulo().toLowerCase().contains(termoLower);
            case "motivo" ->        report.getMotivo() != null && report.getMotivo().toLowerCase().contains(termoLower);
            case "participante" ->  report.getParticipante() != null && report.getParticipante().toLowerCase().contains(termoLower);
            case "status" ->        report.getStatus() != null && report.getStatus().toLowerCase().contains(termoLower);
            case "data" ->          report.getData() != null && report.getData().toString().toLowerCase().contains(termoLower);
            case "suspenso" ->      Boolean.toString(report.getSuspenso()).toLowerCase().contains(termoLower);
            case "banido" ->        Boolean.toString(report.getBanido()).toLowerCase().contains(termoLower);
            default ->              false;
        }).collect(Collectors.toList()
        );
    }
}
