package org.dailygreen.dailygreen.repository;

import org.dailygreen.dailygreen.model.moderation.Report;

import java.util.List;
import java.util.Optional;

public interface IReportRepository {
    void save(Report report);
    void update(Report report);
    void delete(Report report);
    Optional<Report> findById(Integer id);
    List<Report> findAll();
    List<Report> findByFilter(String tipo, String termo);
}
