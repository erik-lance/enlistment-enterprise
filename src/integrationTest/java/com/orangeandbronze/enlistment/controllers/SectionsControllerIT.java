package com.orangeandbronze.enlistment.controllers;

import com.orangeandbronze.enlistment.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.jdbc.core.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;


import java.time.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import static com.orangeandbronze.enlistment.domain.Days.MTH;
import static com.orangeandbronze.enlistment.domain.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
@SpringBootTest
class SectionsControllerIT  extends AbstractControllerIT {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private FacultyRepository facultyRepository;

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
        jdbcTemplate.update("INSERT INTO faculty (faculty_number) VALUES (?)", DEFAULT_FACULTY_NUMBER);
        // When the path "sections" is invoked using POST method
        mockMvc.perform(post("/sections").sessionAttr("admin", mock(Admin.class))
                .param("sectionId", sectionId)
                .param("subjectId", subjectId)
                .param("days", days.name())
                .param("start", start)
                .param("end", end)
                .param("roomName", roomName)
                .param("facultyNumber", String.valueOf(DEFAULT_FACULTY_NUMBER))
        );

        // Then the new section with correct fields should be found in DB
        Map<String, Object> results = jdbcTemplate.queryForMap("SELECT * FROM section WHERE section_id = ?", sectionId);
        assertAll(
                () -> assertEquals(sectionId, results.get("section_id")),
                () -> assertEquals(subjectId, results.get("subject_subject_id")),
                () -> assertEquals(days.ordinal(), results.get("days")),
                () -> assertEquals(LocalTime.parse(start), LocalTime.parse(results.get("start_time").toString())),
                () -> assertEquals(LocalTime.parse(end), LocalTime.parse(results.get("end_time").toString())),
                () -> assertEquals(roomName, results.get("room_name")),
                () -> assertEquals(DEFAULT_FACULTY_NUMBER, results.get("instructor_faculty_number"))
        );
    }

    @Test
    void concurrently_create_overlapping_section() throws Exception {
        jdbcTemplate.update("INSERT INTO room (name, capacity) VALUES (?, ?)", "roomName", 20);
        jdbcTemplate.update("INSERT INTO subject (subject_id) VALUES (?)", DEFAULT_SUBJECT_ID);
        startSectionCreationThreads();                     //start multi threads
        assertNumberOfSectionsCreated(3);    //check if multi threading was allowed by checking the number of sections created
    }

    private void assertNumberOfSectionsCreated(int expectedCount){
        int numSections = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM room_sections WHERE room_name = 'roomName'", Integer.class);
        assertEquals(expectedCount, numSections);
    }

    private void startSectionCreationThreads() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            new SectionCreationThread(
                    adminRepository.findById(id).orElseThrow(() ->
                        new NoSuchElementException("No such admin w/ id: " + id + " found in DB.")),
                    facultyRepository.findById(id).orElseThrow(() ->
                        new NoSuchElementException("No such faculty w/ id: " + id + " found in DB.")),
                    latch, mockMvc,Integer.toString(i)).start();
        }
        latch.countDown();
        Thread.sleep(5000); // wait time to allow all the threads to finish
    }
    private static class SectionCreationThread extends Thread {
        private final Admin admin;
        private final Faculty faculty;
        private final CountDownLatch latch;
        private final MockMvc mockMvc;
        private final String sectionId;


        public SectionCreationThread(Admin admin, Faculty faculty, CountDownLatch latch, MockMvc mockMvc,String sectionId) {
            this.admin = admin;
            this.faculty = faculty;
            this.latch = latch;
            this.mockMvc = mockMvc;
            this.sectionId=sectionId;
        }

        @Override
        public void run() {
            try {
                latch.await(); // The thread keeps waiting till it is informed
                mockMvc.perform(post("/sections").sessionAttr("admin", admin)
                        .param("sectionId", sectionId).param("subjectId", DEFAULT_SUBJECT_ID)
                        .param("days", "MTH")
                        .param("start","09:00")
                        .param("end", "11:00")
                        .param("roomName","roomName")
                        .param("facultyNumber", String.valueOf(faculty.getFacultyNumber()))
                );

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
