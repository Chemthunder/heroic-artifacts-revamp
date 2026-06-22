package net.the_hero_robot.redemption.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.index.data.RedemptionDamageTypes;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class RedemptionDynamicRegistryProvider extends FabricDynamicRegistryProvider {
    public RedemptionDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        RedemptionDamageTypes.TYPES.addEntries(registries, entries);
    }

    public String getName() {
        return Redemption.MOD_ID + "_dynamic";
    }
}
