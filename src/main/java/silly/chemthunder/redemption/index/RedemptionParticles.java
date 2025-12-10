package silly.chemthunder.redemption.index;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import silly.chemthunder.redemption.Redemption;

public interface RedemptionParticles {
    SimpleParticleType HUNTER_OMEN = FabricParticleTypes.simple(true);

    private static void create(String name, ParticleType<?> particle) {
        Registry.register(Registries.PARTICLE_TYPE, Redemption.id(name), particle);
    }

    static void index() {
        create("hunters_omen", HUNTER_OMEN);
    }

    static void clientIndex() {
        ParticleFactoryRegistry.getInstance().register(HUNTER_OMEN, EndRodParticle.Factory::new);
    }
}
