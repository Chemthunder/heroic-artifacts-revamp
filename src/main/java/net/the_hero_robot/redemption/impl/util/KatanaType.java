package net.the_hero_robot.redemption.impl.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.StringIdentifiable;

import java.util.Arrays;
import java.util.List;

import static net.the_hero_robot.redemption.impl.index.RedemptionItems.*;

/**
 * @author AcoYT
 */
@SuppressWarnings("deprecation")
public enum KatanaType implements StringIdentifiable {
    AMETHYST("amethyst",
            AMETHYST_KATANA,
            StatusEffects.SPEED
    ),
    COPPER("copper",
            COPPER_KATANA,
            StatusEffects.TRIAL_OMEN
    ),
    EMERALD("emerald",
            EMERALD_KATANA,
            StatusEffects.HERO_OF_THE_VILLAGE
    ),
    LAPIS("lapis",
            LAPIS_KATANA,
            StatusEffects.HASTE
    ),
    NETHERITE("netherite",
            NETHERITE_KATANA,
            StatusEffects.STRENGTH, StatusEffects.RESISTANCE
    ),
    QUARTZ("quartz",
            QUARTZ_KATANA,
            StatusEffects.FIRE_RESISTANCE
    ),
    REDSTONE("redstone",
            REDSTONE_KATANA,
            StatusEffects.STRENGTH
    ),
    SCULK("sculk",
            SCULK_KATANA
    ), // No effects
    ASHIRO("ashiro",
            ASHIRO_KATANA,
            StatusEffects.GLOWING
    );

    public static final EnumCodec<KatanaType> CODEC = StringIdentifiable.createCodec(KatanaType::values);

    public final String id;
    public final Item katana;
    public final List<RegistryEntry<StatusEffect>> effectInstances;

    KatanaType(String id, Item katana, RegistryEntry<StatusEffect>... effects) {
        this.id = id;
        this.katana = katana;
        this.effectInstances = List.of(effects);
    }

    public String asString() {
        return this.id;
    }
}
