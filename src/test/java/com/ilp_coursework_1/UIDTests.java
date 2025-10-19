package com.ilp_coursework_1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UIDTests {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void uid_output() {
        var response = restTemplate.getForEntity("http://localhost:" + port + "/api/v1/uid", String.class);
        assertEquals("s2479712", response.getBody());
    }
    @Test
    void uid_code() {
        var response = restTemplate.getForEntity("http://localhost:" + port + "/api/v1/uid", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}