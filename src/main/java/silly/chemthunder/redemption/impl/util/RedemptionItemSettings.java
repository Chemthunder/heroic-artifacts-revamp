package silly.chemthunder.redemption.impl.util;

import net.acoyt.acornlib.api.item.AcornItemSettings;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import org.joml.Vector4i;
import silly.chemthunder.redemption.impl.component.KatanaComponent;
import silly.chemthunder.redemption.impl.index.RedemptionDataComponents;

public class RedemptionItemSettings extends AcornItemSettings {
    public RedemptionItemSettings katana(KatanaComponent.BladeType bladeType, KatanaType katanaType, ItemConvertible... materials) {
        this.component(RedemptionDataComponents.KATANA, new KatanaComponent(bladeType.isSheathed(), bladeType.isSheath(), katanaType, Ingredient.ofItems(materials)));
        return this;
    }

    public RedemptionItemSettings colors(int nameColor, int startColor, int endColor, int backgroundColor) {
        this.component(RedemptionDataComponents.COLORS, new Vector4i(nameColor, startColor, endColor, backgroundColor));
        return this;
    }

    public RedemptionItemSettings colors(int nameColor) {
        this.component(RedemptionDataComponents.COLORS, new Vector4i(nameColor, 0xFF6e5353, 0xFF271e1e, 0xFF1d1212));
        return this;
    }
}
