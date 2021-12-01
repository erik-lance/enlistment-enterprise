package com.orangeandbronze.enlistment.domain;

import javax.persistence.*;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.Validate.*;

@Entity
class Room {
    @Id
    private final String roomName;
    private final int capacity;
    private final Collection<Section> sections = new HashSet<>();

    Room(String roomName, int capacity, Collection<Section> sections) {
        notBlank(roomName);
        isTrue(isAlphanumeric(roomName), "roomName must be alphanumeric, was: " + roomName);
        isTrue(capacity > 0, "capacity must be greater than zero, was: " + capacity);
        notNull(sections);
        this.roomName = roomName;
        this.capacity = capacity;
        this.sections.addAll(sections);
        this.sections.removeIf(Objects::isNull);
    }

    Room(String roomName, int capacity) {
        this(roomName, capacity, Collections.emptyList());
    }

    void checkIfAtOrOverCapacity(int occupancy) {
        if (occupancy >= capacity) {
            throw new CapacityException("at or over capacity of " + capacity + "  at occupancy of " + occupancy);
        }
    }

    void addSection(Section newSection) {
        notNull(newSection);
        sections.forEach(currSection -> currSection.checkForScheduleConflict(newSection));
        sections.add(newSection);
    }

    @Override
    public String toString() {
        return roomName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return roomName != null ? roomName.equals(room.roomName) : room.roomName == null;
    }

    @Override
    public int hashCode() {
        return roomName != null ? roomName.hashCode() : 0;
    }
}
