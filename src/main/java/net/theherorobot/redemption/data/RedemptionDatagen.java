package net.theherorobot.redemption.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.theherorobot.redemption.data.provider.RedemptionDamageTypeTagGen;
import net.theherorobot.redemption.data.provider.RedemptionDynamicRegistryGen;
import net.theherorobot.redemption.data.provider.RedemptionItemTagGen;
import net.theherorobot.redemption.data.provider.lang.RedemptionLangGen;
import net.theherorobot.redemption.data.provider.lang.RedemptionLolLangGen;
import net.theherorobot.redemption.data.provider.resources.RedemptionModelGen;
import net.theherorobot.redemption.impl.index.data.RedemptionDamageTypes;

public class RedemptionDatagen implements DataGeneratorEntrypoint {
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(RedemptionLangGen::new);
        pack.addProvider(RedemptionLolLangGen::new);

        pack.addProvider(RedemptionModelGen::new);

        pack.addProvider(RedemptionDamageTypeTagGen::new);
        pack.addProvider(RedemptionItemTagGen::new);

        pack.addProvider(RedemptionDynamicRegistryGen::new);
    }

    public void buildRegistry(RegistryBuilder builder) {
        builder.addRegistry(RegistryKeys.DAMAGE_TYPE, RedemptionDamageTypes.TYPES::bootstrap);
    }
}
