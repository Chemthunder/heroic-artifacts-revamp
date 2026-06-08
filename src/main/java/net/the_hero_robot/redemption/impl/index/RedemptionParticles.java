package net.the_hero_robot.redemption.impl.index;

import net.acoyt.acornlib.api.registrants.ParticleTypeRegistrant;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.particle.SimpleParticleType;
import net.the_hero_robot.redemption.impl.Redemption;

public interface RedemptionParticles {
    ParticleTypeRegistrant PARTICLES = new ParticleTypeRegistrant(Redemption.MOD_ID);

    SimpleParticleType HUNTER_OMEN = PARTICLES.register("hunters_omen", FabricParticleTypes.simple(true));

    static void init() {}

    static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(HUNTER_OMEN, EndRodParticle.Factory::new);
    }
}
