package org.dailygreen.dailygreen.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dailygreen.dailygreen.model.moderation.Report;
import org.dailygreen.dailygreen.repository.IReportRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReportRepository implements IReportRepository {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/report.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    private List<Report> loadReports() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) { return new ArrayList<>(); }
            return mapper.readValue(file, new TypeReference<List<Report>>() {});
        } catch (IOException e) { throw  new RuntimeException("Erro ao salvar denúncias: "+e.getMessage(), e); }
    }

    public void saveReports(List<Report> reports) {
        try { mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), reports); }
        catch (IOException e) { throw new RuntimeException("Erro ao salvar denúncias: "+e.getMessage(), e); }
    }

    private int nextId(List<Report> reports) { return reports.stream().map(Report::getId).filter(Objects::nonNull).max(Integer::compareTo).orElse(0)+1; }

    @Override
    public void save(Report report) {
        List<Report> reports = loadReports();
        if (report.getId() == null) { report.setId(nextId(reports)); }
        reports.add(report);
        saveReports(reports);
    }

    @Override
    public void update(Report report) {
        List<Report> reports = loadReports();
        reports.replaceAll(r -> r.getId().equals(report.getId()) ? report : r);
        saveReports(reports);
    }

    @Override
    public void delete(Report report) {
        List<Report> reports = loadReports();
        reports.removeIf(r -> r.getId().equals(report.getId()));
        saveReports(reports);
    }

    @Override
    public Optional<Report> findById(Integer id) { return loadReports().stream().filter(r -> r.getId().equals(id)).findFirst(); }

    @Override
    public List<Report> findAll() { return loadReports(); }

    @Override
    public List<Report> findByFilter(String tipo, String termo) {
        if (tipo == null || termo == null || tipo.isEmpty() || termo.isEmpty()) return findAll();
        String tipoLower = tipo.toLowerCase();
        String termoLower = termo.toLowerCase();
        return loadReports().stream().filter(report -> {
           switch (tipoLower) {
               case "titulo": return report.getTitulo() != null && report.getTitulo().toLowerCase().contains(termoLower);
               case "motivo": return report.getMotivo() != null && report.getMotivo().toLowerCase().contains(termoLower);
               case "participante": return report.getParticipante() != null && report.getParticipante().toLowerCase().contains(termoLower);
               case "status": return report.getStatus() != null && report.getStatus().toLowerCase().contains(termoLower);
               case "data": return report.getData() != null && report.getData().toString().toLowerCase().contains(termoLower);
               case "suspenso": return Boolean.toString(report.getSuspenso()).toLowerCase().contains(termoLower);
               case "banido": return Boolean.toString(report.getBanido()).toLowerCase().contains(termoLower);
               default: return false;
           }
        }).collect(Collectors.toList());
    }

}
