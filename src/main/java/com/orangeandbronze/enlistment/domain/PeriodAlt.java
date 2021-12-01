package com.orangeandbronze.enlistment.domain;

import static org.apache.commons.lang3.Validate.*;

class PeriodAlt {

    private final Time startTime;
    private final Time endTime;

    PeriodAlt(Time startTime, Time endTime) {
        notNull(startTime);
        notNull(endTime);
        isTrue(startTime.isBefore(endTime), "start cannot be at or after end, start: " + startTime + " end: " + endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    void checkOverlap(PeriodAlt other) {
        if (this.startTime.isBefore(other.endTime) && this.endTime.isAfter(other.startTime)) {
            throw new ScheduleConflictException("Period overlap between this: " + this + " & other: " + other);
        }
    }

    @Override
    public String toString() {
        return startTime + " - " + endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PeriodAlt periodAlt = (PeriodAlt) o;

        if (startTime != periodAlt.startTime) return false;
        return endTime == periodAlt.endTime;
    }

    @Override
    public int hashCode() {
        int result = startTime != null ? startTime.hashCode() : 0;
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }
}


enum Time {
    H0830, H0900, H0930, H1000, H1030, H1100, H1130, H1200, H1230, H1300,
    H1330, H1400, H1430, H1500, H1530, H1600, H1630, H1700, H1730;

    boolean isBefore(Time other) {
        return this.ordinal() < other.ordinal();
    }

    boolean isAfter(Time other) {
        return this.ordinal() > other.ordinal();
    }
}

