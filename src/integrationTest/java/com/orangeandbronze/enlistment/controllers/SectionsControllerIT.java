package com.orangeandbronze.enlistment.controllers;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.testcontainers.junit.jupiter.*;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
class SectionsControllerIT {

    // No need for multi-threaded test, unlikely that work of two or more admins will collide
    @Test
    void createSection_save_to_db() throws Exception {

    }

}
