package com.orangeandbronze.enlistment.controllers;

import com.orangeandbronze.enlistment.domain.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.*;

import java.time.LocalTime;
import java.util.*;

import static com.orangeandbronze.enlistment.domain.Days.MTH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.orangeandbronze.enlistment.domain.TestUtils.*;

class SectionsControllerTest {

    @Test
    void createSection_save_new_section_to_repository() {
        // Given the controller, repositories & valid parameter arguments for creating a section

        String roomId = "AS204";
        String sectionId = "SAM";
        String subjectId = "Psych101";

        LocalTime startTime = LocalTime.of(17, 0);
        LocalTime endTime = LocalTime.of(17, 30);
        String start = startTime.toString();
        String end = endTime.toString();

        SectionRepository sectionRepository = mock(SectionRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        SubjectRepository subjectRepository = mock(SubjectRepository.class);
        RedirectAttributes redirectAttrs = mock(RedirectAttributes.class);

        SectionsController controller = new SectionsController();
        // When the controller receives the arguments

        Room room = new Room(roomId, 10);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        Subject subject = new Subject(subjectId);
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        Schedule schedule = new Schedule(MTH, new Period(startTime, endTime));
        Section section = new Section(sectionId, subject, schedule, room);
        when(sectionRepository.save(section)).thenReturn(section);

        room.removeSection(section);

        controller.setRoomRepo(roomRepository);
        controller.setSubjectRepo(subjectRepository);
        controller.setSectionRepo(sectionRepository);


        // Then
        // - it should retrieve the entities from the db, create a new section
        String returnVal = controller.createSection(sectionId, subjectId, MTH, start,end, roomId, redirectAttrs);
        // - save the section in the db
        verify(sectionRepository).save(section);
        // - set a flash attribute called "sectionSuccessMessage" with the message "Successfully created new section " + sectionId
        verify(redirectAttrs).addFlashAttribute("sectionSuccessMessage", "Successfully created new section " + sectionId);
        // - return the string value "redirect:sections" to redirect to the GET method
        assertEquals("redirect:sections", returnVal);
    }
}
