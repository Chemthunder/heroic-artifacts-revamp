package net.the_hero_robot.redemption.data.provider.lang;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static net.the_hero_robot.redemption.impl.index.RNItems.COURT_GLASS;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public class RNLolLanguageProvider extends FabricLanguageProvider {
    public RNLolLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "lol_us", registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
        builder.add(COURT_GLASS, "Aortic Work of Art");
    }
}
