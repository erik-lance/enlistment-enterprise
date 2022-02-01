package com.orangeandbronze.enlistment.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.*;
import org.testcontainers.containers.*;
import org.testcontainers.junit.jupiter.Container;

class AbstractControllerIT {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    MockMvc mockMvc;

    private final static String TEST = "test";

    @Container
    private final PostgreSQLContainer container = new PostgreSQLContainer("postgres:14")
            .withDatabaseName(TEST).withUsername(TEST).withPassword(TEST);

    @DynamicPropertySource
    private static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:tc:postgresql:14:///" + TEST);
        registry.add("spring.datasource.password", () -> TEST);
        registry.add("spring.datasource.username", () -> TEST);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }
}
