package com.ilp_coursework_1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class IsInRegionTests {
    @Autowired
    private MockMvc mockMvc;
    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    List<Position> positions = new ArrayList<>(){
        {
            add(new Position(55.946233 , -3.192473));
            add(new Position(55.942617, -3.192473));
            add(new Position(55.942617, -3.184319));
            add(new Position(55.946233, -3.184319));
            add(new Position(55.946233 , -3.192473));
        }
    };
    List<Position> positionsShort = new ArrayList<>(){
        {
            add(new Position(55.946233 , -3.192473));
            add(new Position(55.942617, -3.192473));
            add(new Position(55.946233 , -3.192473));
        }
    };
    List<Position> positionsEmpty = new ArrayList<>();
    @Test
    void isInRegion_output_correct() {
        RegionRequest request = new RegionRequest();
        request.setPosition(new Position(1.222 ,  1.234));
        request.setRegion(new Region("Central", positions));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals("false", response.getBody());
    }
    @Test
    void isInRegion_code_correct() {
        RegionRequest request = new RegionRequest();
        request.setPosition(new Position(1.222 ,  1.234));
        request.setRegion(new Region("Central", positions));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void isInRegion_code_missingPosition() {
        RegionRequest request = new RegionRequest();
        request.setRegion(new Region("Central", positions));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void isInRegion_code_missingRegion() {
        RegionRequest request = new RegionRequest();
        request.setPosition(new Position(1.222 ,  1.234));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void isInRegion_code_nullLat() {
        RegionRequest request = new RegionRequest();
        request.setPosition(new Position(null,  1.234));
        request.setRegion(new Region("Central", positions));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void isInRegion_code_nullLng() {
        RegionRequest request = new RegionRequest();
        request.setPosition(new Position(1.222 ,  null));
        request.setRegion(new Region("Central", positions));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void isInRegion_code_regionName() {
        RegionRequest request = new RegionRequest();
        request.setPosition(new Position(1.222 ,  1.234));
        request.setRegion(new Region(null, positions));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void isInRegion_code_positionsList() {
        RegionRequest request = new RegionRequest();
        request.setPosition(new Position(1.222 ,  1.234));
        request.setRegion(new Region("Central", null));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void isInRegion_code_emptyPositionsList() {
        RegionRequest request = new RegionRequest();
        request.setPosition(new Position(1.222 ,  1.234));
        request.setRegion(new Region("Central", positionsEmpty));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void isInRegion_code_positionsStartEndNotEqual() {
        positions.add(new Position(55.946233 , -3.192474));
        RegionRequest request = new RegionRequest();
        request.setPosition(new Position(1.222 ,  1.234));
        request.setRegion(new Region("Central", positionsEmpty));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void isInRegion_code_lessThan4Positions() {
        RegionRequest request = new RegionRequest();
        request.setPosition(new Position(1.222 ,  1.234));
        request.setRegion(new Region("Central", positionsShort));
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void isInRegion_code_nullRequest() {
        RegionRequest request = new RegionRequest();
        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/isInRegion",
                request,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void isInRegion_output_correctSyntax() throws Exception {
        String requestBody = """
                {
                    "position": {
                        "lng": 1.234,
                        "lat": 1.222
                    },
                    "region": {
                        "name" : "central",
                        "vertices": [
                            {
                                "lng": -3.192473,
                                "lat": 55.946233
                            },
                            {
                                "lng": -3.192473,
                                "lat": 55.942617
                            },
                            {
                                "lng": -3.184319,
                                "lat":  55.942617
                            },
                            {
                                "lng": -3.184319,
                                "lat": 55.946233
                            },
                            {
                                "lng": -3.192473,
                                "lat": 55.946233
                            }
                        ]
                    }
                }
                
                """;
        mockMvc.perform(post("/api/v1/isInRegion")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(content().string("false"));
    }
    @Test
    void isInRegion_code_correctSyntax() throws Exception {
        String requestBody = """
                {
                    "position": {
                        "lng": 1.234,
                        "lat": 1.222
                    },
                    "region": {
                        "name" : "central",
                        "vertices": [
                            {
                                "lng": -3.192473,
                                "lat": 55.946233
                            },
                            {
                                "lng": -3.192473,
                                "lat": 55.942617
                            },
                            {
                                "lng": -3.184319,
                                "lat":  55.942617
                            },
                            {
                                "lng": -3.184319,
                                "lat": 55.946233
                            },
                            {
                                "lng": -3.192473,
                                "lat": 55.946233
                            }
                        ]
                    }
                }
                
                """;
        mockMvc.perform(post("/api/v1/isInRegion")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }
    @Test
    void isInRegion_code_incorrectSyntax() throws Exception {
        String requestBody = """
                {
                    "position": {
                        "lng": 1.234,
                        "lat": 1.222
                    },
                    "region": {
                        "name" : "central",
                        "vertices": [
                            {
                                "lng": -3.192473,
                                "lat": 55.946233
                            },
                            {
                                "lng": -3.192473,
                                "lat": 55.942617
                            },
                            {
                                "lng": -3.184319,
                                "lat":  55.942617
                            },
                            {
                                "lng": -3.184319,
                                "lat": 55.946233
                            },
                            {
                                "lng": -3.192473,
                                "lat": 55.946233
                            }
                        ]
                    }
                
                """;
        mockMvc.perform(post("/api/v1/isInRegion")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
