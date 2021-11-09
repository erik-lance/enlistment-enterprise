package com.orangeandbronze.enlistment;

import java.util.concurrent.locks.*;

import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.Validate.*;

class Section {

    private final String sectionId;
    private final Schedule schedule;
    private final Room room;
    private int numberOfStudents;
    private final ReentrantLock lock = new ReentrantLock();

    Section(String sectionId, Schedule schedule, Room room) {
        notBlank(sectionId,
                "sectionId can't be null, empty or whitespace ");
        isTrue(isAlphanumeric(sectionId),
                "sectionId must be alphanumeric, was: "
                        + sectionId);
        notNull(room);
        this.sectionId = sectionId;
        this.schedule = schedule;
        this.room = room;
    }

    Section(String sectionId, Schedule schedule, Room room, int numberOfStudents) {
        this(sectionId, schedule, room);
        isTrue(numberOfStudents >= 0,
                "numberOfStudents must be non-negative, was: " + numberOfStudents);
        this.numberOfStudents = numberOfStudents;
    }

    void checkForConflict(Section other) {
        if (this.schedule.equals(other.schedule)) {
            throw new ScheduleConflictException(
                    "schedule conflict between current section " +
                            this + " and new section " + other +
                            " at schedule " + this.schedule);
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
