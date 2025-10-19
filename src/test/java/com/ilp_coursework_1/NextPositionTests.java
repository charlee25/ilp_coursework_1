package com.ilp_coursework_1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class NextPositionTests {
    @Autowired
    private MockMvc mockMvc;
    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void nextPosition_output_correct() {
        NextPositionRequest request = new NextPositionRequest();
        request.setStart(new Position(55.946233,  -3.192473));
        request.setAngle(45.0);
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/nextPosition",
                request,
                String.class);
        assertEquals("{\"lat\":55.946339,\"lng\":-3.192367}", response.getBody());
    }
    @Test
    void nextPosition_code_correct() {
        NextPositionRequest request = new NextPositionRequest();
        request.setStart(new Position(55.946233,  -3.192473));
        request.setAngle(45.0);
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/nextPosition",
                request,
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void nextPosition_code_missingStart() {
        NextPositionRequest request = new NextPositionRequest();
        request.setAngle(45.0);
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/nextPosition",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void nextPosition_code_missingAngle() {
        NextPositionRequest request = new NextPositionRequest();
        request.setStart(new Position(55.946233,  -3.192473));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/nextPosition",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void nextPosition_code_nullLat() {
        NextPositionRequest request = new NextPositionRequest();
        request.setStart(new Position(null,  -3.192473));
        request.setAngle(45.0);
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/nextPosition",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void nextPosition_code_nullLng() {
        NextPositionRequest request = new NextPositionRequest();
        request.setStart(new Position(55.946233,  null));
        request.setAngle(45.0);
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/nextPosition",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void nextPosition_code_nullAngle() {
        NextPositionRequest request = new NextPositionRequest();
        request.setStart(new Position(55.946233,  null));
        request.setAngle(null);
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/nextPosition",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void nextPosition_code_nullRequest() {
        NextPositionRequest request = new NextPositionRequest();
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/nextPosition",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void nextPosition_output_correctSyntax() throws Exception {
        String requestBody = """
                {
                    "start": {
                        "lng": -3.192473,
                        "lat": 55.946233
                    },
                    "angle": 45.0
                }
                
                """;
        mockMvc.perform(post("/api/v1/nextPosition")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(content().string("{\"lat\":55.946339,\"lng\":-3.192367}"));
    }
    @Test
    void nextPosition_code_correctSyntax() throws Exception {
        String requestBody = """
                {
                    "start": {
                        "lng": -3.192473,
                        "lat": 55.946233
                    },
                    "angle": 45.0
                }
                
                """;
        mockMvc.perform(post("/api/v1/nextPosition")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }
    @Test
    void nextPosition_code_incorrectSyntax() throws Exception {
        String requestBody = """
                {
                    "start": {
                        "lng": -3.192473,
                        "lat": 55.946233
                    },
                    "angle": 45.0
              
                """;
        mockMvc.perform(post("/api/v1/nextPosition")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
