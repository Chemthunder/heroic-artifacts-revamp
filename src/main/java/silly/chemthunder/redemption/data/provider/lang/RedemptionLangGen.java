package silly.chemthunder.redemption.data.provider.lang;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import silly.chemthunder.redemption.impl.index.RedemptionItems;

import java.util.concurrent.CompletableFuture;

public class RedemptionLangGen extends FabricLanguageProvider {
    public RedemptionLangGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
        RedemptionItems.ITEMS.registerLang(wrapperLookup, builder);

        builder.add("itemGroup.redemption", "Redemption");

        builder.add("death.attack.katana", "%1$s was cut in half");
        builder.add("death.attack.katana.player", "%1$s was cut in half by %2$s");
        builder.add("death.attack.katana.item", "%1$s was cut in half by %2$s wielding %3$s");

        builder.add("death.attack.descend", "%1$s's purpose was revoked");
        builder.add("death.attack.descend.player", "%1$s's purpose was revoked");
        builder.add("death.attack.descend.item", "%1$s's purpose was revoked");

        builder.add("lore.court_glass.0", "An ancient artifact that has been passed down");
        builder.add("lore.court_glass.1", "time and time again from one Judge to the next.");
        builder.add("lore.court_glass.2", "It's your turn now.");

        builder.add("lore.hunters_glass.0", "An artifact from a time long ago.");
        builder.add("lore.hunters_glass.1", "It was said to be once wielded by the Last Judge.");
        builder.add("lore.hunters_glass.2", "Now, it's in your hands. Don't let her down.");

        builder.add("category.redemption", "Ikir Abilities");
        builder.add("key.redemption.switch_gamemodes", "Switch Game Modes");
        builder.add("key.redemption.use_immolation", "Use Immolation");
    }
}
