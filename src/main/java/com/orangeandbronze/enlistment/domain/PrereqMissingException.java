package com.orangeandbronze.enlistment.domain;

class PrereqMissingException extends RuntimeException {
    PrereqMissingException(String msg) {
        super(msg);
    }
}
