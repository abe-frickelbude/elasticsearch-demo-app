package de.frickelbude.es.sandbox.service.builder;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.frickelbude.es.sandbox.entity.BipolarTransistor;
import de.frickelbude.es.sandbox.service.generator.BasicValueFiller;
import de.frickelbude.es.sandbox.service.generator.ComponentBuilder;
import de.frickelbude.es.sandbox.util.RandomValueUtils;

@Component
public class BipolarTransistorBuilder implements ComponentBuilder<BipolarTransistor> {

    private static final float MIN_IC_MAX = 0.01f; // 10 mA
    private static final float MAX_IC_MAX = 5.0f; // 5 A (perhaps we have a 2N3055 here....? :D)

    private static final int MIN_VCEMAX = 10; // 10 V
    private static final int MAX_VCEMAX = 600; // 10 V

    private static final int MIN_HFE = 20;
    private static final int MAX_HFE = 500;

    @Autowired
    private BasicValueFiller basicValueFiller;

    @Override
    public BipolarTransistor buildComponent() {

        BipolarTransistor bjt = new BipolarTransistor();
        basicValueFiller.fill(bjt);
        bjt.setPolarity(RandomValueUtils.pickRandomValue(BipolarTransistor.Polarity.values()));
        bjt.sethFE(RandomUtils.nextInt(MIN_HFE, MAX_HFE + 1));
        bjt.setVceMax(RandomUtils.nextInt(MIN_VCEMAX, MAX_VCEMAX + 1));
        bjt.setIcMax(RandomUtils.nextFloat(MIN_IC_MAX, MAX_IC_MAX));
        return bjt;
    }
}
