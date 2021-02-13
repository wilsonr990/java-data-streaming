package com.datastreaming.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StocksControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void handlesOneEventOutOfLastMinute() {
        Instant now = Instant.now().minusSeconds(60);
        String body = now.toEpochMilli() + ",0.0442672968,1282509067";
        webTestClient.post()
                .uri("/event")
                .bodyValue(body)
                .exchange()
                .expectStatus().isAccepted();

        String response = webTestClient.get()
                .uri("/stats")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThat(response).isEqualTo("0,0.0000000000,0.0000000000,0,0.0000000000");
    }

    @Test
    public void handlesOneEventWithinLastMinute() {
        Instant now = Instant.now();
        String body = now.toEpochMilli() + ",0.0442672968,1282509067";
        webTestClient.post()
                .uri("/event")
                .bodyValue(body)
                .exchange()
                .expectStatus().isAccepted();

        String response = webTestClient.get()
                .uri("/stats")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThat(response).isEqualTo("1,0.0442672968,0.0442672968,1282509067,1282509067.0000000000");
    }

    @Test
    public void handlesMultipleEventsWithinLastMinute() {
        String body = Instant.now().toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().toEpochMilli() + ",0.0442672968,1282509067";

        webTestClient.post()
                .uri("/event")
                .bodyValue(body)
                .exchange()
                .expectStatus().isAccepted();

        String response = webTestClient.get()
                .uri("/stats")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThat(response).isEqualTo("10,0.4426729680,0.0442672968,12825090670,1282509067.0000000000");
    }

    @Test
    public void handlesMultipleEventsOutOfLastMinute() {
        String body = Instant.now().minusSeconds(60).toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().minusSeconds(60).toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().minusSeconds(60).toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().minusSeconds(60).toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().minusSeconds(60).toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().minusSeconds(60).toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().minusSeconds(60).toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().minusSeconds(60).toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().minusSeconds(60).toEpochMilli() + ",0.0442672968,1282509067\n" +
                Instant.now().minusSeconds(60).toEpochMilli() + ",0.0442672968,1282509067";

        webTestClient.post()
                .uri("/event")
                .bodyValue(body)
                .exchange()
                .expectStatus().isAccepted();

        String response = webTestClient.get()
                .uri("/stats")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThat(response).isEqualTo("0,0.0000000000,0.0000000000,0,0.0000000000");
    }
}
