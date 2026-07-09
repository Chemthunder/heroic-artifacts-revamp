package net.the_hero_robot.redemption.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.registry.tag.DamageTypeTags.*;
import static net.the_hero_robot.redemption.impl.index.data.RedemptionDamageTypes.DESCEND;
import static net.the_hero_robot.redemption.impl.index.data.RedemptionDamageTypes.IMMOLATION;

/**
 * @author AcoYT
 */
public class RedemptionDamageTypeTagProvider extends FabricTagProvider<DamageType> {
    public RedemptionDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        List<TagKey<DamageType>> bypasses = Arrays.asList(BYPASSES_ARMOR, BYPASSES_SHIELD, BYPASSES_INVULNERABILITY, BYPASSES_COOLDOWN, BYPASSES_EFFECTS, BYPASSES_RESISTANCE, BYPASSES_ENCHANTMENTS);

        bypasses.forEach(tagKey -> this.getOrCreateTagBuilder(tagKey)
                .add(DESCEND)
                .setReplace(false));

        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .add(IMMOLATION)
                .setReplace(false);

        this.getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK)
                .add(DESCEND)
                .setReplace(false);

        this.getOrCreateTagBuilder(TagKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("vitality", "bypasses_vitality")))
                .add(DESCEND, IMMOLATION)
                .setReplace(false);
    }
}
