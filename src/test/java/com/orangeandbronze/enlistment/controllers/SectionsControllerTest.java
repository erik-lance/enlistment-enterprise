package com.orangeandbronze.enlistment.controllers;

import com.orangeandbronze.enlistment.domain.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.web.servlet.mvc.support.*;

import java.util.*;

import static com.orangeandbronze.enlistment.domain.Days.MTH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.orangeandbronze.enlistment.domain.TestUtils.*;

class SectionsControllerTest {

    @Test
    void createSection_save_new_section_to_repository() {
        // Given the controller, repositories & valid parameter arguments for creating a section
        // When the controller receives the arguments
        // Then
        // - it should retrieve the entities from the db, create a new section
        // - save the section in the db
        // - set a flash attribute called "sectionSuccessMessage" with the message "Successfully created new section " + sectionId
        // - return the string value "redirect:sections" to redirect to the GET method

    }
}
