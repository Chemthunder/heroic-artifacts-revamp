package silly.chemthunder.redemption.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CourtGlassItem extends Item {
    public CourtGlassItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
