package de.frickelbude.es.sandbox.rules;

import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Basic [visual] tests for our custom TimingWatcher JUnit rule.
 * 
 * @author Ibragim Kuliev
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimingWatcherTest {

    @Rule
    public TimingWatcher watcher = new TimingWatcher();

    @Test
    public void test1() throws InterruptedException {
        Thread.sleep(1300);
    }

    @Test
    public void test2() {
        for (int i = 0; i < 1000000; i++) {
            String.valueOf(i);
        }
    }

    @Test
    public void testFailed3() throws InterruptedException {
        Thread.sleep(150);
        fail("failed!");
    }

    @Test
    @Ignore
    public void testSkipped4() {

    }

}
