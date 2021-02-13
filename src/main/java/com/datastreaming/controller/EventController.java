package com.datastreaming.controller;

import com.datastreaming.model.Event;
import com.datastreaming.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.StringTokenizer;

@RestController()
@RequestMapping("")
public class EventController {
    private final EventService eventsService;

    public EventController(EventService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping(value = "/stats")
    public Mono<String> stats() {
        return eventsService.getStats()
                .map(it ->
                        String.format("%d,%.10f,%.10f,%d,%.10f", it.getTotal(), it.getSumX(), it.getAvgX(), it.getSumY(), it.getAvgY())
                );
    }

    @PostMapping(value = "/event", consumes = "text/plain")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Boolean> event(@RequestBody String events) {
        return eventsService.registerEvents(
                Flux.fromStream(events.lines()).map(it -> {
                            StringTokenizer eventTokens = new StringTokenizer(it, ",");
                            return new Event(
                                    Long.parseLong(eventTokens.nextToken()),
                                    Double.parseDouble(eventTokens.nextToken()),
                                    Integer.parseInt(eventTokens.nextToken()));
                        }
                ));
    }
}
