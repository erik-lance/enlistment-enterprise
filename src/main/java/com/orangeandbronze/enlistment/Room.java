package com.orangeandbronze.enlistment;

import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.Validate.*;

class Room {
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
        isTrue(occupancy >= 0, "occupancy must be non-negative, was: " + occupancy);
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
