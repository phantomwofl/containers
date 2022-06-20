package com.nikulin.springnetology;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
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
        devapp.start();
        prodapp.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + devapp.getMappedPort(8080), String.class);
        String expected = "8080";
        String result = forEntity.getBody();
        assertEquals(expected, result);
        // System.out.println(forEntity.getBody());

        ResponseEntity<String> forEntity2 = restTemplate.getForEntity("http://localhost:" + prodapp.getMappedPort(8081), String.class);
        String expected2 = "8081";
        String result2 = forEntity2.getBody();
        assertEquals(expected2, result2);
    }
}