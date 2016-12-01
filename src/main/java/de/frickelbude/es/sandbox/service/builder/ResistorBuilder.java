package de.frickelbude.es.sandbox.service.builder;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.frickelbude.es.sandbox.entity.Resistor;
import de.frickelbude.es.sandbox.service.generator.BasicValueFiller;
import de.frickelbude.es.sandbox.service.generator.ComponentBuilder;

@Component
public class ResistorBuilder implements ComponentBuilder<Resistor> {

    private static final float MIN_VALUE = 0.05f; // 5 mOhm
    private static final float MAX_VALUE = 1.0e7f; // 10 MOhm

    @Autowired
    private BasicValueFiller basicValueFiller;

    @Override
    public Resistor buildComponent() {
        Resistor resistor = new Resistor();
        basicValueFiller.fill(resistor);
        resistor.setTolerance(basicValueFiller.getRandomTolerance());
        resistor.setValue(RandomUtils.nextFloat(MIN_VALUE, MAX_VALUE));
        return resistor;
    }
}
