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
        final String roomName = "roomName";
        SubjectRepository subjectRepository = mock(SubjectRepository.class);
        when(subjectRepository.findById(DEFAULT_SUBJECT_ID)).thenReturn(Optional.of(mock(Subject.class)));
        RoomRepository roomRepository = mock(RoomRepository.class);
        when(roomRepository.findById(roomName)).thenReturn(Optional.of(mock(Room.class)));
        SectionRepository sectionRepository = mock(SectionRepository.class);
        FacultyRepository facultyRepository = mock(FacultyRepository.class);
        when(facultyRepository.findById(DEFAULT_FACULTY_NUMBER)).thenReturn(Optional.of(DEFAULT_FACULTY));
        SectionsController sectionsController = new SectionsController();
        sectionsController.setSubjectRepo(subjectRepository);
        sectionsController.setSectionRepo(sectionRepository);
        sectionsController.setRoomRepo(roomRepository);
        sectionsController.setFacultyRepository(facultyRepository);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        // When the controller receives them
        sectionsController.createSection(DEFAULT_SECTION_ID, DEFAULT_SUBJECT_ID, MTH, "09:00", "10:00", roomName, DEFAULT_FACULTY_NUMBER, redirectAttributes);
        // Then it should retrieve the entities from the db, create a new section, & save the section in the db
        ArgumentCaptor<Section> sectionArgumentCaptor = ArgumentCaptor.forClass(Section.class);
        assertAll(
                () -> verify(subjectRepository).findById(DEFAULT_SUBJECT_ID),
                () -> verify(roomRepository).findById(roomName),
                () -> verify(sectionRepository).save(sectionArgumentCaptor.capture()), // section was saved
                () -> assertEquals(DEFAULT_SECTION_ID, sectionArgumentCaptor.getValue().getSectionId()) // the correct sectionId
        );

    }
}
