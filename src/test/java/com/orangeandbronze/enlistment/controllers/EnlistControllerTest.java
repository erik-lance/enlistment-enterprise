package com.orangeandbronze.enlistment.controllers;

import com.orangeandbronze.enlistment.domain.*;
import org.hibernate.*;
import org.junit.jupiter.api.*;

import javax.persistence.*;
import java.util.*;

import static com.orangeandbronze.enlistment.domain.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class EnlistControllerTest {

    @Test
    void enlist_student_in_section() {
        // Given a student & the sectionId of the section that the student wants to enroll in
        EnlistController controller = new EnlistController();
        Student student = newDefaultStudent();
        SectionRepository sectionRepository = null;
        controller.setSectionRepo(sectionRepository);
        // When the parqmeters are received in the POST method
        controller.enlistOrCancel(student, DEFAULT_SECTION_ID, UserAction.ENLIST);
    }

}
