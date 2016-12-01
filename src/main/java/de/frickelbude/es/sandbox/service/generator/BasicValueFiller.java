package de.frickelbude.es.sandbox.service.generator;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.frickelbude.es.sandbox.entity.Component;
import de.frickelbude.es.sandbox.entity.Manufacturer;
import de.frickelbude.es.sandbox.util.RandomValueUtils;

/**
 * 
 * @author Ibragim Kuliev
 *
 */
@Service
public class BasicValueFiller {

    private static final Integer TOLERANCES[] = {
        1, 2, 5, 10
    };

    @Autowired
    private IdGenerator idGenerator;

    public Integer getRandomTolerance() {
        return RandomValueUtils.pickRandomValue(TOLERANCES);
    }

    public Manufacturer getRandomManufacturer() {
        return RandomValueUtils.pickRandomValue(Manufacturer.values());
    }

    public <T> String getRandomDescription(final Class<T> componentClass) {
        return componentClass.getSimpleName() + "-" + RandomStringUtils.randomAlphanumeric(32);
    }

    public Component fill(final Component component) {
        component.setId(Long.valueOf(idGenerator.getNextId()));
        component.setDescription(getRandomDescription(component.getClass()));
        component.setManufacturer(getRandomManufacturer());
        return component;
    }

}
