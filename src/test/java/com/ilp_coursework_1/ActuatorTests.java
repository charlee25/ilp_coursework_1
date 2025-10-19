package com.ilp_coursework_1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActuatorTests {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void actuator_output() {
        var response = restTemplate.getForEntity("http://localhost:" + port + "/actuator/health", String.class);
        assertTrue(response.getBody().contains("UP"));
    }
    @Test
    void actuator_code() {
        var response = restTemplate.getForEntity("http://localhost:" + port + "/actuator/health", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}