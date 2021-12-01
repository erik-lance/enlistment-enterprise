package com.orangeandbronze.enlistment.domain;

import javax.persistence.*;

import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.Validate.*;

@Entity
class Room {
    @Id
    private final String roomName;
    private final int capacity;

    Room(String roomName, int capacity) {
        notBlank(roomName);
        isTrue(isAlphanumeric(roomName), "roomName must be alphanumeric, was: " + roomName);
        isTrue(capacity > 0, "capacity must be greater than zero, was: " + capacity);
        this.roomName = roomName;
        this.capacity = capacity;
    }

    void checkIfAtOrOverCapacity(int occupancy) {
        if (occupancy >= capacity) {
            throw new CapacityException("at or over capacity of " + capacity + "  at occupancy of " + occupancy);
        }
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
