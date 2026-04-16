package silly.chemthunder.redemption.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import silly.chemthunder.redemption.data.provider.RedemptionDamageTypeTagGen;
import silly.chemthunder.redemption.data.provider.RedemptionDynamicRegistryGen;
import silly.chemthunder.redemption.data.provider.RedemptionItemTagGen;
import silly.chemthunder.redemption.data.provider.lang.RedemptionLangGen;
import silly.chemthunder.redemption.data.provider.resources.RedemptionModelGen;
import silly.chemthunder.redemption.impl.index.data.RedemptionDamageTypes;

public class RedemptionDatagen implements DataGeneratorEntrypoint {
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(RedemptionLangGen::new);

        pack.addProvider(RedemptionModelGen::new);

        pack.addProvider(RedemptionDamageTypeTagGen::new);
        pack.addProvider(RedemptionItemTagGen::new);

        pack.addProvider(RedemptionDynamicRegistryGen::new);
    }

    public void buildRegistry(RegistryBuilder builder) {
        builder.addRegistry(RegistryKeys.DAMAGE_TYPE, RedemptionDamageTypes::bootstrap);
    }
}
