package net.the_hero_robot.redemption.data.provider.lang;


import net.acoyt.acornlib.api.template.OrganizedLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.the_hero_robot.redemption.impl.index.RedemptionItems;
import net.the_hero_robot.redemption.impl.index.data.RedemptionDamageTypes;
import net.the_hero_robot.redemption.impl.index.tag.RedemptionItemTags;

import java.util.concurrent.CompletableFuture;

import static net.acoyt.acornlib.api.util.DataUtils.registerDamageType;

/**
 * @author AcoYT
 */
public class RedemptionLanguageProvider extends OrganizedLanguageProvider {
    public RedemptionLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateRegistrants(RegistryWrapper.WrapperLookup registries, TranslationBuilder builder) {
        RedemptionItems.ITEMS.registerLang(registries, builder);
    }

    public void generateTags(RegistryWrapper.WrapperLookup registries, TranslationBuilder builder) {
        RedemptionItemTags.ITEMS.registerLang(registries, builder);
    }

    public void generateTexts(RegistryWrapper.WrapperLookup registries, TranslationBuilder builder) {
        builder.add("itemGroup.redemption", "Redemption");

        registerDamageType(builder, RedemptionDamageTypes.KATANA,
                "%1$s was cut in half",
                "%1$s was cut in half by %2$s wielding %3$s",
                "%1$s was cut in half by %2$s"
        );

        registerDamageType(builder, RedemptionDamageTypes.DESCEND,
                "%1$s's purpose was revoked",
                "%1$s's purpose was revoked by %2$s with %3$s",
                "%1$s's purpose was revoked by %2$s"
        );

        builder.add("tooltip.redemption.court_glass.0", "An ancient artifact that has been passed down");
        builder.add("tooltip.redemption.court_glass.1", "time and time again from one Judge to the next.");
        builder.add("tooltip.redemption.court_glass.2", "It's your turn now.");

        builder.add("tooltip.redemption.hunters_glass.0", "An artifact from a time long ago.");
        builder.add("tooltip.redemption.hunters_glass.1", "It was said to be once wielded by the Last Judge.");
        builder.add("tooltip.redemption.hunters_glass.2", "Now, it's in your hands. Don't let her down.");

        builder.add("key.categories.redemption", "Redemption");
        builder.add("key.redemption.use_immolation", "Use Immolation");
        builder.add("key.redemption.switch_gamemode", "Switch Game Mode");

        builder.add("lore.courtling", "Courtling");
    }
}
