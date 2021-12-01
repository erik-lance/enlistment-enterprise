package com.orangeandbronze.enlistment.domain;

import java.time.*;

import static com.orangeandbronze.enlistment.domain.Days.*;

public class TestUtils {

    public static final Schedule MTH830to10 = new Schedule(MTH, new Period(LocalTime.of(8, 30), LocalTime.of(10, 0)));
    public static final Schedule TF10to1130 = new Schedule(TF, new Period(LocalTime.of(10, 0), LocalTime.of(11, 30)));
    public static final Subject DEFAULT_SUBJECT = new Subject("DefaultSubject");
    public static final String DEFAULT_SECTION_ID = "DefaultSection";
    public static int DEFAULT_STUDENT_NUMBER = 10;

//    /**
//     * Return Student with studentNumber "1", no enlisted sections, no taken subjects
//     **/
//    public static Student newDefaultStudent() {
//        return new Student(DEFAULT_STUDENT_NUMBER, "x", "x");
//    }
//
//    public static Section newDefaultSection() {
//        return new Section(DEFAULT_SECTION_ID, DEFAULT_SUBJECT, MTH830to10, new Room("X", 10));
//    }
}
