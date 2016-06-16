package com.gigigo.orchextra.domain.abstractions.threads;

public interface ThreadSpec {
    void execute(Runnable action);
}
