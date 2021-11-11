package com.orangeandbronze.enlistment;

import java.util.*;
import static org.apache.commons.lang3.Validate.*;

class Student {

    private final int studentNumber;
    private final Collection<Section> sections = new HashSet<>();

    Student(int studentNumber, Collection<Section> sections) {
        isTrue (studentNumber >= 0,
                "studentNumber can't be negative, was: " + studentNumber);
        notNull(sections, "sections can't be null");
        this.studentNumber = studentNumber;
        this.sections.addAll(sections);
        this.sections.removeIf(Objects::isNull);
    }

    Student(int studentNumber) {
        this(studentNumber, Collections.emptyList());
    }

    void enlist(Section newSection) {
        notNull(newSection,"section can't be null") ;
        sections.forEach(currSection -> currSection.checkForConflict(newSection) );
        newSection.lock(); // one thread at a time... this only works if single app instance
        try {
            newSection.incrementNumberOfStudents();
            sections.add(newSection);
        } finally {
            newSection.unlock(); // release lock
        }
    }

    void cancel(Section section) {
        notNull(section);
        if (sections.contains(section)) {
            sections.remove(section);
            section.decrementNumberOfStudents();
        }
    }

    Collection<Section> getSections() {
        return new ArrayList<>(sections);
    }

    @Override
    public String toString() {
        return "Student# " + studentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return studentNumber == student.studentNumber;
    }

    @Override
    public int hashCode() {
        return studentNumber;
    }


}
