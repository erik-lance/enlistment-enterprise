package com.orangeandbronze.enlistment.domain;

class ScheduleConflictException extends RuntimeException {
    ScheduleConflictException(String msg) {
        super(msg);
    }
}
