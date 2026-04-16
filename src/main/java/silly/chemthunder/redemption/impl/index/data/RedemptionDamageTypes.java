package silly.chemthunder.redemption.impl.index.data;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import silly.chemthunder.redemption.impl.Redemption;

public interface RedemptionDamageTypes {
    RegistryKey<DamageType> KATANA = create("katana");
    RegistryKey<DamageType> DESCEND = create("descend");
    RegistryKey<DamageType> IMMOLATION = create("immolation");

    private static RegistryKey<DamageType> create(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Redemption.id(name));
    }

    static void bootstrap(Registerable<DamageType> registerable) {
        registerable.register(KATANA, new DamageType("katana", 0.0F));
        registerable.register(DESCEND, new DamageType("descend", 0.0F));
        registerable.register(IMMOLATION, new DamageType("immolation", 0.0F));
    }

    static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(key), source, attacker);
    }

    static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(key), attacker);
    }

    static DamageSource create(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(key));
    }
}
