package com.orangeandbronze.enlistment.domain;

import javax.persistence.*;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isAlphanumeric;
import static org.apache.commons.lang3.Validate.*;
@Entity
public class Subject {
    @Id
    private final String subjectId;
    @ManyToMany
    private final Collection<Subject> prerequisites = new HashSet<>();

    Subject(String subjectId, Collection<Subject> prerequisites) {
        notBlank(subjectId);
        notNull(prerequisites);
        isTrue(isAlphanumeric(subjectId), "subjectId must be alphanumeric, was: " + subjectId);
        this.subjectId = subjectId;
        this.prerequisites.addAll(prerequisites);
        this.prerequisites.removeIf(Objects::isNull);
    }

    public Subject(String subjectId) {
        this(subjectId, Collections.emptyList());
    }

    void checkPrereqs(Collection<Subject> subjectsTaken) {
        notNull(subjectsTaken);
        Collection<Subject> copySubjectsTaken = new HashSet<>(subjectsTaken); // sets are quicker to search through
        if (!copySubjectsTaken.containsAll(prerequisites)) {
            Collection<Subject> copyOfPrereqs = new HashSet<>(prerequisites);
            copyOfPrereqs.removeAll(copySubjectsTaken);
            throw new PrereqMissingException(
                    "missing prereqs: " + copyOfPrereqs);
        }
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

    // For JPA only! Do not call!
    private Subject() {
        this.subjectId = null;
    }
}
