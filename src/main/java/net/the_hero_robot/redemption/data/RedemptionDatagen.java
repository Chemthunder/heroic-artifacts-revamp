package net.the_hero_robot.redemption.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.the_hero_robot.redemption.data.provider.RedemptionDamageTypeTagProvider;
import net.the_hero_robot.redemption.data.provider.RedemptionDynamicRegistryProvider;
import net.the_hero_robot.redemption.data.provider.RedemptionItemTagProvider;
import net.the_hero_robot.redemption.data.provider.RedemptionRecipeProvider;
import net.the_hero_robot.redemption.data.provider.lang.RedemptionLanguageProvider;
import net.the_hero_robot.redemption.data.provider.lang.RedemptionLolLanguageProvider;
import net.the_hero_robot.redemption.data.provider.resources.RedemptionModelProvider;
import net.the_hero_robot.redemption.impl.index.data.RedemptionDamageTypes;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public class RedemptionDatagen implements DataGeneratorEntrypoint {
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(RedemptionLanguageProvider::new);
        pack.addProvider(RedemptionLolLanguageProvider::new);

        pack.addProvider(RedemptionModelProvider::new);

        pack.addProvider(RedemptionDamageTypeTagProvider::new);
        pack.addProvider(RedemptionItemTagProvider::new);

        pack.addProvider(RedemptionRecipeProvider::new);

        pack.addProvider(RedemptionDynamicRegistryProvider::new);
    }

    public void buildRegistry(RegistryBuilder builder) {
        builder.addRegistry(RegistryKeys.DAMAGE_TYPE, RedemptionDamageTypes.TYPES::bootstrap);
    }
}
