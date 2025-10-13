package org.dailygreen.dailygreen.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.dailygreen.dailygreen.model.event.Event;
import org.dailygreen.dailygreen.repository.IEventRepository;

import java.lang.reflect.Type;
import java.util.List;

public class EventJsonRepository extends BaseJsonRepository<Event> implements IEventRepository {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/events.json";
    private static final String ID_FILE_PATH = "src/main/resources/db_dailygreen/event_last_id.txt";
    private static final Type LIST_TYPE = new TypeToken<List<Event>>() {}.getType();

    public EventJsonRepository() { super(FILE_PATH, ID_FILE_PATH, LIST_TYPE); }

    @Override public List<Event> findAll() { return readAll(); }

    @Override public Event findById(long id) { return readAll().stream().filter(e -> e.getID() == id).findFirst().orElse(null); }

    @Override
    public boolean save(Event event) {
        List<Event> events = readAll();
        if (event.getID() == 0) event.setID(generateNewId());
        events.add(event);
        saveAll(events);
        return true;
    }

    @Override
    public boolean deleteById(long id) {
        List<Event> events = readAll();
        boolean removed = events.removeIf(e -> e.getID() == id);
        if (removed) saveAll(events);
        return removed;
    }

}
