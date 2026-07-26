package net.the_hero_robot.redemption.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.index.data.RNDamageTypes;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class RNDynamicRegistryProvider extends FabricDynamicRegistryProvider {
    public RNDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        RNDamageTypes.TYPES.addEntries(registries, entries);
    }

    public String getName() {
        return Redemption.MOD_ID + "_dynamic";
    }
}
