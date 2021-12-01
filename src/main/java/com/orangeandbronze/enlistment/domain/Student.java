package com.orangeandbronze.enlistment.domain;

import javax.persistence.*;
import java.util.*;
import static org.apache.commons.lang3.Validate.*;
@Entity
class Student {
    @Id
    private final int studentNumber;
    private final String firstname;
    private final String lastname;
    @ManyToMany
    private final Collection<Section> sections = new HashSet<>();
    @ManyToMany
    private final Collection<Subject> subjectsTaken = new HashSet<>();

    Student(int studentNumber, String firstname, String lastname, Collection<Section> sections, Collection<Subject> subjectsTaken) {
        isTrue (studentNumber >= 0,
                "studentNumber can't be negative, was: " + studentNumber);
        notBlank(firstname);
        notBlank(lastname);
        notNull(sections, "sections can't be null");
        notNull(subjectsTaken);
        this.studentNumber = studentNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sections.addAll(sections);
        this.sections.removeIf(Objects::isNull);
        this.subjectsTaken.addAll(subjectsTaken);
        this.subjectsTaken.removeIf(Objects::isNull);
    }

    Student(int studentNumber, String firstname, String lastname) {
        this(studentNumber, firstname, lastname, Collections.emptyList(), Collections.emptyList());
    }

    Student(int studentNumber, String firstname, String lastname, Collection<Section> sections) {
        this(studentNumber, firstname, lastname, sections, Collections.emptyList());
    }

    void enlist(Section newSection) {
        notNull(newSection,"section can't be null") ;
        sections.forEach(currSection -> currSection.checkForConflict(newSection) );
        newSection.checkPrereqs(subjectsTaken);
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
