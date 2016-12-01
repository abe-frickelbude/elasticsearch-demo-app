package de.frickelbude.es.sandbox.service.builder;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.frickelbude.es.sandbox.entity.Inductor;
import de.frickelbude.es.sandbox.service.generator.BasicValueFiller;
import de.frickelbude.es.sandbox.service.generator.ComponentBuilder;

@Component
public class InductorBuilder implements ComponentBuilder<Inductor> {

    private static final float MIN_VALUE = 1e-7f; // 0.1 uH
    private static final float MAX_VALUE = 1.0f; // 1 H

    @Autowired
    private BasicValueFiller basicValueFiller;

    @Override
    public Inductor buildComponent() {
        Inductor inductor = new Inductor();
        basicValueFiller.fill(inductor);
        inductor.setTolerance(basicValueFiller.getRandomTolerance());
        inductor.setValue(RandomUtils.nextFloat(MIN_VALUE, MAX_VALUE));
        return inductor;
    }
}
