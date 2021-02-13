package com.datastreaming.repository;

import com.datastreaming.model.Event;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface EventRepository{
    boolean save(List<Event> event);

    Flux<Event> findAll();
}
