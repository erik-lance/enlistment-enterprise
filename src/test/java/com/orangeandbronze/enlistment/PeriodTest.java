package com.orangeandbronze.enlistment;

import org.junit.jupiter.api.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class PeriodTest {

    @Test
    void newPeriod_valid_minute() {
        assertDoesNotThrow(() -> new Period(LocalTime.of(8, 30), LocalTime.of(10, 0)));
    }

    @Test
    void newPeriod_invalid_minute() {
        assertAll(
                () -> assertThrows(InvalidTimeException.class, () -> new Period(LocalTime.of(9, 15), LocalTime.of(10, 0))),
                () -> assertThrows(InvalidTimeException.class, () -> new Period(LocalTime.of(8, 30), LocalTime.of(10, 15)))
        );
    }
}
