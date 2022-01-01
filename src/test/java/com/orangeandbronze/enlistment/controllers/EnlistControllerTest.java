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
        // Given the Controller w/ a student in session, param of sectionId to enlist, & UserAction "ENLIST"
        Student student = mock(Student.class);
        String sectionId = "X";
        UserAction userAction = UserAction.ENLIST;
        // When enlist (post) method is called
        SectionRepository sectionRepository = mock(SectionRepository.class);
        Section section = newDefaultSection();
        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(section));
        StudentRepository studentRepository = mock(StudentRepository.class);
        EnlistController controller = new EnlistController();
        controller.setSectionRepo(sectionRepository);
        controller.setStudentRepo(studentRepository);
        EntityManager entityManager = mock(EntityManager.class);
        Session session = mock(Session.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);
        controller.setEntityManager(entityManager);
        String returnVal = controller.enlistOrCancel(student, sectionId, userAction);
        // Then
        // - retrieve the Section object from the DB using the sectionId
        verify(sectionRepository).findById(sectionId);
        // - student.enlist method will be called, passing in the section
        verify(student).enlist(section);
        // - save student to DB
        verify(studentRepository).save(student);
        // - save section to DB
        verify(sectionRepository).save(section);
        assertEquals("redirect:enlist", returnVal);
    }

}
