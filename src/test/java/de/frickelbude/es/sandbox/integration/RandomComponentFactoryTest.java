package de.frickelbude.es.sandbox.integration;

import static org.junit.Assert.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import org.junit.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import de.frickelbude.es.sandbox.base.*;
import de.frickelbude.es.sandbox.common.*;
import de.frickelbude.es.sandbox.entity.*;
import de.frickelbude.es.sandbox.service.generator.*;

/**
 * Integration test for {@linkplain RandomComponentFactory}.
 * 
 * @author Ibragim Kuliev
 *
 */
public class RandomComponentFactoryTest extends BaseEsSandboxTest {

    private static final int NUM_ELEMENTS = 1000000;

    @Log
    private static Logger log;

    @Autowired
    private RandomComponentFactory componentFactory;

    @Test
    public void testMakeComponent() {
        for (int i = 0; i < 100; i++) {
            Component component = componentFactory.makeComponent();
            assertNotNull(component);
            assertNotNull(component.getId());
            assertNotNull(component.getDescription());
            assertNotNull(component.getCreateDate());
            assertNotNull(component.getManufacturer());
            log.debug(component.toString());
        }
    }

    @Test
    public void testComponentDistribution() {

        // initialize counters
        Map<Class<?>, AtomicInteger> counters = new HashMap<>();
        for (Class<?> componentClass : componentFactory.getComponentClasses()) {
            counters.put(componentClass, new AtomicInteger());
        }

        // Stopwatch stopwatch = SimonManager.getStopwatch("componentFactoryTest");
        // Split split = stopwatch.start();

        for (int i = 0; i < NUM_ELEMENTS; i++) {
            Component component = componentFactory.makeComponent();
            counters.get(component.getClass()).getAndIncrement();
        }
        // split.stop();
        // split.close();

        // log.info("Generating {} elements took:\n{}", NUM_ELEMENTS, stopwatch.toString());
        log.info("Generated component distribution:");
        for (Class<?> componentClass : counters.keySet()) {
            log.info("{} : {}", componentClass.getSimpleName(), counters.get(componentClass));
        }
        log.info("\n");
    }
}
