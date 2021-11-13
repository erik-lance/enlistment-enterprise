package com.orangeandbronze.enlistment;

import static org.apache.commons.lang3.StringUtils.isAlphanumeric;
import static org.apache.commons.lang3.Validate.*;

class Subject {

    private final String subjectId;

    Subject(String subjectId) {
        notBlank(subjectId);
        isTrue(isAlphanumeric(subjectId), "subjectId must be alphanumeric, was: " + subjectId);
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        return subjectId != null ? subjectId.equals(subject.subjectId) : subject.subjectId == null;
    }

    @Override
    public int hashCode() {
        return subjectId != null ? subjectId.hashCode() : 0;
    }
}
