package net.the_hero_robot.redemption.impl.util;

import net.acoyt.acornlib.api.item.AcornItemSettings;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.index.RNDataComponents;
import org.joml.Vector4i;

/**
 * @author AcoYT
 */
public class RedemptionItemSettings extends AcornItemSettings {
    public RedemptionItemSettings katana(KatanaComponent.BladeType bladeType, KatanaType katanaType, ItemConvertible... materials) {
        this.component(RNDataComponents.KATANA, new KatanaComponent(bladeType, katanaType, Ingredient.ofItems(materials)));
        return this;
    }

    public RedemptionItemSettings colors(int nameColor, int startColor, int endColor, int backgroundColor) {
        this.component(RNDataComponents.COLORS, new Vector4i(nameColor, startColor, endColor, backgroundColor));
        return this;
    }

    public RedemptionItemSettings colors(int nameColor) {
        this.component(RNDataComponents.COLORS, new Vector4i(nameColor, 0xFF6e5353, 0xFF271e1e, 0xFF1d1212));
        return this;
    }
}
