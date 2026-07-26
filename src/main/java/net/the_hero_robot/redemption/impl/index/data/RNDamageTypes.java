package net.the_hero_robot.redemption.impl.index.data;

import net.acoyt.acornlib.api.builder.KeyedBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.Redemption;
import org.jetbrains.annotations.Nullable;

/**
 * @author AcoYT
 */
public interface RNDamageTypes {
    KeyedBuilder<DamageType> TYPES = new KeyedBuilder<>(Redemption.MOD_ID, RegistryKeys.DAMAGE_TYPE);

    RegistryKey<DamageType> KATANA = TYPES.register("katana", new DamageType("katana", 0.0F));
    RegistryKey<DamageType> DESCEND = TYPES.register("descend", new DamageType("descend", 0.0F));
    RegistryKey<DamageType> IMMOLATION = TYPES.register("immolation", new DamageType("immolation", 0.0F));

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
