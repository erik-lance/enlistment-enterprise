package com.orangeandbronze.enlistment.domain;

import javax.persistence.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.*;

@Entity
public class Faculty {
    @Id
    private final int facultyNumber;
    @OneToMany
    private final Collection<Section> sections = new HashSet<>();
    Faculty(int facultyNumber) {
        isTrue(facultyNumber >= 0, "faculty number must be non-negative, was: "+facultyNumber);
        this.facultyNumber = facultyNumber;
    }

    public void addSection(Section section) {
        notNull(section);
        checkForScheduleConflict(section);
        sections.add(section);
    }

    private void checkForScheduleConflict(Section section) {
        sections.forEach(s -> s.checkForScheduleConflict(section));
    }
    @Override
    public String toString() {
        return "Faculty# " + facultyNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;
        return facultyNumber == faculty.facultyNumber;
    }

    public int getFacultyNumber() {
        return facultyNumber;
    }

    @Override
    public int hashCode() {
        return facultyNumber;
    }

    /* Do not call! For JPA only! */
    private Faculty() {
        facultyNumber = -1;
    }
}
