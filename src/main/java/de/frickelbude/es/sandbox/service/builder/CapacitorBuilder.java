package de.frickelbude.es.sandbox.service.builder;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.frickelbude.es.sandbox.entity.Capacitor;
import de.frickelbude.es.sandbox.service.generator.BasicValueFiller;
import de.frickelbude.es.sandbox.service.generator.ComponentBuilder;

@Component
public class CapacitorBuilder implements ComponentBuilder<Capacitor> {

    private static final float MIN_VALUE = 1e-12f; // 1pF
    private static final float MAX_VALUE = 1e-3f; // 1000 uF

    @Autowired
    private BasicValueFiller basicValueFiller;

    @Override
    public Capacitor buildComponent() {
        Capacitor capacitor = new Capacitor();
        basicValueFiller.fill(capacitor);
        capacitor.setTolerance(basicValueFiller.getRandomTolerance());
        capacitor.setValue(RandomUtils.nextFloat(MIN_VALUE, MAX_VALUE));
        return capacitor;
    }
}
