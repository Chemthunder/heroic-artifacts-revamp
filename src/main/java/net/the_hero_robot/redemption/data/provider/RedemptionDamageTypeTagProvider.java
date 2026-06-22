package net.the_hero_robot.redemption.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.the_hero_robot.redemption.impl.index.data.RedemptionDamageTypes;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class RedemptionDamageTypeTagProvider extends FabricTagProvider<DamageType> {
    public RedemptionDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .add(RedemptionDamageTypes.DESCEND)
                .add(RedemptionDamageTypes.IMMOLATION)
                .setReplace(false);

        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_SHIELD)
                .add(RedemptionDamageTypes.DESCEND)
                .setReplace(false);

        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY)
                .add(RedemptionDamageTypes.DESCEND)
                .setReplace(false);

        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN)
                .add(RedemptionDamageTypes.DESCEND)
                .setReplace(false);

        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS)
                .add(RedemptionDamageTypes.DESCEND)
                .setReplace(false);

        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE)
                .add(RedemptionDamageTypes.DESCEND)
                .setReplace(false);

        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS)
                .add(RedemptionDamageTypes.DESCEND)
                .setReplace(false);

        this.getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK)
                .add(RedemptionDamageTypes.DESCEND)
                .setReplace(false);
    }
}
