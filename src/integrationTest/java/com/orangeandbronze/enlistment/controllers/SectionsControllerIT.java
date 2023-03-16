package com.orangeandbronze.enlistment.controllers;

import com.orangeandbronze.enlistment.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.jdbc.core.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.time.*;
import java.util.*;

import static com.orangeandbronze.enlistment.domain.Days.MTH;
import static com.orangeandbronze.enlistment.domain.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
class SectionsControllerIT  {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SectionRepository sectionRepository;

    private final static String TEST = "test";

    @Container
    private final PostgreSQLContainer container = new PostgreSQLContainer("postgres:14")
            .withDatabaseName(TEST).withUsername(TEST).withPassword(TEST);

    @DynamicPropertySource
    private static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:tc:postgresql:14:///" + TEST);
        registry.add("spring.datasource.username", () -> TEST);
        registry.add("spring.datasource.password", () -> TEST);
    }

    // No need for multi-threaded test, unlikely that work of two or more admins will collide
    @Test
    void createSection_save_to_db() throws Exception {
        final int adminId = 9;

        final String startTime = "08:00";
        final String endTime = "09:00";
        final String roomName = "X";

        // Insert an admin
        jdbcTemplate.update("INSERT INTO admin(id, firstname, lastname) VALUES (?, ?, ?)",
                adminId, "firstname", "lastname");
        // Insert a subject
        jdbcTemplate.update("INSERT INTO subject(subject_id) VALUES (?)",
                DEFAULT_SUBJECT_ID);
        // Insert a room
        jdbcTemplate.update("INSERT INTO room(name, capacity) VALUES (?, ?)",
                roomName, 10);

        // Insert a section
        jdbcTemplate.update("INSERT INTO section(section_id, number_of_students, days, start_time, end_time, room_name, subject_subject_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                DEFAULT_SECTION_ID, 0, MTH.ordinal(), LocalTime.of(8,0), LocalTime.of(9,0), roomName, DEFAULT_SUBJECT_ID);

        // When: an admin creates a section
        mockMvc.perform(post("/sections").sessionAttr("admin", adminId)
                .param("sectionId", DEFAULT_SECTION_ID)
                .param("subjectId", DEFAULT_SUBJECT_ID)
                .param("roomName", roomName)
                .param("days", MTH.toString())
                .param("startTime", startTime)
                .param("endTime", endTime)
        );

        // Then: the section is saved to the DB
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM section WHERE section_id = ?",
                Integer.class, DEFAULT_SECTION_ID);

        assertEquals(1, count);

    }

}
