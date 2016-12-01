package de.frickelbude.es.sandbox.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A simple test watcher that additionally provides measurement of time intervals spent in individual tests.
 * 
 * @author Ibragim Kuliev
 *
 */
public class TimingWatcher implements TestRule {

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new TimingStatement(base, description);
    }
}
