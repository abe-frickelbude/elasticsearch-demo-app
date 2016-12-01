package de.frickelbude.es.sandbox.service.generator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Basic sequence generator that uses an AtomicLong instance to synchronize access and produce globally unique IDs, as
 * long as the generator instance exists and is not altered via setSeed().
 * 
 * The class is assumed to be operated as a singleton, otherwise the behavior cannot be guaranteed.
 * 
 * @author Ibragim Kuliev
 *
 */
public final class IdGenerator {

    private final AtomicLong sequence;

    public IdGenerator() {
        this(0);
    }

    public IdGenerator(final long initialValue) {
        sequence = new AtomicLong(initialValue);
    }

    public void setSeed(final long seedValue) {
        sequence.set(seedValue);
    }

    public long getNextId() {
        return sequence.getAndIncrement();
    }
}
