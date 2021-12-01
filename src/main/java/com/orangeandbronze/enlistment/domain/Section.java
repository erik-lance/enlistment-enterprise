package com.orangeandbronze.enlistment.domain;

import java.util.*;
import java.util.concurrent.locks.*;

import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.Validate.*;

class Section {

    private final String sectionId;
    private final Subject subject;
    private final Schedule schedule;
    private final Room room;
    private int numberOfStudents;
    private final ReentrantLock lock = new ReentrantLock();

    Section(String sectionId, Subject subject, Schedule schedule, Room room) {
        notBlank(sectionId,
                "sectionId can't be null, empty or whitespace ");
        notNull(subject);
        isTrue(isAlphanumeric(sectionId),
                "sectionId must be alphanumeric, was: "
                        + sectionId);
        notNull(room);
        this.sectionId = sectionId;
        this.subject = subject;
        this.schedule = schedule;
        this.room = room;
    }

    Section(String sectionId, Subject subject, Schedule schedule, Room room, int numberOfStudents) {
        this(sectionId, subject, schedule, room);
        isTrue(numberOfStudents >= 0,
                "numberOfStudents must be non-negative, was: " + numberOfStudents);
        this.numberOfStudents = numberOfStudents;
    }

    void checkForConflict(Section other) {
        this.schedule.checkOverlap(other.schedule);
        if (this.subject.equals(other.subject)) {
            throw new SameSubjectException("This section " + this + " & other section " + other +
                    " have same subject of " + subject);
        }
    }

    int getNumberOfStudents() {
        return numberOfStudents;
    }

    void incrementNumberOfStudents() {
        room.checkIfAtOrOverCapacity(numberOfStudents);
        numberOfStudents++;
    }

    public void decrementNumberOfStudents() {
        numberOfStudents--;
    }

    void checkPrereqs(Collection<Subject> subjectsTaken) {
        notNull(subjectsTaken);
        Collection<Subject> copy = new HashSet<>(subjectsTaken); // sets are quicker to search through
        subject.checkPrereqs(copy);
    }

    /** Locks this object's ReentrantLock **/
    void lock() {
        lock.lock();
    }

    /** Unlock this object's ReentrantLock **/
    void unlock() {
        lock.unlock();
    }

    @Override
    public String toString() {
        return sectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        return sectionId != null ? sectionId.equals(section.sectionId) : section.sectionId == null;
    }

    @Override
    public int hashCode() {
        return sectionId != null ? sectionId.hashCode() : 0;
    }
}
