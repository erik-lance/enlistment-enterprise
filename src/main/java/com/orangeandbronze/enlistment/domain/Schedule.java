package com.orangeandbronze.enlistment.domain;

import static org.apache.commons.lang3.Validate.notNull;

class Schedule {
    private final Days days;
    private final Period period;

    Schedule(Days days, Period period) {
        notNull(days);
        notNull(period);
        this.days = days;
        this.period = period;
    }

    void checkOverlap(Schedule other) {
        if (this.days.equals(other.days)) {
            this.period.checkOverlap(other.period);
        }
    }

    @Override
    public String toString() {
        return days + " " + period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (days != schedule.days) return false;
        return period == schedule.period;
    }

    @Override
    public int hashCode() {
        int result = days != null ? days.hashCode() : 0;
        result = 31 * result + (period != null ? period.hashCode() : 0);
        return result;
    }
}

enum Days {
    MTH, TF, WS
}

