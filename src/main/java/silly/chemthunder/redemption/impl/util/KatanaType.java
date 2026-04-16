package silly.chemthunder.redemption.impl.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.Arrays;
import java.util.List;

import static silly.chemthunder.redemption.impl.index.RedemptionItems.*;

public enum KatanaType {
    AMETHYST("amethyst",
            AMETHYST_KATANA, AMETHYST_SHEATH, AMETHYST_SHEATHED,
            StatusEffects.SPEED
    ),
    COPPER("copper",
            COPPER_KATANA, COPPER_SHEATH, COPPER_SHEATHED,
            StatusEffects.TRIAL_OMEN
    ),
    EMERALD("emerald",
            EMERALD_KATANA, EMERALD_SHEATH, EMERALD_SHEATHED,
            StatusEffects.HERO_OF_THE_VILLAGE
    ),
    LAPIS("lapis",
            LAPIS_KATANA, LAPIS_SHEATH, LAPIS_SHEATHED,
            StatusEffects.HASTE
    ),
    NETHERITE("netherite",
            NETHERITE_KATANA, NETHERITE_SHEATH, NETHERITE_SHEATHED,
            StatusEffects.STRENGTH, StatusEffects.RESISTANCE
    ),
    QUARTZ("quartz",
            QUARTZ_KATANA, QUARTZ_SHEATH, QUARTZ_SHEATHED,
            StatusEffects.FIRE_RESISTANCE
    ),
    REDSTONE("redstone",
            REDSTONE_KATANA, REDSTONE_SHEATH, REDSTONE_SHEATHED,
            StatusEffects.STRENGTH
    ),
    SCULK("sculk",
            SCULK_KATANA, SCULK_SHEATH, SCULK_SHEATHED
    ); // No effects

    public final String id;
    public final Item katana;
    public final Item sheath;
    public final Item sheathed;
    private final List<Item> items;
    public final List<StatusEffectInstance> effectInstances;

    KatanaType(String id, Item katana, Item sheath, Item sheathed, RegistryEntry<StatusEffect>... effects) {
        this.id = id;
        this.katana = katana;
        this.sheath = sheath;
        this.sheathed = sheathed;
        this.items = Arrays.asList(katana, sheath, sheathed);
        this.effectInstances = Arrays.stream(effects).map(entry -> new StatusEffectInstance(entry, 400)).toList();
    }

    public static KatanaType getForItem(Item item) {
        for (KatanaType type : values()) {
            if (type.items.contains(item)) {
                return type;
            }
        }

        return AMETHYST;
    }
}
