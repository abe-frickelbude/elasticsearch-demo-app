package de.frickelbude.es.sandbox.service.builder;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.frickelbude.es.sandbox.entity.MosfetTransistor;
import de.frickelbude.es.sandbox.service.generator.BasicValueFiller;
import de.frickelbude.es.sandbox.service.generator.ComponentBuilder;
import de.frickelbude.es.sandbox.util.RandomValueUtils;

@Component
public class MosfetTransistorBuilder implements ComponentBuilder<MosfetTransistor> {

    private static final float MIN_IDS_MAX = 0.1f; // 100 mA
    private static final float MAX_IDS_MAX = 10.0f; // 10 A
    private static final float MIN_RDS_ON = 0.001f; // 1 mOhm, not unrealistic for modern low-voltage MOSFETs
    private static final float MAX_RDS_ON = 25.0f; // 25 Ohm

    @Autowired
    private BasicValueFiller basicValueFiller;

    @Override
    public MosfetTransistor buildComponent() {
        MosfetTransistor mfet = new MosfetTransistor();
        basicValueFiller.fill(mfet);
        mfet.setPolarity(RandomValueUtils.pickRandomValue(MosfetTransistor.Polarity.values()));
        mfet.setIdsMax(RandomUtils.nextFloat(MIN_IDS_MAX, MAX_IDS_MAX));
        mfet.setRdsON(RandomUtils.nextFloat(MIN_RDS_ON, MAX_RDS_ON));
        return mfet;
    }
}
