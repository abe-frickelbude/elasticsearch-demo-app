package de.frickelbude.es.sandbox.service.builder;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.frickelbude.es.sandbox.entity.Diode;
import de.frickelbude.es.sandbox.service.generator.BasicValueFiller;
import de.frickelbude.es.sandbox.service.generator.ComponentBuilder;

@Component
public class DiodeBuilder implements ComponentBuilder<Diode> {

    private static float MIN_VF = 0.15f; // can be this low for Schottky diodes
    private static float MAX_VF = 0.7f;
    private static int MIN_VREV_MAX = 30;
    private static int MAX_VREV_MAX = 80;
    private static float MIN_IF_MAX = 0.01f; // 10 mA, applicable e.g. to small-signal diodes
    private static float MAX_IF_MAX = 10.0f; // 10 A

    @Autowired
    private BasicValueFiller basicValueFiller;

    @Override
    public Diode buildComponent() {
        Diode diode = new Diode();
        basicValueFiller.fill(diode);
        diode.setVf(RandomUtils.nextFloat(MIN_VF, MAX_VF));
        diode.setVrevMax(RandomUtils.nextInt(MIN_VREV_MAX, MAX_VREV_MAX + 1));
        diode.setIfMax(RandomUtils.nextFloat(MIN_IF_MAX, MAX_IF_MAX));
        return diode;
    }
}
