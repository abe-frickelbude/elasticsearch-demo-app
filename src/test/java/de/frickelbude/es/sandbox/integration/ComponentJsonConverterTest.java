package de.frickelbude.es.sandbox.integration;

import static org.junit.Assert.*;
import org.joda.time.*;
import org.junit.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import de.frickelbude.es.sandbox.base.*;
import de.frickelbude.es.sandbox.common.*;
import de.frickelbude.es.sandbox.entity.*;
import de.frickelbude.es.sandbox.service.generator.*;
import de.frickelbude.es.sandbox.service.io.*;

public class ComponentJsonConverterTest extends BaseEsSandboxTest {

    private static final int NUM_ELEMENTS = 10000;

    private static final String TEST_JSON_DATA = "{ \"id\" : 123,"
        + "\"type\": \"resistor\",   "
        + "\"category\" : \"passive\",   "
        + "\"description\" : \"test description\",   "
        + "\"manufacturer\" : \"VISHAY\",   "
        + "\"createDate\" : \"2015-08-10T17:25:34.073Z\",   "
        + "\"value\" : 1000.0,   "
        + "\"tolerance\" : 5 "
        + "}";

    @Log
    private static Logger log;

    @Autowired
    private ComponentJsonConverter converter;

    @Autowired
    private RandomComponentFactory componentFactory;

    @Test
    public void testEncode() {
        Component component = componentFactory.makeComponent();
        String jsonData = converter.toJson(component);
        assertNotNull(jsonData);
    }

    @Test
    public void testDecode() {
        Component target = converter.toModel(TEST_JSON_DATA);
        assertNotNull(target);
        assertTrue(target instanceof Resistor);

        Resistor resistor = Resistor.class.cast(target);
        assertEquals(Long.valueOf(123L), resistor.getId());
        assertEquals("resistor", resistor.getType());
        assertEquals("passive", resistor.getCategory());
        assertEquals("test description", resistor.getDescription());
        assertEquals(Manufacturer.VISHAY, resistor.getManufacturer());
        assertEquals(new DateTime("2015-08-10T17:25:34.073Z", DateTimeZone.UTC), resistor.getCreateDate());
        assertEquals(1000.0f, resistor.getValue(), 1e-9);
        assertEquals(5, resistor.getTolerance());
    }

    @SuppressWarnings({
        "resource", "unused"
    })
    @Test
    public void testRoundTrip() {

        // Stopwatch stopwatch = SimonManager.getStopwatch("jsonRoundTripConversion");
        for (int i = 0; i < NUM_ELEMENTS; i++) {

            Component source = componentFactory.makeComponent();
            // Split split = Split.start();
            String jsonData = converter.toJson(source);
            // split.stop();
            // stopwatch.addSplit(split);

            // split = Split.start();
            Component target = converter.toModel(jsonData);
            // split.stop();
            // stopwatch.addSplit(split);
        }
        log.info("Roundtrip conversion times for {} elements", NUM_ELEMENTS);
        // log.info("Min: {} microseconds Max: {} microseconds Avg: {} microseconds",
        // TimeUnit.NANOSECONDS.toMicros(Math.round(stopwatch.getMin())),
        // TimeUnit.NANOSECONDS.toMicros(Math.round(stopwatch.getMax())),
        // TimeUnit.NANOSECONDS.toMicros(Math.round(stopwatch.getMean())));
    }

}
