package net.theherorobot.redemption.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.theherorobot.redemption.impl.Redemption;
import net.theherorobot.redemption.impl.index.data.RedemptionDamageTypes;

import java.util.concurrent.CompletableFuture;

public class RedemptionDynamicRegistryGen extends FabricDynamicRegistryProvider {
    public RedemptionDynamicRegistryGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        RedemptionDamageTypes.TYPES.addEntries(registries, entries);
    }

    public String getName() {
        return Redemption.MOD_ID + "_dynamic";
    }
}
