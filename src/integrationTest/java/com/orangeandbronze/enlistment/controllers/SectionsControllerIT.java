package com.orangeandbronze.enlistment.controllers;

import com.orangeandbronze.enlistment.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.testcontainers.junit.jupiter.*;

import java.time.*;
import java.util.*;

import static com.orangeandbronze.enlistment.domain.Days.MTH;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
class SectionsControllerIT extends AbstractControllerIT {

    // No need for multi-threaded test, unlikely that work of two or more admins will collide
    @Test
    void createSection_save_to_db() throws Exception {
        // Given the ff parameter arguments
        final String sectionId = "sectionId";
        final String subjectId = "subjectId";
        final Days days = MTH;
        final String start = "09:00";
        final String end = "10:00";
        final String roomName = "roomName";

        jdbcTemplate.update("INSERT INTO subject (subject_id) VALUES (?)", subjectId);
        jdbcTemplate.update("INSERT INTO room (name, capacity) VALUES (?, ?)", roomName, 10);
        // When the path "sections" is invoked using POST method
        mockMvc.perform(post("/sections").sessionAttr("admin", mock(Admin.class)).param(sectionId, sectionId)
                .param(subjectId, subjectId).param("days", days.name()).param("start", start)
                .param("end", end).param(roomName, roomName));
        // Then new section with correct fields should be found in DB
        Map<String, Object> results = jdbcTemplate.queryForMap("SELECT * FROM section WHERE section_id = ?", sectionId);
        assertAll(
                () -> assertEquals(sectionId, results.get("section_id")),
                () -> assertEquals(subjectId, results.get("subject_subject_id")),
                () -> assertEquals(days.ordinal(), results.get("days")),
                () -> assertEquals(LocalTime.parse(start), LocalTime.parse(results.get("start_time").toString())),
                () -> assertEquals((LocalTime.parse(end)), LocalTime.parse(results.get("end_time").toString())),
                () -> assertEquals(roomName, results.get("room_name"))
        );
    }

}
