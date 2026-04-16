package silly.chemthunder.redemption.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import silly.chemthunder.redemption.impl.Redemption;

import java.util.concurrent.CompletableFuture;

public class RedemptionDynamicRegistryGen extends FabricDynamicRegistryProvider {
    public RedemptionDynamicRegistryGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        entries.addAll(wrapperLookup.getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE));
    }

    public String getName() {
        return Redemption.MOD_ID + "_dynamic";
    }
}
