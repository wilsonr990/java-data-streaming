package com.datastreaming.service;

import com.datastreaming.model.Event;
import com.datastreaming.model.Stats;
import com.datastreaming.repository.EventRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.Instant;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Mono<Boolean> registerEvents(Flux<Event> events) {
        return events.collectList().map(eventRepository::save);
    }

    public Mono<Stats> getStats() {
        return eventRepository.findAll().filter(it ->
                it.getInstant().isAfter(Instant.now().minusSeconds(60))
        ).map(it ->
                it
        ).reduce(new Stats(), (stats, e) -> {
            stats.setTotal(stats.getTotal() + 1);
            stats.setSumX(stats.getSumX() + e.getX());
            stats.setAvgX(stats.getSumX() / stats.getTotal());
            stats.setSumY(stats.getSumY().add(BigInteger.valueOf(e.getY())));
            stats.setAvgY(stats.getSumY().doubleValue() / stats.getTotal());
            return stats;
        });
    }
}
