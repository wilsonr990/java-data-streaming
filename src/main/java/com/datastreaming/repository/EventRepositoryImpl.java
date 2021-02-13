package com.datastreaming.repository;

import com.datastreaming.model.Event;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class EventRepositoryImpl implements EventRepository {
    final ConcurrentLinkedQueue<Event> events = new ConcurrentLinkedQueue<>();

    public boolean save(List<Event> eventList) {
        return events.addAll(eventList);
    }

    public Flux<Event> findAll() {
        return Flux.fromStream(events.stream());
    }
}
