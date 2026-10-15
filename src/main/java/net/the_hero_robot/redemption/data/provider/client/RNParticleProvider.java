package net.the_hero_robot.redemption.data.provider.client;

import net.acoyt.acornlib.data.provider.resources.AcornParticleGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.index.RNParticles;

/**
 * @author AcoYT
 */
public class RNParticleProvider extends AcornParticleGen {
    public RNParticleProvider(FabricDataOutput output) {
        super(output);
    }

    public void generate(ParticleDataConsumer consumer) {
        consumer.accept(RNParticles.HUNTERS_OMEN, Redemption.id("hunters_omen"));
    }
}
