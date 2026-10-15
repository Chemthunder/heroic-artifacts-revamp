package net.the_hero_robot.redemption.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.the_hero_robot.redemption.data.provider.client.RNLanguageProvider;
import net.the_hero_robot.redemption.data.provider.client.RNLolLanguageProvider;
import net.the_hero_robot.redemption.data.provider.client.RNModelProvider;
import net.the_hero_robot.redemption.data.provider.client.RNParticleProvider;
import net.the_hero_robot.redemption.data.provider.server.RNDynamicRegistryProvider;
import net.the_hero_robot.redemption.data.provider.server.RNRecipeProvider;
import net.the_hero_robot.redemption.data.provider.server.tags.RNDamageTypeTagProvider;
import net.the_hero_robot.redemption.data.provider.server.tags.RNItemTagProvider;
import net.the_hero_robot.redemption.impl.index.data.RNDamageTypes;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public class RedemptionDatagen implements DataGeneratorEntrypoint {
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        // Client
        pack.addProvider(RNLanguageProvider::new);
        pack.addProvider(RNLolLanguageProvider::new);

        pack.addProvider(RNModelProvider::new);
        pack.addProvider(RNParticleProvider::new);

        // Server
        pack.addProvider(RNDamageTypeTagProvider::new);
        pack.addProvider(RNItemTagProvider::new);

        pack.addProvider(RNRecipeProvider::new);

        pack.addProvider(RNDynamicRegistryProvider::new);
    }

    public void buildRegistry(RegistryBuilder builder) {
        builder.addRegistry(RegistryKeys.DAMAGE_TYPE, RNDamageTypes.TYPES::bootstrap);
    }
}
