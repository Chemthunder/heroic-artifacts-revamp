package net.the_hero_robot.redemption.mixin.compat.acornlib;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.item.KatanaItem;
import net.the_hero_robot.redemption.impl.util.ModUtil;

/**
 * @author AcoYT
 */
@Mixin(ItemRegistrant.class)
public abstract class ItemRegistrantMixin {
    @WrapMethod(method = "lambda$registerLang$1")
    private void redemption$registerMultipleForKatanas(FabricLanguageProvider.TranslationBuilder builder, Item item, Operation<Void> original) {
        if (item instanceof KatanaItem) {
            for (KatanaComponent.BladeType bladeType : KatanaComponent.BladeType.values()) {
                Identifier id = ModUtil.formatKatanaId(item, bladeType, KatanaComponent.get(item.getDefaultStack()).type(), false);
                builder.add(Util.createTranslationKey("item", id), MiscUtils.formatString(id.getPath()));
            }

            return;
        }

        original.call(builder, item);
    }
}
