package org.dailygreen.dailygreen.repository;

import org.dailygreen.dailygreen.model.event.Event;

import java.util.List;

public interface IEventRepository {
    List<Event> findAll();
    Event findById(long id);
    boolean save(Event event);
    boolean deleteById(long id);
}
