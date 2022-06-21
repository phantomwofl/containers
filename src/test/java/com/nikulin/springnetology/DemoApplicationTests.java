package com.nikulin.springnetology;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    public static GenericContainer<?> devapp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);

    public static GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        try {
            devapp.start();
            prodapp.start();
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void contextLoads() {
        try {

        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + devapp.getMappedPort(8080), String.class);
        String expected = "8080";
        String result = forEntity.getBody();
        assertEquals(expected, result);

        ResponseEntity<String> forEntity2 = restTemplate.getForEntity("http://localhost:" + prodapp.getMappedPort(8081), String.class);
        String expected2 = "8081";
        String result2 = forEntity2.getBody();
        assertEquals(expected2, result2);
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
    }
}