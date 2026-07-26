package net.the_hero_robot.redemption.impl.index;

import net.acoyt.acornlib.api.registrants.ParticleTypeRegistrant;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.the_hero_robot.redemption.impl.Redemption;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public interface RNParticles {
    ParticleTypeRegistrant PARTICLES = new ParticleTypeRegistrant(Redemption.MOD_ID);

    SimpleParticleType HUNTER_OMEN = PARTICLES.register("hunters_omen", FabricParticleTypes.simple(true));

    static void init() {}
}
